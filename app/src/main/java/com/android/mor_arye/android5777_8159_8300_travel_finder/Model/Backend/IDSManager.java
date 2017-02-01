package com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Backend;

import android.content.ContentValues;

import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Entities.Business;
import com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Entities.Recreation;

import java.util.Collection;

/**
 * Created by mor on 26 נובמבר 2016.
 */

public interface IDSManager {

    public void insertBusiness(ContentValues newBusiness);
    public void insertRecreation(ContentValues newRecreation);
    public Collection<Business> getAllBusiness();
    public Collection<Recreation> getAllRecreation();
}
