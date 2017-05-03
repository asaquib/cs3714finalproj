package com.example.bjaso.cs3714finalproj.database;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.bjaso.cs3714finalproj.data.Users;
import com.example.bjaso.cs3714finalproj.service.backgroundService;
import com.example.bjaso.cs3714finalproj.socketio.socketIO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.socket.client.Socket;

/**
 * Created by root on 4/25/17.
 */

public class DBController {
    private SQLiteDatabase db;
    private trailsDB db_helper;
    private volatile Boolean processing = false;
    private backgroundService background_service;
    private Context context;
    private Socket mSocket;

    public DBController(Context context, backgroundService background_service, Application application) {
        db_helper = new trailsDB(context);
        this.background_service = background_service;
        this.context = context;

        socketIO app = (socketIO) application;
        mSocket = app.getSocket();
        mSocket.connect();
    }

    public void insertInfo(final ArrayList<ContentValues> newusers)
    {
        Log.d("db","insert users called id db open ?"+db.isOpen()+" IsFree to write? "+IsFree());
        for(int i = 0; i < newusers.size(); i++)
        {
            db.insert(DBConstants.TABLE_NAME ,null, newusers.get(i));
        }

        UploadUsers();
    }
    //     You need to call this open up the database
    public void OpenDB() {
        db = db_helper.getWritableDatabase();
    }


    // When the app is no longer using the database you must release the resources. This method will do that for you.
    public void CloseDB() {
        db.close();
        db = null;
    }


    // To avoid any complications that could happen due to simultaneous write/read operations you must make sure to check if the DB is not busy.
    private boolean IsFree() {
        return !processing;
    }

    private void UploadUsers() {

        Log.d("db", "Upload user called" + " isFree " + IsFree());

        if (db.isOpen() && IsFree()) {

            new AsyncTask<Void, Void, Void>() {

                //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                Cursor cursor = null;
                //Float total = new Float(0f);
                JSONObject data;


                @Override
                protected Void doInBackground(Void... params) {
                    if (!mSocket.connected())
                        mSocket.connect();


                    JSONObject data;

                    processing = true;

                    cursor = db.rawQuery("SELECT * FROM " + DBConstants.TABLE_NAME, null);


                    if (cursor.moveToFirst()) {
                        do {
                            String username = cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_USERINFO_USERNAME));
                            String imageuri = cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_USERINFO_PICTURE_URL));
                            //String email = cursor.getString(cursor.getColumnIndex(DBConstants.COLUM_USERINFO_EMAIL));
                            data = new JSONObject();
                            Log.d("db", "Steps:" + username);
                            try {

                                data.put("username", username);
                                data.put("userimage", imageuri);
                               // data.put("email", email);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            if (mSocket != null) {
                                mSocket.emit("User", data);


                            }

                        } while (cursor.moveToNext());
                    }
                    cursor.close();

                    processing = false;


                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {


                }

            }.execute();

        }
    }
    public ArrayList<Users> readUsers() {

        Log.d("db", "Read users called" + " isFree " + IsFree());
        final ArrayList<Users> members = new ArrayList<Users>();
        if (db.isOpen() && IsFree()) {

            new AsyncTask<Void, Void, Void>() {

                //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                Cursor cursor = null;


                @Override
                protected Void doInBackground(Void... params) {
                    if (!mSocket.connected())
                        mSocket.connect();

                    processing = true;

                    cursor = db.rawQuery("SELECT * FROM " + DBConstants.TABLE_NAME, null);

                    if (cursor.moveToFirst()) {
                        do {
                            String username = cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_USERINFO_USERNAME));
                            String imageuri = cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_USERINFO_PICTURE_URL));
                            //String email = cursor.getString(cursor.getColumnIndex(DBConstants.COLUM_USERINFO_EMAIL));
                            Users member = new Users(username, imageuri);
                            members.add(member);


                        } while (cursor.moveToNext());
                    }
                    cursor.close();

                    processing = false;


                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {


                }

            }.execute();

        }
        return members;
    }
}
