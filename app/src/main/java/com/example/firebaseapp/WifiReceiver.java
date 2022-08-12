package com.example.firebaseapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

public class WifiReceiver extends BroadcastReceiver {

    String wifi;

    @Override
    public void onReceive(Context context, Intent intent) {



        if (wifiOn(context.getApplicationContext())) {
            wifi="Wifi is on";
            // Toast.makeText(context, "Wifi is on", Toast.LENGTH_SHORT).show();
        } else {
            wifi="Wifi is off";
            //Toast.makeText(context, "Wifi is off", Toast.LENGTH_SHORT).show();
        }

        intent = new Intent(WifiReceiver.this , MainActivityChild.class);
        intent.putExtra("wifi", String.valueOf(wifi));
    }

    private static boolean wifiOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.Global.WIFI_ON, 0) != 0;
    }
}