package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.CallLog;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class MainActivityChild extends AppCompatActivity {

    //location
    Bundle extras = getIntent().getExtras();
    String location = extras.getString("location");
    //wifi
    WifiReceiver wifiReceiver = new WifiReceiver();
    Bundle extras1 = getIntent().getExtras();
    String wifiSituation = extras1.getString("wifi");
    //Airplane
    Bundle extras2 = getIntent().getExtras();
    String airPlane= extras2.getString("airPlane");
    AirplaneModeChangeReceiver airplaneModeChangeReceiver = new AirplaneModeChangeReceiver();
    //Battery
    private BroadcastReceiver mbcr=new BroadcastReceiver()
    {
        public void onReceive(Context c, Intent i)
        {
            int level=i.getIntExtra("level", 0);
            String tv = "Batterylevel:"+Integer.toString(level)+"%";
            String batteryLevel = tv.toString();
            if(level==100)
            {
                try
                {
                    AssetFileDescriptor afd=getAssets().openFd("small.mp4");
                    MediaPlayer mp=new MediaPlayer();
                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    mp.prepare();
                    mp.start();
                }
                catch(IOException e){}
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Battery
        BroadcastReceiver mbcr=new BroadcastReceiver()
        {
            public void onReceive(Context c, Intent i)
            {
                int level=i.getIntExtra("level", 0);
                String tv = "Batterylevel:"+Integer.toString(level)+"%";
                String batteryLevel = tv.toString();
                if(level==100)
                {
                    try
                    {
                        AssetFileDescriptor afd=getAssets().openFd("small.mp4");
                        MediaPlayer mp=new MediaPlayer();
                        mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        mp.prepare();
                        mp.start();
                    }
                    catch(IOException e){}
                }
            }
        };

        String message= "location"+location + ",last call"+ getLastCallNumber() + "airPlane Mode"+ airPlane+"wifi mode"+wifiSituation;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("childinfo").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    message.setText(snapshot.child("message").getValue().toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //airPlane
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneModeChangeReceiver, filter);
        //wifi
        IntentFilter filter1 = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiReceiver, filter1);
        //Battery
        registerReceiver(mbcr,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(airplaneModeChangeReceiver);
        unregisterReceiver(wifiReceiver);
        unregisterReceiver(mbcr);
    }

    //last call
    public String getLastCallNumber() {

        Uri contacts = CallLog.Calls.CONTENT_URI;
        Context context = null;
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permission denied can not get phone number", Toast.LENGTH_SHORT).show();
        }
        Cursor managedCursor = context.getContentResolver().query(
                contacts, null, null, null, null);
        int number = managedCursor.getColumnIndex( CallLog.Calls.NUMBER );
        String phoneNumber = null;
        if( managedCursor.moveToLast() == true ) {
            phoneNumber = managedCursor.getString( number );
        }
        managedCursor.close();
        return phoneNumber;
    }
}
