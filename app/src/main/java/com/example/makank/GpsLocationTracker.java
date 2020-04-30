package com.example.makank;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;

public class GpsLocationTracker extends Service implements LocationListener {


    private Context mContext;

    /**
     * flag for gps status
     */
    private boolean isGpsEnabled = false;

    /**
     * flag for network status
     */
    private boolean isNetworkEnabled = false;

    /**
     * flag for gps
     */
    private boolean canGetLocation = false;

    /**
     * location
     */
    private Location mLocation;

    /**
     * latitude
     */
    private double mLatitude;

    /**
     * longitude
     */
    private double mLongitude;

    /**
     * min distance change to get location update
     */
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATE = 10;

    /**
     * min time for location update
     * 60000 = 1min
     */
    private static final long MIN_TIME_FOR_UPDATE = 60000;

    /**
     * location manager
     */
    private LocationManager mLocationManager;


    /**
     * @param mContext constructor of the class
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public GpsLocationTracker(Context mContext) {

        this.mContext = mContext;
        getLocation();
    }


    /**
     * @return
     */
    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public Location getLocation() {

        try {

            mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            /*getting status of the gps*/
            isGpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            /*getting status of network provider*/
            isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGpsEnabled && !isNetworkEnabled) {

                /*no location provider enabled*/
            } else {

                this.canGetLocation = true;

                /*getting location from network provider*/
                if (isNetworkEnabled) {

                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_CHANGE_FOR_UPDATE, this);

                    if (mLocationManager != null) {

                        mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (mLocation != null) {

                            mLatitude = mLocation.getLatitude();

                            mLongitude = mLocation.getLongitude();
                        }
                    }

                    if (mLocation != null) {

                        mLatitude = mLocation.getLatitude();

                        mLongitude = mLocation.getLongitude();
                    }
                }
                /*if gps is enabled then get location using gps*/
                if (isGpsEnabled) {

                    if (mLocation == null) {

                        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_CHANGE_FOR_UPDATE, this);

                        if (mLocationManager != null) {

                            mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if (mLocation != null) {

                                mLatitude = mLocation.getLatitude();

                                mLongitude = mLocation.getLongitude();
                            }

                        }
                    }

                }
            }


        } catch (Exception e) {

            e.printStackTrace();
        }

        return mLocation;
    }

    /**
     * call this function to stop using gps in your application
     */
    public void stopUsingGps() {

        if (mLocationManager != null) {

            mLocationManager.removeUpdates(GpsLocationTracker.this);

        }
    }

    /**
     * @return latitude
     *         <p/>
     *         function to get latitude
     */
    public double getLatitude() {

        if (mLocation != null) {

            mLatitude = mLocation.getLatitude();
        }
        return mLatitude;
    }

    /**
     * @return longitude
     *         function to get longitude
     */
    public double getLongitude() {

        if (mLocation != null) {

            mLongitude = mLocation.getLongitude();

        }

        return mLongitude;
    }

    /**
     * @return to check gps or wifi is enabled or not
     */
    public boolean canGetLocation() {

        return this.canGetLocation;
    }

    /**
     * function to prompt user to open
     * settings to enable gps
     */
    public void showSettingsAlert() {

        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(new ContextThemeWrapper(mContext, R.style.AppTheme));

        mAlertDialog.setTitle("Gps Disabled");

        mAlertDialog.setMessage("gps is not enabled . do you want to enable ?");

        mAlertDialog.setPositiveButton("settings", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Intent mIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(mIntent);
            }
        });

        mAlertDialog.setNegativeButton("cancle", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();

            }
        });

        final AlertDialog mcreateDialog = mAlertDialog.create();
        mcreateDialog.show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

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
}
