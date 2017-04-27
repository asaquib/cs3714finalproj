package com.example.bjaso.cs3714finalproj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

/**
 * Created by bjaso on 4/4/2017.
 */


public class MainActivity extends AppCompatActivity {

    private android.support.v4.app.Fragment TrailFragment, eventFragment, mapFragment;
    private ImageView image;
    private Button map;
    private FragmentManager fragmentManager;
    AccessToken token = AccessToken.getCurrentAccessToken();

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

        Log.d("final", "status:" + prefs.getString("status", ""));



        if (token != null) {


            Log.d("hiking", "logged in");


        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivity(intent);
            finish();

        }
        Log.d("Login Successful??", "I'm Logged in?!!!");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {

        }
        else if (id == R.id.action_message) {

        }

        else if (id == R.id.action_logout) {
            // Log the user out
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


//    @Override
//    public void InitiateLoginActivity() {
//        Intent intent = new Intent(getBaseContext(), LoginScreen.class);
//        startActivity(intent);
//        finish();
//    }
}
