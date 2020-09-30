package com.example.lt4_android_p2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
    }

    public void gps(View view) {
        //kiem tra da cap quyen chua
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission
                (this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            LocationManager locationManager  = (LocationManager)getSystemService(Context.LOCATION_SERVICE);


/*            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            double latitude = location.getLatitude();
            double longtitude = location.getLongitude();
            tv.setText(latitude + " - " + longtitude);*/

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longtitude = location.getLongitude();

                    tv.setText(String.valueOf(latitude) + " - " + String.valueOf(longtitude));
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }

            });
        }
        //neu chua thi xin quyen
        else{
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            },999);
        }


    }

    public void checkInternet(View view) {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        NetworkInfo data = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnected()){
            tv.setText("ket noi bang wifi");
        }else  if (data.isConnected()){
            tv.setText("Ket noi bang 3g");
        }else{
            tv.setText("Khong co mang");
        }

    }
}
