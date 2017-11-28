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
 * Created by User on 2/28/2017.
 */

public class EditData extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";

    private Button btnSave,btnDelete;
    private EditText editable_item;
    private EditText editable_item2;

    DatabaseHelper mDatabaseHelper;

    private String selectedName;
    private String selectedMobile;
    private int selectedID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        editable_item = (EditText) findViewById(R.id.editable_item);
        editable_item2 = (EditText) findViewById(R.id.editable_item2);
        mDatabaseHelper = new DatabaseHelper(this);

        //get the intent extra from the ListDataActivity
        Intent receivedIntent = getIntent();

        //now get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id",-1); //NOTE: -1 is just the default value

        //now get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name");
        selectedMobile = receivedIntent.getStringExtra("mobile");

        //set the text to show the current selected name
        editable_item.setText(selectedName);
        editable_item2.setText(selectedMobile);

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
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
