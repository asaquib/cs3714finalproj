package com.example.bjaso.cs3714finalproj.network;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.bjaso.cs3714finalproj.fragments.ProfileFragment;
import com.example.bjaso.cs3714finalproj.fragments.TaskFragment;
import com.example.bjaso.cs3714finalproj.interfaces.ProfileInteraction;
import com.example.bjaso.cs3714finalproj.interfaces.RetainedFragmentInteraction;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by root on 4/29/17.
 */

public class ProfileDataAsyncTask extends AsyncTask {
    private Context context;
    AccessToken accessToken;

    JSONObject profileInfo;
    private String id, birthDay, firstName, lastName, gender;

    public ProfileDataAsyncTask(Context context){

        this.context=context;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {

    }
}
