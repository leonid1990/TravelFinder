package com.android.mor_arye.android5777_8159_8300_travel_finder.Controller;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.util.Log;

import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Backend.IDSManager;
import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Backend.ManagerFactory;

public class TravelsReceiver extends BroadcastReceiver {
    public static final String BR_TAG = "TravelsReceiver";
    public static IDSManager DSManager = ManagerFactory.getDS();

    /*
        @Override
        public void onReceive(Context context, Intent intent) {
            StringBuilder sb = new StringBuilder();
            sb.append("Action: " + intent.getAction() + "\n");
            sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
            String log = sb.toString();
            Log.d(BR_TAG, log);
            Toast.makeText(context, log, Toast.LENGTH_LONG).show();
        }
        */
    public TravelsReceiver() {
    }

    Context c;

    @Override
    public void onReceive(final Context context, Intent intent) {
        c = context;
        if (intent.getAction().equalsIgnoreCase("com.android.mor_arye.android5777_8159_8300.newUpdates")) {

            char type = intent.getExtras().getChar("table");
            if (type == 'b') {
                Log.d(BR_TAG, "businesses");
                Uri uriOfAllBusinesses = Uri.parse("content://com.android.mor_arye.android5777_8159_8300/businesses");
                try {
                    Cursor result = context.getContentResolver().query(uriOfAllBusinesses, null, null, null, null);
                    final ContentValues business = new ContentValues();
                    if (result.moveToFirst()) {
                        do {
                            DatabaseUtils.cursorRowToContentValues(result, business);
                            DSManager.insertBusiness(business);
                        }
                        while (result.moveToNext());
                    }
                } catch (Exception ex) {
                    Log.d(BR_TAG, ex.getMessage());
                }
            } else if (type == 'r') {
                Log.d(BR_TAG, "travels");
                try {
                    Uri uriOfAllRecreations = Uri.parse("content://com.android.mor_arye.android5777_8159_8300/travels");
                    Cursor result = context.getContentResolver().query(uriOfAllRecreations, null, null, null, null);
                    final ContentValues newTravel = new ContentValues();
                    if (result.moveToFirst()) {
                        do {
                            DatabaseUtils.cursorRowToContentValues(result, newTravel);
                            DSManager.insertTravel(newTravel);
                        }
                        while (result.moveToNext());
                    }
                } catch (Exception ex) {
                    Log.d(BR_TAG, ex.getMessage());
                }
            } else
                Log.d(BR_TAG, "error");
        }
    }
}