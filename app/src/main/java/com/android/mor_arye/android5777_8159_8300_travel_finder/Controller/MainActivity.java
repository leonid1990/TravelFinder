package com.android.mor_arye.android5777_8159_8300_travel_finder.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Backend.DsUpdater;
import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Backend.IDSManager;
import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Backend.ManagerFactory;
import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Entities.Travel;
import com.android.mor_arye.android5777_8159_8300_travel_finder.R;

public class MainActivity extends AppCompatActivity {

    public static final String DS_TAG = "testDS";
    /*SharedPreferences sharedpreferences;
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String COUNT_KEY = "countKey";*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            DsUpdater.FillUpDS(this);
        }
        catch (Exception ex) {
            Log.d(DsUpdater.UPDATER_TAG, ex.getMessage());
        }
    }

    public void testDS(View view)
    {
        IDSManager DSManager = ManagerFactory.getDS();

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
