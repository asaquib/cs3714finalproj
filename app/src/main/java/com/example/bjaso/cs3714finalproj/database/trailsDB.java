package com.example.bjaso.cs3714finalproj.database;

/**
 * Created by root on 4/25/17.
 */

public class trailsDB {
    private static final String DATABASE_NAME = "trails.db";
    private static final int DATABASE_VERSION = 1;



    // creates a table for the local database that will save steps
    //TODO: look inside DBConstants and create a string that will create a table with the correct datatypes.
//    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + DBConstants.TABLE_NAME + "("
//            + DBConstants.COLUMN_NAME_ENTRY_ID + " INTEGER PRIMARY KEY,"
//            + DBConstants.COLUMN_NAME_ENTRY_DATE + " varchar(255),"
//            + DBConstants.COLUMN_NAME_ENTRY_DATE_EPOCH + " bigint,"
//            + DBConstants.COLUMN_NAME_TOTAL_VALUE + " int, "
//            + DBConstants.COLUMN_NAME_INCREMENT_VALUE + " smallint,"
//            + DBConstants.COLUMN_NAME_STATUS + " boolean);";


    // This constructor requires Context
//    public StepCountDB(Context context){
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }


    // this method will execute SQL_CREATE_ENTRIES to create the table with all the rows
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(SQL_CREATE_ENTRIES);


//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// we don't wanna do upgrades for this simple db
//    }
}
