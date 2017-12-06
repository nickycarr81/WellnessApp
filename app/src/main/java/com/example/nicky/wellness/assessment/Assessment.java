package com.example.nicky.wellness.assessment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicky.wellness.R;


public class Assessment extends AppCompatActivity {

    private EditText EditTextFatigue;
    private EditText EditTextSleepQuality;
    private EditText EditTextMuscleSoreness;
    private EditText EditTextStressLevels;
    private EditText EditTextMood;
    private Button AddButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        EditTextFatigue = (EditText) findViewById(R.id.fatigue);
        EditTextSleepQuality = (EditText) findViewById(R.id.SleepQuality);
        EditTextMuscleSoreness = (EditText) findViewById(R.id.MuscleSoreness);
        EditTextStressLevels = (EditText) findViewById(R.id.StressLevels);
        EditTextMood = (EditText) findViewById(R.id.Mood);
        AddButton = (Button) findViewById(R.id.btnAdd);


        AddButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String value1 = EditTextFatigue.getText().toString();
                String value2 = EditTextSleepQuality.getText().toString();
                String value3 = EditTextMuscleSoreness.getText().toString();
                String value4 = EditTextStressLevels.getText().toString();
                String value5 = EditTextMood.getText().toString();
                int a = Integer.parseInt(value1);
                int b = Integer.parseInt(value2);
                int c = Integer.parseInt(value3);
                int d = Integer.parseInt(value4);
                int e = Integer.parseInt(value5);
                int sum = a + b + c + d + e;

                if (sum <=10) Toast.makeText(Assessment.this,
                        "Rest required!",
                        Toast.LENGTH_LONG).show();
                else Toast.makeText(Assessment.this,
                        "Train as normal!",
                        Toast.LENGTH_LONG).show();


                //Toast.makeText(getApplicationContext(), String.valueOf(sum), Toast.LENGTH_LONG).show();
            }
        });
    }}
