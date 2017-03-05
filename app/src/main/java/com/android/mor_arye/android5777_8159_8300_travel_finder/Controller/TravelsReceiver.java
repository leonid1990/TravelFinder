package com.android.mor_arye.android5777_8159_8300_travel_finder.Controller;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Backend.IDSManager;
import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Backend.ManagerFactory;
import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Entities.Recreation;

import java.util.ArrayList;
import java.util.List;

public class TravelsReceiver extends BroadcastReceiver {
    SharedPreferences sharedpreferences;
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String COUNT_KEY = "countKey";
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
                DSManager.getAllBusinesses().clear();
                Toast.makeText(context, "TravelFinder: New business added!", Toast.LENGTH_LONG).show();
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
                try {
                    DSManager.getAllTravels().clear();
                    Uri uriOfAllRecreations = Uri.parse("content://com.android.mor_arye.android5777_8159_8300/recreations");
                    Cursor result = context.getContentResolver().query(uriOfAllRecreations, null,
                            null, null, null);
                    List<ContentValues> travels = new ArrayList<>();
                    final ContentValues travel = new ContentValues();
                    while (result.moveToNext()) {
                        String str = result.getString(result.getColumnIndex("typeOfRecreation"));
                        if (result.getString(result.getColumnIndex("typeOfRecreation")).equals("TRAVEL")) {
                            DatabaseUtils.cursorRowToContentValues(result, travel);
                            travels.add(travel);
                        }
                    }
                    int numOfTravels = getPrefs(context);
                    if (travels.size() > numOfTravels)
                        Toast.makeText(context, "TravelFinder: New travel added!", Toast.LENGTH_LONG).show();
                    savePrefs(context, travels.size());
                    for (ContentValues newTravel : travels)
                        DSManager.insertTravel(newTravel);
                }
                catch(Exception ex){
                    Log.d(BR_TAG, ex.getMessage());
                }
        }
            } else
                Log.d(BR_TAG, "error");
        }
    public int getPrefs(Context c)
    {
        sharedpreferences = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            return sharedpreferences.getInt(COUNT_KEY, 0);
    }
    public void savePrefs(Context c, int numOfTravels) {
        sharedpreferences = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(COUNT_KEY, numOfTravels);
        editor.commit();
    }
}