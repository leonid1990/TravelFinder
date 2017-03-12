package com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 09-Mar-17.
 */

public class DsUpdater {
    public static IDSManager DSManager = ManagerFactory.getDS();
    public static final String UPDATER_TAG = "DsUpdater";

    public static void FillUpDS(Context c) throws Exception
    {
        businessFillUp(c);
        travelsFillUp(c);
    }
    public static void updateDS(char type, Context c) throws Exception
    {
        if (type == 'b') {
            try {
                businessFillUp(c);
            }
            catch (Exception e){
                Log.d(UPDATER_TAG, e.getMessage());
            }
        }
        else if (type == 'r') {
            try {
                travelsFillUp(c);
            }
            catch (Exception e){
                Log.d(UPDATER_TAG, e.getMessage());
            }
        }
        else
            throw new Exception("Error. Expects a business or a travel.");
    }
    private static void travelsFillUp(final Context context) throws Exception {
        DSManager.getAllTravels().clear();
        new AsyncTask<Context, Void, Cursor>() {
            Cursor result;
            Context[] c ={context};
            Uri uriOfAllRecreations = Uri.parse("content://com.android.mor_arye.android5777_8159_8300/recreations");
            @Override
            protected Cursor doInBackground(Context... c) {
                return result = c[0].getContentResolver().query(uriOfAllRecreations, null, null, null, null);
            }
            @Override
            protected void onPostExecute(Cursor result) {
                List<ContentValues> travels = new ArrayList<>();
                final ContentValues travel = new ContentValues();
                while (result.moveToNext()) {
                    String typeOfRecreation = result.getString(result.getColumnIndex("typeOfRecreation"));
                    if (typeOfRecreation.equals("TRAVEL")) {
                        DatabaseUtils.cursorRowToContentValues(result, travel);
                        travels.add(travel);
                    }
                }
                for (ContentValues newTravel : travels)
                    DSManager.insertTravel(newTravel);
            }
        }.execute();
    }

    private static void businessFillUp(final Context context) throws Exception {
        DSManager.getAllBusinesses().clear();
        //Toast.makeText(this, "TravelFinder: New business added!", Toast.LENGTH_LONG).show();
        new AsyncTask<Context, Void, Cursor>() {
            Cursor result;
            Context[] c ={context};
            Uri uriOfAllBusinesses = Uri.parse("content://com.android.mor_arye.android5777_8159_8300/businesses");
            @Override
            protected Cursor doInBackground(Context... c) {
                return result = c[0].getContentResolver().query(uriOfAllBusinesses, null, null, null, null);
            }
            @Override
            protected void onPostExecute(Cursor result) {
                final ContentValues business = new ContentValues();
                if (result.moveToFirst()) {
                    do {
                        DatabaseUtils.cursorRowToContentValues(result, business);
                        DSManager.insertBusiness(business);
                    }
                    while (result.moveToNext());
                }
            }
        }.execute();
    }
}
