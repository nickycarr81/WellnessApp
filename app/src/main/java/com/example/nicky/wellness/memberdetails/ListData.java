package com.example.nicky.wellness.memberdetails;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nicky.wellness.DatabaseHelper;
import com.example.nicky.wellness.R;

import java.util.ArrayList;

/**
 * Created by Nicky Carr on 10/11/2017.
 * This is the ListData class.
 * This will allow the display of the members in a list
 * format.
 */

public class ListData extends AppCompatActivity {

    private static final String TAG = "ListData";                       // Tag name for the class
    DatabaseHelper mDatabaseHelper;                                     // SQLite db object
    private ListView mListView;                                         // a List to view member details

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);


        mListView = (ListView) findViewById(R.id.listView);             // find list id and assign
        mDatabaseHelper = new DatabaseHelper(this);                     // create new db helper object
        populateListView();                                             // onCreate method  - Calls the populateListView()
    }

    /**
     * This function retrieves the data from the db
     * and attaches it to the list. An array list is then created
     * and the data is added to the arraylist.
     */

    private void populateListView() {
        // This log prints to the console so you can check the right values are being passed
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        final Cursor data = mDatabaseHelper.getData();                       // DM class function getData is called and assigns to a cursor
        final ArrayList<String> listData = new ArrayList<>();                // New ArrayList is created
        while(data.moveToNext()){                                            // Get the value from the table in column 1

            //Log.d(TAG,data.getString(2) + data.getString(3));                // Used to ensure mobile number is there in console.
            listData.add(data.getString(1)); //+ "\n" + data.getString(2));  // Add it to the ArrayList
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);      // Creates the list adapter
        mListView.setAdapter(adapter);                                                                      // Sets the adapter

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {                            // Set an onItemClickListener to the ListView
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();

                Cursor data = mDatabaseHelper.getItemID(name);                                               // Get the id associated with that name
                //Cursor data2 = mDatabaseHelper.getData();
                //String mobile = data.getString(2).toString();
                //String email = data.getString(3).toString();
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                   // editDataIntent(itemID);
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    //String mobile = data.getString(2);
                   Intent editScreenIntent = new Intent(ListData.this, EditData.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name",name);
                   // editScreenIntent.putExtra("mobile",mobile);
                   // editScreenIntent.putExtra("email",email);
                   startActivity(editScreenIntent);
                    //finish();
                }
                else{
                    toastMessage("No ID associated with that name");
                }
            }
        });
    }

   /* private void editDataIntent(int itemID){
        final Cursor dataTest = mDatabaseHelper.getData2(itemID);
        String name = dataTest.getString(1).toString();
        String mobile = dataTest.getString(2).toString();
        String email = dataTest.getString(3).toString();

        Intent editScreenIntent = new Intent(ListData.this, EditData.class);
        editScreenIntent.putExtra("id",itemID);
        editScreenIntent.putExtra("name",name);
        editScreenIntent.putExtra("mobile",mobile);
        editScreenIntent.putExtra("email",email);
        startActivity(editScreenIntent);
    }*/

    /**
     * This is a toast function which receives
     * a string parameter to display
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    /**
     *  Called when the user taps the add member button
     */
    public void addUserNavigation(View view){
        Intent intent = new Intent(this, UserDetails.class);
        startActivity(intent);
    }


}
