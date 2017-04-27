package com.example.bjaso.cs3714finalproj;

/**
 * Created by root on 4/13/17.
 */


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends Activity implements View.OnClickListener{


    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.bjaso.cs3714finalproj",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Log.d("login", "Login Success");
                    //friend json parse??? not working at this point <----------------------------------------------------__!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11
                    AccessToken token = AccessToken.getCurrentAccessToken();
                    GraphRequest graphRequest = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                            try {
                                JSONArray jsonArrayFriends = jsonObject.getJSONObject("friendlist").getJSONArray("data");
                                JSONObject friendlistObject = jsonArrayFriends.getJSONObject(0);
                                String frienListID = friendlistObject.getString("id");
                                //myNewGraphReq(friendListID);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    Bundle param = new Bundle();
                    //param.putString("fields", "friendlist", "members");
                    graphRequest.setParameters(param);
                    graphRequest.executeAsync();

                    //Change the intent now
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);;
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancel() {
                    Log.d("login", "Login Canceled");
                }

                @Override
                public void onError(FacebookException error) {
                    Log.d("login", "Login Error Happened");
                }
            }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
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
