package com.example.bjaso.cs3714finalproj.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by root on 4/25/17.
 */

public class trailsDB extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "trails.db";
    private static final int DATABASE_VERSION = 1;



//     creates a table for the local database that will save steps
//    TODO: look inside DBConstants and create a string that will create a table with the correct datatypes.
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + DBConstants.TABLE_NAME + "("
            + DBConstants.COLUMN_USERINFO_USERNAME + " varchar(255),"
            + DBConstants.COLUMN_USERINFO_PICTURE_URL + " varchar(255));";


//     This constructor requires Context
    public trailsDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


//     this method will execute SQL_CREATE_ENTRIES to create the table with all the rows
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// we don't wanna do upgrades for this simple db
    }
}
