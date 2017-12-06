package com.example.nicky.wellness;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.nicky.wellness.R;

public class IntervalTrainer extends AppCompatActivity {


    //==========================================================
    //               Declaration of Variables
    //==========================================================
    private EditText WorkTimeInput;      // How long to run
    private EditText RestTimeInput;      // How long to walk/rest
    private Button Start;           // Start the timer
    private Button Stop;            // Stop the timer
    private TextView Status;        // Displays current state(work/rest)
    private TextView ClockDisplay;  // Where timer will show

    private CountDownTimer workTimer; // These are the 2 different timers
    private CountDownTimer restTimer;

    int hundrethsLeft;  // used for keeping track of time in timers

    int workTime;  // This will be set by reading user input
    int restTime;  // This will be set by reading user input

    boolean invalidInput = false;

    private MediaPlayer startSound;
    private MediaPlayer stopSound;

    /*
Problem: After entering values in the app and clicking "Start" button the keyboard would
remain on screen and block view of the timer. This was very annoying. This code allows for
the keyboard to be removed after clicking "Start".
 */
    private LinearLayout mainLayout;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval_trainer);

        //==========================================================
        //                Beginning of my Code
        //==========================================================
        mainLayout = (LinearLayout)findViewById(R.id.SetWork_LL); // used for removing keyboard
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);


        // For getting input from user
        WorkTimeInput = (EditText)findViewById(R.id.WorkTime);
        RestTimeInput = (EditText)findViewById(R.id.RestTime);

        // Setup Status bar
        Status = (TextView)findViewById(R.id.Status);
        // Where countdown will be displayed
        ClockDisplay = (TextView)findViewById(R.id.ClockDisplay);

        startSound = MediaPlayer.create(getApplicationContext(), R.raw.start_bell_short);
        stopSound = MediaPlayer.create(getApplicationContext(), R.raw.stop_bell_short);


        //==========================================================
        //                    Start Button
        //==========================================================
        // Tried several methods to try and handle a ":" being entered
        // instead of a number but the app just crashes, can't find
        // a way to handle it
        Start = (Button) findViewById(R.id.StartButton);
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // close keyboard
                imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
                // Get "work" time from user input
                String val1 = WorkTimeInput.getText().toString();
                // Make sure WorkTime is not empty
                if (val1.isEmpty()){
                    Toast.makeText(getApplicationContext(), getText(R.string.error_WorkInput), Toast.LENGTH_SHORT).show();
                }else{
                    // Disable start button to avoid odd app behaviour/sound spam
                    Start.setEnabled(false);
                    Stop.setEnabled(true);

                    workTime = Integer.valueOf(val1);

                    // Get "rest" time from user input
                    String val2 = RestTimeInput.getText().toString();
                    // If "Rest" time is empty set to 0 and app will loop over work time
                    if(val2.isEmpty()){
                        restTime = 0;
                    }else{
                        restTime = Integer.valueOf(val2);
                    }
                    // Start the interval trainer
                    startTimer(workTime * 1000, restTime * 1000);
                }

            }
        });

        //==========================================================
        //                    Stop Button
        //==========================================================
        Stop = (Button)findViewById(R.id.StopButton);
        Stop.setEnabled(false);
        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workTimer.cancel();
                restTimer.cancel();
                ClockDisplay.setText(getText(R.string.ZeroTime));
                Start.setEnabled(true);
                Stop.setEnabled(false);
            }
        });
    }

    //================================================================================
    //                               Start Timer
    //================================================================================
    //                              Work Time Setup
    //--------------------------------------------------------------------------------
    public void startTimer(int work, final int rest){
        workTimer = new CountDownTimer(work, 10) {
            //---------------------
            //       OnTick
            //---------------------
            @Override
            public void onTick(long ms) {    // ms = millisTillFinished
                /*
                The native android countdown is not accurate and therefore counts the last second
                twice due to this error. The piece of math code below compensates for this error so
                long as the phone's CPU is not under too much stress apparently.
                Credit: https://stackoverflow.com/questions/6810416/android-countdowntimer-shows-1-for-two-seconds/6811744#6811744
                 */
                if(Math.round((float)ms / 1000.0f) != hundrethsLeft){
                    hundrethsLeft = Math.round((float)ms / 10.0f);
                    String displaySeconds = String.valueOf(hundrethsLeft / 100);
                    String displayHundredths = String.valueOf(hundrethsLeft % 100);
                    String totalDisplay;

                    /*
                    It took some time to work this section out as I was evaluating the seconds first
                    working left to right but you need to start with the smallest units first and
                    work right to left along the numbers to decide where a "0" needs to be added
                    to the display.
                     */
                    if ((hundrethsLeft % 100) < 10){  // If < 10 hundredths of second left add a "0"in the "tenths" column.
                        if(hundrethsLeft < 1000){     // If < 10 seconds left add a "0" in the "tens" column
                            totalDisplay = "0" + displaySeconds + ":0" + displayHundredths;
                            ClockDisplay.setText(totalDisplay);
                        }else{
                            totalDisplay = displaySeconds + ":0" + displayHundredths;
                            ClockDisplay.setText(totalDisplay);
                        }
                    }else{
                        if(hundrethsLeft < 1000){
                            totalDisplay = "0" + displaySeconds + ":" + displayHundredths;
                            ClockDisplay.setText(totalDisplay);
                        }else{
                            totalDisplay = displaySeconds + ":" + displayHundredths;
                            ClockDisplay.setText(totalDisplay);
                        }
                    }

                }
            }
            //---------------------
            //       OnFinish
            //---------------------
            @Override
            public void onFinish() {
                // zero the clock
                ClockDisplay.setText(getText(R.string.ZeroTime));
                workTimer.cancel();

                // restart work timer if there is no rest period
                if (rest == 0){
                    startSound.start();
                    workTimer.start();
                }else{
                    stopSound.start();
                    //Status.setTextColor(getColor(R.color.RestColour));
                    Status.setText(getText(R.string.RestStatus));
                    restTimer.start();
                }

            }
        };
        //================================================================================
        //                            Rest Time Setup
        //--------------------------------------------------------------------------------
        // This is basically a stripped down version of the work timer
        restTimer = new CountDownTimer(rest, 10) {
            //---------------------
            //       OnTick
            //---------------------
            @Override
            public void onTick(long ms) {    // ms = millisTillFinished
                if(Math.round((float)ms / 1000.0f) != hundrethsLeft){
                    hundrethsLeft = Math.round((float)ms / 10.0f);
                    String displaySeconds = String.valueOf(hundrethsLeft / 100);
                    String displayHundredths = String.valueOf(hundrethsLeft % 100);
                    String totalDisplay;

                    if ((hundrethsLeft % 100) < 10){  // If < 10 hundredths of second left add a "0"in the "tenths" column.
                        if(hundrethsLeft < 1000){     // If < 10 seconds left add a "0" in the "tens" column
                            totalDisplay = "0" + displaySeconds + ":0" + displayHundredths;
                            ClockDisplay.setText(totalDisplay);
                        }else{
                            totalDisplay = displaySeconds + ":0" + displayHundredths;
                            ClockDisplay.setText(totalDisplay);
                        }
                    }else{
                        if(hundrethsLeft < 1000){
                            totalDisplay = "0" + displaySeconds + ":" + displayHundredths;
                            ClockDisplay.setText(totalDisplay);
                        }else{
                            totalDisplay = displaySeconds + ":" + displayHundredths;
                            ClockDisplay.setText(totalDisplay);
                        }
                    }

                }
            }
            //---------------------
            //       OnFinish
            //---------------------
            @Override
            public void onFinish() {
                ClockDisplay.setText(getText(R.string.ZeroTime));
                //Status.setTextColor(getColor(R.color.WorkColour));
                Status.setText(getText(R.string.WorkStatus));
                restTimer.cancel();
                startSound.start();
                workTimer.start();
            }
        };
        startSound.start();
        workTimer.start();

    }   //--------------------------------------------------------------------------------
    //                               End of My app
} //--------------------------------------------------------------------------------







