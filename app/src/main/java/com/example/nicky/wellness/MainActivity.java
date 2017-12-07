package com.example.nicky.wellness;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.nicky.wellness.assessment.Assessment;
import com.example.nicky.wellness.dashboard.DashBoard;
import com.example.nicky.wellness.location.LocationMap;
import com.example.nicky.wellness.memberdetails.ListData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by Nicky Carr on 10/11/2017.
 * This is the main class for the app homepage.
 * This allows navigation to different activities
 * using image buttons and intents. It will also check
 * google play version to ensure maps can run on the device.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOGUE_REQUEST = 9001;


    /**
     * onCreate checks calls the isServiceOK()
     * and acts accordingly
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isServicesOK()){
            init();
        }
    }


    /**
     * This function initialises google maps
     * and onclick of the image button calls
     * an intent to move to the maps page.
     */
    private void init(){
        ImageButton btnMap = (ImageButton) findViewById(R.id.imageButton3);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LocationMap.class);
                startActivity(intent);

            }
        });
    }



    /**
     * This function checks the device has the correct
     * google play version to view google maps. If not
     * a dialogue will display to advise the user to
     * update or the maps just wont work.
     */

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS){
            //User can make the map request
            Log.d(TAG, "isServiceOK: Google Play Services are working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            // an error occurred but it can be resolved
            Log.d(TAG, "isServiceOK: An error occurred but it can be resolved");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOGUE_REQUEST);
            dialog.show();

        }else{
            Toast.makeText(this,"You cant make map requests",Toast.LENGTH_SHORT).show();
        }
        return false;
    }



    /** Called when the user taps the view resource button*/
    public void viewResourcePage(View view){
        Intent intent = new Intent(this, ListData.class);
        startActivity(intent);
    }

    /** Called when the user taps the dashboard button*/
    public void viewDashPage(View view){
        Intent intent = new Intent(this, IntervalTrainer.class);
        startActivity(intent);
    }

    /** Called when the user taps the location button
    public void viewLocationPage(View view){
        Intent intent = new Intent(this, Location.class);
        startActivity(intent);
    }*/

    /** Called when the user taps the assessment button*/
    public void viewAssessPage(View view){
        Intent intent = new Intent(this, Assessment.class);
        startActivity(intent);
    }
}
