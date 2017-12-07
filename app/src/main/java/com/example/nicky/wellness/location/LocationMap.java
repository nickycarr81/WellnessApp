package com.example.nicky.wellness.location;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.nicky.wellness.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


/**
 * Created by Nicky November 2017
 * This class checks the permissions of the user/device
 * for maps to be called and the location of the device.
 * If successful the map will display the users location.
 * The class implements onMapReady to display a google map.
 */


public class LocationMap extends AppCompatActivity implements OnMapReadyCallback {

    /**
     * This function display the google map and
     * if permissions are granted will call the
     * getDeviceLocation().
     * @param googleMap
     */

    @Override
    public void onMapReady(GoogleMap googleMap){
        Toast.makeText(this,"Map is available",Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if(mLocationPermissionGranted){
           getDeviceLocation();

           if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                   != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                   Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
               return;

           }
           mMap.setMyLocationEnabled(true);
           mMap.getUiSettings().setMyLocationButtonEnabled(false);

        }
    }


    // This is all the constants and variables required for the class
    private static final String TAG = "Location";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private boolean mLocationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    /**
     * This function calls the getLocationPermission()
     * when the activity is created.
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        getLocationPermission();
    }

    /**
     * This function finds the devices location.
     * It will find the devices latitude and longitude
     * and pass to moveCamera to display the location
     * on the map.
     */

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the device's current location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,  "onComplete: found location ");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);

                        }else{
                            Log.d(TAG,  "onComplete: unable to find location ");
                            Toast.makeText(LocationMap.this, "Unable to get the current location", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }

        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }

    }


    /**
     * This function moves the camera based
     * on the parameters passed in.
     * @param latLng
     * @param zoom
     */
    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to: Lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }


    /**
     * This function displays the map by
     * finding the fragment id.
     */
    private void initMap() {
        Log.d(TAG, "initMap: initialising map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(LocationMap.this);
    }


    /**
     * This function checks permissions which
     * are held in the manifest
     * FINE
     * COARSE
     * INTERNET
     * to ensure the user/device can access maps.
     *
     */
    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }

        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }

    }


    /**
     * This function deals with the permission result
     * and initialises the map by calling initMap().
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        Log.d(TAG, "onRequestPermissionsResult: called");
        mLocationPermissionGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for (int i=0; i<grantResults.length; i++){
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionGranted = true;
                    // initialise map
                    initMap();

                }
            }
        }
    }



}
