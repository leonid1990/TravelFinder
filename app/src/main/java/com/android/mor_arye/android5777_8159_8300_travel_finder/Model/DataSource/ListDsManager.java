package com.android.mor_arye.android5777_8159_8300_travel_finder.Model.DataSource;

import android.content.ContentValues;
import android.location.Address;

import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Backend.IDSManager;
import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Entities.Business;
import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Entities.Recreation;
import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Entities.TypeOfRecreation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by mor on 26 נובמבר 2016.
 */


public class ListDsManager implements IDSManager {

    public List<Business> businesses;
    public List<Recreation> travels;

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
        String dateB = newTravel.getAsString("dateOfBeginning");
        String dateE = newTravel.getAsString("dateOfEnding");

        travels.add(new Recreation(
                TypeOfRecreation.valueOf(newTravel.getAsString("typeOfRecreation")),
                newTravel.getAsString("nameOfCountry"),
                new GregorianCalendar(
                        new Integer(dateB.substring(6,10)),
                        new Integer(dateB.substring(3,5)),
                        new Integer(dateB.substring(0,2))),
                new GregorianCalendar(
                        new Integer(dateE.substring(6,10)),
                        new Integer(dateE.substring(3,5)),
                        new Integer(dateE.substring(0,2))),
                newTravel.getAsDouble("price"),
                newTravel.getAsString("description"),
                newTravel.getAsInteger("idBusiness")
        ));
    }

    // ~~~~~~~ get all collections ~~~~~~~

    @Override
    public Collection<Business> getAllBusiness() {
        return businesses;
    }

    @Override
    public Collection<Recreation> getAllTravels() {
        return travels;
    }
    // ~~~~~~~~~~~~~~

}
