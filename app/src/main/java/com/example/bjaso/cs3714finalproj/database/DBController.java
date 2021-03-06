package com.example.bjaso.cs3714finalproj.database;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.bjaso.cs3714finalproj.data.Users;
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
    //private backgroundService background_service;
    private Context context;
    private Socket mSocket;
    boolean start;

    public DBController(Context context, Application application) {
        db_helper = new trailsDB(context);
        //this.background_service = background_service;
        this.context = context;
        start = false;

        socketIO app = (socketIO) application;
        mSocket = app.getSocket();
        mSocket.connect();
    }

    public void insertInfo(final ArrayList<ContentValues> newusers)
    {
        Log.d("db","insert users called id db open ?"+db.isOpen()+" IsFree to write? "+IsFree());
        if(!start && newusers.get(0).get(DBConstants.COLUMN_USERINFO_USERNAME)!= null)
        {
            db.insert(DBConstants.TABLE_NAME ,null, newusers.get(0));
            start = true;
        }

        UploadUsers(newusers);
    }
    //     You need to call this open up the database
    public void OpenDB() {
        db = db_helper.getWritableDatabase();
    }

    public void removeAll(){
        db.delete(DBConstants.TABLE_NAME, null, null);
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

    private void UploadUsers(final ArrayList<ContentValues> newusers) {

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

                    boolean flag = true;
                    JSONObject data;

                    processing = true;

                    cursor = db.rawQuery("SELECT * FROM " + DBConstants.TABLE_NAME, null);


                    if (cursor.moveToFirst()) {
                        do {
                            String username = cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_USERINFO_USERNAME));
                            String imageuri = cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_USERINFO_PICTURE_URL));
                            //String email = cursor.getString(cursor.getColumnIndex(DBConstants.COLUM_USERINFO_EMAIL));
                            if ( username.equals(newusers.get(0).get(DBConstants.COLUMN_USERINFO_USERNAME))
                                    || imageuri.equals(newusers.get(0).get(DBConstants.COLUMN_USERINFO_PICTURE_URL)))
                            {
                                flag = false;
                            }
                            data = new JSONObject();
                            Log.d("db", "UserName:" + username);
                            try {

                                data.put("username", username);
                                data.put("userimage", imageuri);
                               // data.put("email", email);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            if (mSocket != null) {
                                mSocket.emit("UsersModel", data);


                            }

                        } while (cursor.moveToNext());
                        if(flag)
                            db.insert(DBConstants.TABLE_NAME ,null, newusers.get(0));
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

//                    mSocket.on("UsersModel", new Emitter.Listener() {
//                        @Override
//                        public void call(Object... args) {
//                            Log.d("db", "Read users called");
//                            JSONObject json = (JSONObject) args[0];
//                            String username = "";
//                            String imageuri = "";
//                            try {
//                                username = json.getString(DBConstants.COLUMN_USERINFO_USERNAME);
//                                imageuri = json.getString(DBConstants.COLUMN_USERINFO_PICTURE_URL);
//                                Log.d("db", username);
//                                Log.d("db", imageuri);
//                                Users member = new Users(username, imageuri);
//                                members.add(member);
//                            }catch (JSONException e)
//                            {
//                                e.getMessage();
//                            }
//                        }
//                    });


                    cursor = db.rawQuery("SELECT * FROM " + DBConstants.TABLE_NAME, null);

                    if (cursor.moveToFirst()) {
//                        do {
                            String username = cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_USERINFO_USERNAME));
                            String imageuri = cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_USERINFO_PICTURE_URL));
//                            String email = cursor.getString(cursor.getColumnIndex(DBConstants.COLUM_USERINFO_EMAIL));



                        } while (cursor.moveToNext());

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
