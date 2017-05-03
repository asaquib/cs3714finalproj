package com.example.bjaso.cs3714finalproj;

/**
 * Created by root on 4/13/17.
 */


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends Activity implements View.OnClickListener{


    CallbackManager callbackManager;
    AccessToken token = AccessToken.getCurrentAccessToken();

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

        if (token != null) {


            Log.d("hiking", "logged in");

            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            finish();


        } else {


            callbackManager = CallbackManager.Factory.create();
            LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);

            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            Log.d("login", "Login Success");
                            AccessToken token = AccessToken.getCurrentAccessToken();
                            GraphRequest graphRequest = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                                    Log.d("graphResponse", graphResponse.getRawResponse());

                                    JSONArray data = graphResponse.getJSONObject().optJSONArray("data");
                                    try{
                                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                        intent.putExtra("id",jsonObject.get("id").toString());
                                        intent.putExtra("name",jsonObject.get("name").toString());
                                        setResult(Activity.RESULT_OK, intent);
                                        Log.d("graphResponse",jsonObject.get("name").toString());
                                        finish();
                                    }
                                    catch (JSONException e)
                                    {
                                        Log.d("graphRespose", "NO ID OR USERNAME");
                                    }


                                }
                            });
                            Bundle param = new Bundle();
                            //param.putString("fields", "friendlist", "members");
                            graphRequest.setParameters(param);
                            graphRequest.executeAsync();

                            //Change the intent now

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

}
