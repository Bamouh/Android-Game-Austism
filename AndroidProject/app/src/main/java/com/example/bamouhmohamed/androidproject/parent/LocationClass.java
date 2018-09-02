package com.example.bamouhmohamed.androidproject.parent;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by BamouhMohamed on 15/06/2018.
 */

//Classe pour la detection de la position pour
public class LocationClass implements LocationListener {

    LocationManager locationManager;
    Location location;
    public double lat=0.0;
    public double longi=0.0;
    boolean isGPS = false;
    boolean isNetwork = false;

    public LocationClass(Context c,Activity a){
        //Permissions
        if ( ContextCompat.checkSelfPermission( c, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( a, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},99);
        }
        if ( ContextCompat.checkSelfPermission( c, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( a, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},99);
        }
        locationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
        isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        System.out.println(isGPS);
        System.out.println(isNetwork);
        getLocation();
    }
    @Override
    public void onLocationChanged(Location location) {
        this.location=location;
        lat=this.location.getLatitude();
        longi=this.location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {
        getLocation();
    }

    @Override
    public void onProviderDisabled(String s) {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    public void getLocation(){
        try {
            if(isGPS) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        this.lat = location.getLatitude();
                        this.longi = location.getLongitude();
                    }
                }
            }
            else if(isNetwork) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, this);
                if (locationManager != null) {
                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        this.lat = location.getLatitude();
                        this.longi = location.getLongitude();
                    }
                }
            }
        }catch(SecurityException e){e.printStackTrace();}
    }
}
