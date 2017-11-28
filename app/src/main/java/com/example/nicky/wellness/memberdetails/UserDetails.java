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

public class UserDetails extends AppCompatActivity {

    private static final String TAG = "UserDetails";

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd, btnViewData;
    private EditText editText;
    private EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnViewData = (Button) findViewById(R.id.btnView);
        mDatabaseHelper = new DatabaseHelper(this);

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
                    toastMessage("Please enter the name of the new team member");
                }

            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetails.this, ListData.class);
                startActivity(intent);
            }
        });

    }

    public void AddData(String newEntry, String newEntry2) {
        boolean insertData = mDatabaseHelper.addData(newEntry, newEntry2);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
        Intent intent = new Intent(UserDetails.this, ListData.class);
        startActivity(intent);
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}

