package com.example.nicky.wellness;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Nicky Carr on 10/11/2017.
 * This is the main class for the SQLite DB
 * This will create the tables for the members
 * and assessment details.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";                     // Tag name for the class

    //Database Values
    private static final String DATABASE_NAME = "teamMember.db";
    private static final int DATABASE_VERSION = 2;

    // Team Member Table Details
    private static final String TABLE_NAME = "member";                      // DB table name
    private static final String COL1 = "ID";                                // Column 1 name of table - Primary key
    private static final String COL2 = "name";                              // Column 2 name of table - Team Member name
    private static final String COL3 = "mobile";                            // Column 3 name of table - Team Member mobile number

    /**
     * Constructor for the helper class
     *
     * @param context
     */

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * DB onCreate Function called when activity loads
     * @param db
     * String createTable passed generate the DB table Member.
     **/
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, MOBILE TEXT)";
        db.execSQL(createTable);
    }

    /**
     * Function to create a new version of the database when the version is changed
     * The current table is dropped and new version created.
     * @param db
     * @param oldVersion
     * @param newVersion
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Function to add data to the db table
     * ContentValues are based on the input form the user
     * and passed as a string parameter.
     * @param item
     * @param item2
     * @return Boolean
     */

    public boolean addData(String item, String item2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);
        contentValues.put(COL3, item2);

        // This log prints to the console so you can check the right values are being passed
        // Example of this is the string items being added to the db table
        Log.d(TAG, "addData: Adding " + item + item2 + " to " + TABLE_NAME);

       long result = db.insert(TABLE_NAME, null, contentValues);

        //The db insert function will return -1 if it fails.
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Function to return all data from the specific table
     * @return data
     */
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    /**
     * Function to return only the ID that matches the name passed in
     * @param name
     * @return data
     */
    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Updates the name field
     * @param newName
     * @param id
     * @param oldName
     */
    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    /**
     * Delete from database
     * @param id
     * @param name
     */
    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }

}
























