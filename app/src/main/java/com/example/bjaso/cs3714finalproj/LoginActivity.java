package com.example.bjaso.cs3714finalproj;

/**
 * Created by root on 4/13/17.
 */


import com.facebook.FacebookSdk;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText password, username;
    private Button login;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();

        login = (Button)findViewById(R.id.button);
        login.setOnClickListener(this);

        password = (EditText)findViewById(R.id.password);
        username = (EditText)findViewById(R.id.username);
    }

    @Override
    public void onClick(View v) {
        if ((username.getText().toString().equals("hike")) && (password.getText().toString().equals("123"))) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("loggedin",true);
            this.startActivity(intent);
            finish();
        }
//        if(!busyNetworking){
//
//            logintask = new UserLoginTask(this,username.getText().toString(),password.getText().toString());
//
//            logintask.execute();
//        }


    }
/*
    @Override
    public void LoginStatus(String status) {
        //saving login status into persistence
        editor.putString("status",status).commit();

        if(status!= Constants.STATUS_RELOGIN) {

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(status,true);
            this.startActivity(intent);
            finish();

        }else{
            Toast.makeText(this, "Failed to login: wrong username or password", Toast.LENGTH_SHORT).show();

        }
    }
*/
}
