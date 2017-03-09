package com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 09-Mar-17.
 */

public class DsUpdater {
    public static IDSManager DSManager = ManagerFactory.getDS();
    public static final String UPDATER_TAG = "DsUpdater";

    public static void FillUpDS(Context c)
    {
        try{
            businessFillUp(c);
            travelsFillUp(c);
        }
        catch (Exception e){
            Log.d(UPDATER_TAG, e.getMessage());
        }
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
    private static void travelsFillUp(Context c) throws Exception {
        Cursor result;
        DSManager.getAllTravels().clear();
        Uri uriOfAllRecreations = Uri.parse("content://com.android.mor_arye.android5777_8159_8300/recreations");
        try{
            result = c.getContentResolver().query(uriOfAllRecreations, null, null, null, null);
        }
        catch (Exception e){
            throw e;
        }
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

    private static void businessFillUp(Context c) throws Exception{
        DSManager.getAllBusinesses().clear();
        //Toast.makeText(this, "TravelFinder: New business added!", Toast.LENGTH_LONG).show();
        Uri uriOfAllBusinesses = Uri.parse("content://com.android.mor_arye.android5777_8159_8300/businesses");
        Cursor result;
        try{
            result = c.getContentResolver().query(uriOfAllBusinesses, null, null, null, null);
        }
        catch (Exception e){
            throw e;
        }
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
