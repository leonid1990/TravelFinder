package com.android.mor_arye.android5777_8159_8300_travel_finder.Views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mor_arye.android5777_8159_8300_travel_finder.R;

/**
 * Created by Leon on 18-Mar-17.
 */

public class TravelsFragment extends Fragment {

    public TravelsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_travels, container, false);

        return rootView;
    }

}
