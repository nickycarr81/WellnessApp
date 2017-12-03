package com.example.nicky.wellness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.nicky.wellness.assessment.Assessment;
import com.example.nicky.wellness.dashboard.DashBoard;
import com.example.nicky.wellness.location.Location;
import com.example.nicky.wellness.memberdetails.ListData;

/**
 * Created by Nicky Carr on 10/11/2017.
 * This is the main class for the app homepage.
 * This allows navigation to different activities
 * uisnf image buttons and intents.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /** Called when the user taps the view resource button*/
    public void viewResourcePage(View view){
        Intent intent = new Intent(this, ListData.class);
        startActivity(intent);
    }

    /** Called when the user taps the dashboard button*/
    public void viewDashPage(View view){
        Intent intent = new Intent(this, DashBoard.class);
        startActivity(intent);
    }

    /** Called when the user taps the location button*/
    public void viewLocationPage(View view){
        Intent intent = new Intent(this, Location.class);
        startActivity(intent);
    }

    /** Called when the user taps the assessment button*/
    public void viewAssessPage(View view){
        Intent intent = new Intent(this, Assessment.class);
        startActivity(intent);
    }
}
