package com.example.nicky.wellness.memberdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicky.wellness.DatabaseHelper;
import com.example.nicky.wellness.R;

/**
 * Created by Nicky Carr on 10/11/2017.
 * This is the EditData class.
 * This will allow members details to be displayed
 * and updated or deleted.
 */
public class EditData extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";                           // Tag name for the class

    private Button btnSave,btnDelete;                                               // Buttons to amend and delete members
    private EditText editable_item, editable_item2,editable_item3;                                 // Text fields to display and update data
    DatabaseHelper mDatabaseHelper;                                                 // DB helper class

    private String selectedName;                                                    // String name
    private String selectedMobile;                                                  // String mobile
    private String selectedEmail;
    private int selectedID;                                                         // Int ID

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        btnSave = (Button) findViewById(R.id.btnSave);                              // find button id and assign
        btnDelete = (Button) findViewById(R.id.btnDelete);                          // find button id and assign
        editable_item = (EditText) findViewById(R.id.editable_item);                // find text id and assign
        editable_item2 = (EditText) findViewById(R.id.editable_item2);              // find text id and assign
        editable_item3 = (EditText) findViewById(R.id.editText10);
        mDatabaseHelper = new DatabaseHelper(this);


        Intent receivedIntent = getIntent();                                        // Get the intent extra from the ListDataActivity
        selectedID = receivedIntent.getIntExtra("id",-1);                           // Get the itemID passed as an extra
        selectedName = receivedIntent.getStringExtra("name");                       // Get the name passed as an extra
        selectedMobile = receivedIntent.getStringExtra("mobile");
        selectedEmail = receivedIntent.getStringExtra("email");
        editable_item.setText(selectedName);                                        // Set the text to show the current selected name
        editable_item2.setText(selectedMobile);                                     // Set the text to show the current selected names mobile
        editable_item3.setText(selectedEmail);


        /**
         * btnSave onclick listener function.
         * This function updates the members details
         * by calling the updateName() function and
         * start the list activity. A toast message
         * is also displayed if nothing entered.
         */
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editable_item.getText().toString();
                if(!item.equals("")){
                    mDatabaseHelper.updateName(item,selectedID,selectedName);
                    toastMessage("Member details updated");
                    Intent intent = new Intent(EditData.this, ListData.class);
                    startActivity(intent);
                }else{
                    toastMessage("You must enter a name");
                }
            }
        });

        /**
         * btnDelete onclick listener function.
         * This function deletes the members details
         * by calling the deleteName() function and
         * start the list activity. A toast message
         * is also displayed if nothing entered.
         */
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteName(selectedID,selectedName);
                editable_item.setText("");
                toastMessage("Member removed from list");
                Intent intent = new Intent(EditData.this, ListData.class);
                startActivity(intent);
            }
        });

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
