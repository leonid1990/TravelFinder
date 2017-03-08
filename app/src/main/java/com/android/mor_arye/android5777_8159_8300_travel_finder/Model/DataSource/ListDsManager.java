package com.android.mor_arye.android5777_8159_8300_travel_finder.Model.DataSource;

import android.content.ContentValues;
import android.location.Address;
import android.util.Log;

import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Backend.IDSManager;
import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Entities.Business;
import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Entities.Travel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by mor on 26 נובמבר 2016.
 */


public class ListDsManager implements IDSManager {

    public List<Business> businesses;
    public List<Travel> travels;

    private boolean businessesUpdates = false;
    private boolean recreationsUpdates = false;

    // constructor
    public ListDsManager() {
        businesses = new ArrayList<>();
        travels = new ArrayList<>();
    }

    @Override
    public void insertBusiness(ContentValues newBusiness) {
        businessesUpdates=true;
        businesses.add(new Business(
                newBusiness.getAsString("nameBusiness"),
                new Address(new Locale(newBusiness.getAsString("addressBusiness"))),
                newBusiness.getAsString("phoneNumber"),
                newBusiness.getAsString("emailAddress"),
                newBusiness.getAsString("websiteLink")
        ));
    }

    @Override
    public void insertTravel(ContentValues newTravel) {
        recreationsUpdates = true;
        GregorianCalendar calB = new GregorianCalendar();
        GregorianCalendar calE = new GregorianCalendar();
        try {
            calB = strToCal(newTravel.getAsString("dateOfBeginning"));
            calE = strToCal(newTravel.getAsString("dateOfEnding"));
        }
        catch (Exception e)
        {
            Log.d("Date error", e.getMessage());
        }
        travels.add(new Travel(
                newTravel.getAsString("nameOfCountry"),
                /*new GregorianCalendar(
                        new Integer(dateB.substring(6,10)),
                        new Integer(dateB.substring(3,5)),
                        new Integer(dateB.substring(0,2))),
                new GregorianCalendar(
                        new Integer(dateE.substring(6,10)),
                        new Integer(dateE.substring(3,5)),
                        new Integer(dateE.substring(0,2))),*/
                calB, calE,
                newTravel.getAsDouble("price"),
                newTravel.getAsString("description"),
                newTravel.getAsInteger("idBusiness")
        ));
    }

    // ~~~~~~~ get all collections ~~~~~~~

    @Override
    public Collection<Business> getAllBusinesses() {
        return businesses;
    }

    @Override
    public Collection<Travel> getAllTravels() {
        return travels;
    }

    @Override
    public Collection<Travel> getTravelsByCountry(String nameOfCountry) {
        HashMap<String, Collection<Travel>> hashMap = new HashMap<String, Collection<Travel>>();
        for (Travel travel: getAllTravels())
        {
            if (!hashMap.containsKey("Country")) {
                List<Travel> list = new ArrayList<Travel>();
                list.add(travel);

                hashMap.put("Country", list);
            } else {
                hashMap.get("Country").add(travel);
            }
        }

        return hashMap.get(nameOfCountry);
    }

    @Override
    public Collection<Travel> getTravelsByBusiness(String nameOfBusiness) {
        HashMap<String, Collection<Travel>> hashMap = new HashMap<String, Collection<Travel>>();
        for (Travel travel: getAllTravels())
        {
            if (!hashMap.containsKey("Country")) {
                List<Travel> list = new ArrayList<Travel>();
                list.add(travel);

                hashMap.put("Country", list);
            } else {
                hashMap.get("Country").add(travel);
            }
        }

        return hashMap.get(nameOfBusiness);
    }
    public GregorianCalendar strToCal(String strDate) throws Exception
    {
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = df.parse(strDate);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        return cal;
    }
    // ~~~~~~~~~~~~~~

}
