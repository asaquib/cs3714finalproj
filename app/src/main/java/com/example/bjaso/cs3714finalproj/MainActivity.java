package com.example.bjaso.cs3714finalproj;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.bjaso.cs3714finalproj.fragments.EventFragment;
import com.example.bjaso.cs3714finalproj.fragments.HomeFragment;
import com.example.bjaso.cs3714finalproj.fragments.MapFragment;
import com.example.bjaso.cs3714finalproj.fragments.ProfileFragment;
import com.example.bjaso.cs3714finalproj.fragments.TaskFragment;
import com.example.bjaso.cs3714finalproj.fragments.TrailFragment;
import com.example.bjaso.cs3714finalproj.interfaces.ActivityInteraction;
import com.example.bjaso.cs3714finalproj.interfaces.HomeScreenInteraction;
import com.example.bjaso.cs3714finalproj.interfaces.RetainedFragmentInteraction;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

/**
 * Created by bjaso on 4/4/2017.
 */


public class MainActivity extends AppCompatActivity implements HomeScreenInteraction,
        ActivityInteraction {


    private Fragment trailFragment, eventFragment, mapFragment, taskFragment, homeFragment, profileFragment;

    static final int MAP_REQUEST = 1;
    static final int PROFILE_REQUEST = 5;
    private String placeID;
    private String name;
    private String photoReference;
    private String profileName;
    private String gender;
    private String birthday;

    private FragmentManager fragmentManager;
    AccessToken token = AccessToken.getCurrentAccessToken();

    private SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Log.d("final", "status:" + prefs.getString("status", ""));

        if (token != null) {
            Log.d("hiking", "logged in");
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivityForResult(intent, PROFILE_REQUEST);
            finish();

        }
        Log.d("Login Successful??", "I'm Logged in?!!!");
        taskFragment = (TaskFragment) fragmentManager.findFragmentByTag(TaskFragment.TAG_TASK_FRAGMENT);

        if (taskFragment == null) {

            taskFragment = new TaskFragment();
            fragmentManager.beginTransaction().add(taskFragment, TaskFragment.TAG_TASK_FRAGMENT).commit();
        }

        if (savedInstanceState == null) {

            homeFragment = new HomeFragment();
            // Set dashboard fragment to be the default fragment shown
            ((RetainedFragmentInteraction)taskFragment).setActiveFragmentTag(HomeFragment.HOME_FRAGMENT);
            fragmentManager.beginTransaction().replace(R.id.frame, homeFragment ).commit();
        } else {

            homeFragment = fragmentManager.findFragmentByTag(HomeFragment.HOME_FRAGMENT);
            // Get referencecs to the fragments if they existed, null otherwise
            eventFragment = fragmentManager.findFragmentByTag(EventFragment.EVENT_FRAGMENT);
//                ((RetainedFragmentInteraction)taskFragment).setActiveFragmentTag(MapFragment.MAP_FRAGMENT);
            mapFragment = fragmentManager.findFragmentByTag(MapFragment.MAP_FRAGMENT);

            trailFragment = fragmentManager.findFragmentByTag(TrailFragment.TRAIL_FRAGMENT);

            profileFragment = fragmentManager.findFragmentByTag(ProfileFragment.PROFILE_FRAGMENT);


        }

    }

    public void changeFragment(String fragment_name) {

        Fragment fragment;
        Class fragmentClass = null;
        if(fragment_name.equals(EventFragment.EVENT_FRAGMENT)){
            fragmentClass = EventFragment.class;

            Log.d("cs3714finalproj", "EVENT FRAGMENT SELECTED");
        }
        else if(fragment_name.equals(MapFragment.MAP_FRAGMENT)){
            fragmentClass = MapFragment.class;

            Log.d("cs3714finalproj", "MAP FRAGMENT SELECTED");
        }
        else if(fragment_name.equals(HomeFragment.HOME_FRAGMENT)){
            fragmentClass = HomeFragment.class;

            Log.d("cs3714finalproj", "HOME FRAGMENT SELECTED");
        }
        else if(fragment_name.equals(TrailFragment.TRAIL_FRAGMENT)){
            fragmentClass = TrailFragment.class;

            Log.d("cs3714finalproj", "TRAIL FRAGMENT SELECTED");
        }
        else if(fragment_name.equals(ProfileFragment.PROFILE_FRAGMENT)){
            fragmentClass = ProfileFragment.class;

            Log.d("cs3714finalproj", "TRAIL FRAGMENT SELECTED");
        }

        try {
            if (fragmentClass != null) {
                fragment = (Fragment) fragmentClass.newInstance();

                FragmentTransaction ft= fragmentManager.beginTransaction();

                ft.replace(R.id.frame, fragment,
                        ((RetainedFragmentInteraction)taskFragment).getActiveFragmentTag());
                ft.addToBackStack(null);
                ft.commitAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.home);
//        fragmentManager = getSupportFragmentManager();
//
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {

        }
        else if (id == R.id.action_profile) {
            Bundle bundle = new Bundle();
            
            changeFragment(ProfileFragment.PROFILE_FRAGMENT);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MAP_REQUEST) {
            if (resultCode == RESULT_OK) {
                placeID = data.getStringExtra("place_id");
                name = data.getStringExtra("name");
                photoReference = data.getStringExtra("photo_reference");
                Log.d("Result", getPlaceID());
                Log.d("Result", getName());
                Log.d("Result", getPhotoReference());
                changeFragment(TrailFragment.TRAIL_FRAGMENT);
            }
        }
        if (requestCode == PROFILE_REQUEST){
            profileName = data.getStringExtra("profile_name");
            birthday = data.getStringExtra("birthday");
            gender = data.getStringExtra("gender");
        }
    }

    public String getPlaceID() {
        return placeID;
    }
    public String getName() {
        return name;
    }
    public String getPhotoReference() {
        return photoReference;
    }

    public AccessToken getToken() {
        return token;
    }

    public void setToken(AccessToken token) {
        this.token = token;
    }
}
