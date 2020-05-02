package com.example.leonocio;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

public class MyLocationListener implements LocationListener {

    private Context ctx;

    public void onLocationChanged(Location loc) {
        String message = String.format(
                "New Location \n Longitude: %1$s \n Latitude: %2$s",
                loc.getLongitude(), loc.getLatitude()
        );
        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
    }
    public void onProviderDisabled(String arg0) {

    }
    public void onProviderEnabled(String provider) {

    }
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void set_conetext(Context ctx_arg){
        ctx = ctx_arg;
    }
}
