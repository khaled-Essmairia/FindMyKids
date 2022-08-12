package com.example.firebaseapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

public class AirplaneModeChangeReceiver extends BroadcastReceiver {
    public String AirPlane;
    @Override
    public void onReceive(Context context, Intent intent) {

        if (isAirplaneModeOn(context.getApplicationContext())) {
            Toast.makeText(context, "AirPlane mode is on", Toast.LENGTH_SHORT).show();
            AirPlane = "AirPlane mode is on";
        } else {
            Toast.makeText(context, "AirPlane mode is off", Toast.LENGTH_SHORT).show();
            AirPlane = "AirPlane mode is off";
        }

        Intent intent1 = new Intent(AirplaneModeChangeReceiver.this ,MainActivityChild.class);
        intent.putExtra("airPalne", String.valueOf(AirPlane).toString());
    }



    private static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }
}

