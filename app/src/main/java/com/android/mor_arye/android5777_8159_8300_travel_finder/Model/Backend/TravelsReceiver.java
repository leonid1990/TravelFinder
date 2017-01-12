package com.android.mor_arye.android5777_8159_8300_travel_finder.Model.Backend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class TravelsReceiver extends BroadcastReceiver {
    public TravelsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase("com.example.ezras.newUpdates")) {
            char type = intent.getExtras().getChar("table");
            if (type == 't')
                Log.d("checkBR", "trips");
            else if (type == 'r')
                Log.d("checkBR", "recreations");
            else
                Log.d("checkBR", "error");
        }
    }
}
