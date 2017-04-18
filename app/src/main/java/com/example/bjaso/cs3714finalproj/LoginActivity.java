package com.example.bjaso.cs3714finalproj;

/**
 * Created by root on 4/13/17.
 */


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;


import java.util.Arrays;

public class LoginActivity extends Activity implements View.OnClickListener{


    CallbackManager callbackManager;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);;
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {

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
