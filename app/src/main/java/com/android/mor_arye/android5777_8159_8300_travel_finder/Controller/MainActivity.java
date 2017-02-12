package com.android.mor_arye.android5777_8159_8300_travel_finder.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Backend.IDSManager;
import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Backend.ManagerFactory;
import com.android.mor_arye.android5777_8159_8300_travel_finder.R;

public class MainActivity extends AppCompatActivity {

    public static final String DS_TAG = "testDS";
    public static IDSManager DSManager = ManagerFactory.getDS();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void testDS(View view)
    {
        try
        {
            Log.d(DS_TAG,"Businesses: " + TravelsReceiver.DSManager.getAllBusinesses().toString() + '\n');
            Log.d(DS_TAG,"Travels: " + TravelsReceiver.DSManager.getAllTravels().toString() + '\n');
            Log.d(DS_TAG,"Travels by Afghanistan: " +
                    TravelsReceiver.DSManager.getTravelsByCountry("Afghanistan").toString() + '\n');
            Log.d(DS_TAG,"Travels by Albania: " +
                    TravelsReceiver.DSManager.getTravelsByCountry("Albania").toString() + '\n');
            Log.d(DS_TAG,"Travels by JCT: " +
                    TravelsReceiver.DSManager.getTravelsByBusiness("JCT").toString());
            Log.d(DS_TAG,"Travels by BIU: " +
                    TravelsReceiver.DSManager.getTravelsByBusiness("JCT").toString());
        }
        catch (Exception ex)
        {
            ex.getMessage();
        }
    }
}
