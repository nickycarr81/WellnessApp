package com.example.nicky.wellness.memberdetails;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicky.wellness.DatabaseHelper;
import com.example.nicky.wellness.R;

/**
 * Created by Nicky Carr on 10/11/2017.
 * This is the UserDetails class
 * This will allow input of personal details
 * to generate a new member - name, mobile &
 * email.
 */

public class UserDetails extends AppCompatActivity {

    private static final String TAG = "UserDetails";                // Tag name for the class

    DatabaseHelper mDatabaseHelper;                                 // SQLite db object
    private Button btnAdd, btnViewData;                             // Buttons to add and view members
    private EditText editText, editText2;                           // Text fields to data input


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);


        editText = (EditText) findViewById(R.id.editText);          // find text id and assign
        editText2 = (EditText) findViewById(R.id.editText2);        // find text id and assign
        btnAdd = (Button) findViewById(R.id.btnAdd);                // find button id and assign
        btnViewData = (Button) findViewById(R.id.btnView);          // find button id and assign
        mDatabaseHelper = new DatabaseHelper(this);                 // create new db helper object


        /**
         * btnAdd onclick listener function.
         * Gets the values of the edit text
         * fields and makes them strings.
         * If the fields are not empty AddData()
         * is called.
         */
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                String newEntry2 = editText2.getText().toString();
                if (editText.length() != 0) {
                    AddData(newEntry, newEntry2);
                    editText.setText("");
                    editText2.setText("");
                } else {
                    toastMessage("Please enter the name and contact details of the new team member");
                }

            }
        });

        /**
         * btnViewData onclick listener function.
         * new intent object created to move to
         * the list data activity.
         */
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetails.this, ListData.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Function which receives the inputs of the
     * text fields as string parameters. The function
     * then creates a boolean and calls the addData
     * function in the data base helper class.
     * The return value from addData will display a message if
     * successful or not.
     * @param newEntry
     * @param newEntry2
     */
    public void AddData(String newEntry, String newEntry2) {
        boolean insertData = mDatabaseHelper.addData(newEntry, newEntry2);

        if (insertData) {
            toastMessage("New member details added successfully.");
        } else {
            toastMessage("There has been an issue saving the member details, please try again.");
        }
        Intent intent = new Intent(UserDetails.this, ListData.class);
        startActivity(intent);
    }

    /**
     * This is a toast function which receives
     * a string parameter to display
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}

