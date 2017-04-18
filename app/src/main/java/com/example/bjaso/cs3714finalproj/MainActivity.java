package com.example.bjaso.cs3714finalproj;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.facebook.AccessToken;

/**
 * Created by bjaso on 4/4/2017.
 */

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment TrailFragment;

    private SharedPreferences prefs;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        fragmentManager = getSupportFragmentManager();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Log.d("hw3", "status:" + prefs.getString("status", ""));


        if (1==0) {


            Log.d("hiking", "logged in");


        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivity(intent);
            finish();

        }
    }

//    @Override
//    public void InitiateLoginActivity() {
//        Intent intent = new Intent(getBaseContext(), LoginScreen.class);
//        startActivity(intent);
//        finish();
//    }
}
