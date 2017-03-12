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
        fillUpBusinesses(c);
        fillUpTravels(c);
    }
    public static void updateDS(char type, Context c) throws Exception
    {
        if (type == 'b') {
            try {
                fillUpBusinesses(c);
            }
            catch (Exception e){
                Log.d(UPDATER_TAG, e.getMessage());
            }
        }
        else if (type == 'r') {
            try {
                fillUpTravels(c);
            }
            catch (Exception e){
                Log.d(UPDATER_TAG, e.getMessage());
            }
        }
        else
            throw new Exception("Error. Expects a business or a travel.");
    }
    private static void fillUpTravels(Context context) throws Exception {
        TravelsAsyncTask task = new TravelsAsyncTask(context);
        task.execute();
    }

    private static void fillUpBusinesses(Context context) throws Exception {
        BusinessesAsyncTask task = new BusinessesAsyncTask(context);
        task.execute();
    }

    public static class TravelsAsyncTask extends AsyncTask<Void, Void, Cursor>
    {
        private Context mContext;
        public TravelsAsyncTask (Context context){
            mContext = context;
        }
        Cursor result;
        Uri uriOfAllRecreations = Uri.parse("content://com.android.mor_arye.android5777_8159_8300/recreations");
        @Override
        protected void onPreExecute(){
            DSManager.getAllTravels().clear();
        }
        @Override
        protected Cursor doInBackground(Void... params) {
            /*try{
                wait(10*1000);
            }
            catch (Exception e)
            {
                Log.d(UPDATER_TAG, e.getMessage());
            }*/
            return result = mContext.getContentResolver().query(uriOfAllRecreations, null, null, null, null);
        }
        @Override
        protected void onPostExecute(Cursor result) {
            List<ContentValues> travels = new ArrayList<>();
            final ContentValues travel = new ContentValues();
            while (result.moveToNext()) {
                String typeOfRecreation = result.getString(result.getColumnIndex("typeOfRecreation"));
                if (typeOfRecreation.equals("TRAVEL")) {
                    DatabaseUtils.cursorRowToContentValues(result, travel);
                    DSManager.insertTravel(travel);
                }
            }
        }

    }
    public static class BusinessesAsyncTask extends AsyncTask<Void, Void, Cursor>
    {
        private Context mContext;
        public BusinessesAsyncTask (Context context){
            mContext = context;
        }
        Cursor result;
        Uri uriOfAllBusinesses = Uri.parse("content://com.android.mor_arye.android5777_8159_8300/businesses");
        @Override
        protected void onPreExecute(){
            DSManager.getAllBusinesses().clear();
        }
        @Override
        protected Cursor doInBackground(Void... params) {
            return result = mContext.getContentResolver().query(uriOfAllBusinesses, null, null, null, null);
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
    }
}
