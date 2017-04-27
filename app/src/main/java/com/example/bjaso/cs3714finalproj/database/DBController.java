package com.example.bjaso.cs3714finalproj.database;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.bjaso.cs3714finalproj.service.backgroundService;
import com.example.bjaso.cs3714finalproj.socketio.socketIO;

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

    public  DBController(Context context, backgroundService background_service, Application application) {
        db_helper = new trailsDB(context);
        this.background_service = background_service;
        this.context = context;

        socketIO app = (socketIO)application;
        mSocket = app.getSocket();
        mSocket.connect();
    }



    // You need to call this open up the database
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
}
