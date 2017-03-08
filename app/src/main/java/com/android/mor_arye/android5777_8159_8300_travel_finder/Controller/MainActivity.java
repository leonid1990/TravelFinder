package com.android.mor_arye.android5777_8159_8300_travel_finder.Controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Backend.IDSManager;
import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Backend.ManagerFactory;
import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Entities.Travel;
import com.android.mor_arye.android5777_8159_8300_travel_finder.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String DS_TAG = "testDS";
    public static final String BR_TAG = "TravelsReceiver";
    public static IDSManager DSManager = ManagerFactory.getDS();
    /*SharedPreferences sharedpreferences;
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String COUNT_KEY = "countKey";*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getAction().equalsIgnoreCase("com.android.mor_arye.android5777_8159_8300.newUpdates"))
            try {
                fillDS(getIntent().getExtras().getChar("table"));
            }
            catch (Exception ex) {
                Log.d(BR_TAG, ex.getMessage());
            }
    }

    public void fillDS(char type) throws Exception
    {

        if (type == 'b') {
            DSManager.getAllBusinesses().clear();
            //Toast.makeText(this, "TravelFinder: New business added!", Toast.LENGTH_LONG).show();
            Uri uriOfAllBusinesses = Uri.parse("content://com.android.mor_arye.android5777_8159_8300/businesses");
            Cursor result = getContentResolver().query(uriOfAllBusinesses, null, null, null, null);
            final ContentValues business = new ContentValues();
            if (result.moveToFirst()) {
                do {
                    DatabaseUtils.cursorRowToContentValues(result, business);
                    DSManager.insertBusiness(business);
                }
                while (result.moveToNext());
            }
        }
        else if (type == 'r') {
            DSManager.getAllTravels().clear();
            Uri uriOfAllRecreations = Uri.parse("content://com.android.mor_arye.android5777_8159_8300/recreations");
            Cursor result = getContentResolver().query(uriOfAllRecreations, null,
                    null, null, null);
            List<ContentValues> travels = new ArrayList<>();
            final ContentValues travel = new ContentValues();
            while (result.moveToNext()) {
                String typeOfRecreation = result.getString(result.getColumnIndex("typeOfRecreation"));
                if (typeOfRecreation.equals("TRAVEL")) {
                    DatabaseUtils.cursorRowToContentValues(result, travel);
                    travels.add(travel);
                }
            }
            /*int numOfTravels = getPrefs(this);
            if (travels.size() > numOfTravels)
                Toast.makeText(this, "TravelFinder: New travel added!", Toast.LENGTH_LONG).show();
            savePrefs(this, travels.size());*/
            for (ContentValues newTravel : travels)
                DSManager.insertTravel(newTravel);
        }
        else
            Log.d(BR_TAG, "Error. Expects a business or a travel.");
    }

    public void testDS(View view)
    {
        try
        {
            Log.d(DS_TAG,"Businesses: " + DSManager.getAllBusinesses().toString() + '\n');
            Log.d(DS_TAG,"\nTravels: ");
            for (Travel r: DSManager.getAllTravels())
            {
                Log.d(DS_TAG,r.toString() + ", ");
            }
            /*Log.d(DS_TAG,"Travels by Afghanistan: " +
                    TravelsReceiver.DSManager.getTravelsByCountry("Afghanistan").toString() + '\n');
            Log.d(DS_TAG,"Travels by Albania: " +
                    TravelsReceiver.DSManager.getTravelsByCountry("Albania").toString() + '\n');
            Log.d(DS_TAG,"Travels by JCT: " +
                    TravelsReceiver.DSManager.getTravelsByBusiness("JCT").toString());
            Log.d(DS_TAG,"Travels by BIU: " +
                    TravelsReceiver.DSManager.getTravelsByBusiness("BIU").toString());*/
        }
        catch (Exception ex)
        {
            Log.d(DS_TAG, ex.getMessage());
        }
    }

    /*
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
    */
}
