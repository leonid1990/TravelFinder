package com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Backend;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class TravelsReceiver extends BroadcastReceiver {
    public static final String BR_TAG = "TravelsReceiver";
    private static IDSManager DSManager = ManagerFactory.getDS();
/*
    @Override
    public void onReceive(Context context, Intent intent) {
        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + "\n");
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
        String log = sb.toString();
        Log.d(BR_TAG, log);
        Toast.makeText(context, log, Toast.LENGTH_LONG).show();
    }*/
    public TravelsReceiver() {
    }
Context c;
    @Override
    public void onReceive(final Context context, Intent intent) {
        c = context;
        if (intent.getAction().equalsIgnoreCase("com.android.mor_arye.android5777_8159_8300.newUpdates")) {

            char type = intent.getExtras().getChar("table");
            if (type == 'b'){
                Log.d(BR_TAG, "business");

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        Uri uriOfAllBusinesses = Uri.parse("content://com.android.mor_arye.android5777_8159_8300/businesses");
                        Cursor result = context.getContentResolver().query(uriOfAllBusinesses,null,null,null,null);
                        final ContentValues business = new ContentValues();
                        if (result.moveToFirst())
                        {
                            do
                            {
                                DatabaseUtils.cursorRowToContentValues (result, business);
                                DSManager.insertBusiness(business);
                            }
                            while(result.moveToNext());
                        }
                        return null;
                    }
                }.execute();
            }
            else if (type == 'r') {
                Log.d(BR_TAG, "recreations");
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        Uri uriOfAllRecreations = Uri.parse("content://com.android.mor_arye.android5777_8159_8300/recreations");
                        Cursor result = context.getContentResolver().query(uriOfAllRecreations,null,null,null,null);
                        final ContentValues recreation = new ContentValues();
                        if (result.moveToFirst())
                        {
                            do
                            {
                                DatabaseUtils.cursorRowToContentValues (result, recreation);
                                DSManager.insertBusiness(recreation);
                            }
                            while(result.moveToNext());
                        }
                        return null;
                    }
                }.execute();
            }
            else
                Log.d(BR_TAG, "error");
        }
    }
   /*
    private void getAllBusinesses() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                Uri uriOfAllBusinesses = Uri.parse("content://com.android.mor_arye.android5777_8159_8300/businesses");
                Cursor result = c.getContentResolver().query(uriOfAllBusinesses,null,null,null,null);
                String str;
                if (result.moveToFirst())
                {
                    do
                    {
                        str = result.getString(result.getColumnIndex("nameBusiness"));
                        Log.d(BR_TAG, str);
                    }
                    while(result.moveToNext());
                }
                return null;
            }
        }.execute();
    }
    */
}
