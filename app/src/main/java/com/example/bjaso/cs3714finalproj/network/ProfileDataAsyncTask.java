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
    private ProfileInteraction profileFragment;
    private Context context;
    AccessToken accessToken;

    JSONObject profileInfo;
    private String id, birthDay, firstName, lastName, gender;

    public ProfileDataAsyncTask(Context context, ProfileFragment mProfileFragment){

        this.context=context;
        this.profileFragment =(ProfileInteraction)mProfileFragment;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        if (response != null) {
                            Log.d("check", response.toString());
                            try {
                                id = (String) object.get("id");
                                birthDay = (String) object.get("birthday");
                                firstName = (String) object.get("first_name");
                                lastName = (String) object.get("last_name");
                                gender = (String) object.get("gender");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");
        request.setParameters(parameters);
        request.executeAsync();
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        profileFragment.setProfileData(id, birthDay, firstName, lastName, gender);
    }
}
