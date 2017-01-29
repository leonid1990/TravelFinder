package com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Backend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class TravelsReceiver extends BroadcastReceiver {
    public static final String TravelApp_TAG = "TravelApp";
    private Context c;
    public TravelsReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase("com.android.mor_arye.android5777_8159_8300.newUpdates")) {
            char type = intent.getExtras().getChar("table");
            if (type == 'b'){
                Log.d(TravelApp_TAG, "business");

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        Uri uriOfAllUsers = Uri.parse("content://com.android.mor_arye.android5777_8159_8300/businesses");
                        Cursor result = context.getContentResolver().query(uriOfAllUsers,null,null,null,null);
                        result.moveToFirst();
                        String str = result.getString(result.getColumnIndex("nameBusiness"));
                        Log.d(TravelApp_TAG, str);
                        return null;
                    }
                }.execute();

            }
            else if (type == 'r')
                Log.d("checkBR", "recreations");
            else
                Log.d("checkBR", "error");
        }
    }
}