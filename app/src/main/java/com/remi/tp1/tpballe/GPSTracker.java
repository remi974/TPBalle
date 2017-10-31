package com.remi.tp1.tpballe;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Rémi on 11/09/2017.
 */

public class GPSTracker extends Service implements LocationListener {

    /**
     * Application context.
     */
    private final Context mContext;

    /**
     * Flag for GPS status.
     *
     */
    private boolean isGPSEnabled = false;

    /**
     * Flag for network status.
     *
     */
    private boolean isNetworkEnabled = false;

    /**
     * Flag for location status.
     */
    private boolean canGetLocation = false;

    /**
     * Current Location.
     */
    private Location location; // location

    /**
     * Current latitude.
     */
    private double latitude; // latitude

    /**
     * Current longitude.
     */
    private double longitude; // longitude

    /**
     * The minimum distance to change Updates in meters.
     */
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    /**
     * The minimum time between updates in milliseconds.
     */
    private static final long MIN_TIME_BW_UPDATES = 1000 * 10 * 3; // 30 seconds

    /**
     * Declaring a Location Manager.
     */
    protected LocationManager locationManager;

    /**
     * Constructor using application context as parameter.
     * @param context Application context needed to initialize GPS.
     */
    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    /**
     * Perform tasks to get current location using GPS.
     * @return Current location or null.
     */
    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {

                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();


                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                                String msg = String.format("(%.2f, %.2f)",
                                        location.getLatitude(), location.getLongitude());
                                Log.i("latitude", "latitude : " +  Double.toString(latitude));
                                Log.i("longitude", "longitude : " + Double.toString(longitude));
                                Log.i("message", msg);
                            }
                        }
                    }
                }
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Function to get latitude.
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude.
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check if network or gps are enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app.
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    /**
     *
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {

        Log.d("GPS :: ", "Location changed");
        // check if GPS enabled
        /*if(this.canGetLocation()){

            double latitude = this.getLatitude();
            double longitude = this.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings

        }*/
    }

    /**
     *
     * @param s
     * @param i
     * @param bundle
     */
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    /**
     *
     * @param s
     */
    @Override
    public void onProviderEnabled(String s) {

    }

    /**
     *
     * @param s
     */
    @Override
    public void onProviderDisabled(String s) {

    }
}
