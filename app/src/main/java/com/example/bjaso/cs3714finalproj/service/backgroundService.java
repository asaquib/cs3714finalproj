package com.example.bjaso.cs3714finalproj.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.bjaso.cs3714finalproj.database.DBController;
import com.example.bjaso.cs3714finalproj.socketio.socketIO;

import io.socket.client.Socket;


/**
 * Created by root on 4/25/17.
 */

public class backgroundService extends Service{

    SharedPreferences prefs;

    private Socket mSocket;

    private DBController database_controller;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // happenswhen service starts
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);


        // we need this for extracting username when 'emitting' steps to the server.
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        // We need to initialize an intent filter that will recognize 'ACTION_TIME_TICK'
        IntentFilter filter = new IntentFilter(
                Intent.ACTION_TIME_TICK);

        // We need to register our local broadcast receiver
        registerReceiver(receiver, filter);

        Log.d("background_service", "BackgroundService Started!");


        // Get the socket from the Application and then connect.
        socketIO app = (socketIO) getApplication();
        mSocket = app.getSocket();
        mSocket.connect();


        // Lets initiate the database controller
        database_controller = new DBController(getApplicationContext(), this, getApplication());
        database_controller.OpenDB();


        return Service.START_STICKY;
    }

    // BroadcastRecevier receiver
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    @Override
    public void onDestroy() {
        Log.d("background_service", "BackgroundService Stopped!");

        //inside onDestroy you need to 'unregister' the broadcast receiver
        unregisterReceiver(receiver);
        database_controller.CloseDB();
        database_controller = null;

        //also disconnect the mSocket
        if (mSocket != null)
            mSocket.disconnect();
        super.onDestroy();
    }
}