package com.example.bjaso.cs3714finalproj.socketio;

import android.app.Application;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by root on 4/25/17.
 */

public class socketIO extends Application {
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://172.29.113.44:4000/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    //this method is called from various Android components that want to use SocketIO
    public Socket getSocket() {
        return mSocket;
    }
}
