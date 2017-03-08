package com.android.mor_arye.android5777_8159_8300_travel_finder.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TravelsReceiver extends BroadcastReceiver {

    public TravelsReceiver() {}
    Context c;

    @Override
    public void onReceive(final Context context, Intent intent) {
        c = context;
        if (intent.getAction().equalsIgnoreCase("com.android.mor_arye.android5777_8159_8300.newUpdates")) {
            Intent i = new Intent(c, MainActivity.class);
            i.putExtra("table", intent.getExtras().getChar("table"));
            c.startActivity(i);
        }
    }
    /*
        @Override
        public void onReceive(Context context, Intent intent) {
            StringBuilder sb = new StringBuilder();
            sb.append("Action: " + intent.getAction() + "\n");
            sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
            String log = sb.toString();
            Log.d(BR_TAG, log);
            Toast.makeText(context, log, Toast.LENGTH_LONG).show();
        }
        */
}