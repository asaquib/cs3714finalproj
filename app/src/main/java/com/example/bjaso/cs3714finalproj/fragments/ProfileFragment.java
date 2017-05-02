package com.example.bjaso.cs3714finalproj.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bjaso.cs3714finalproj.LoginActivity;
import com.example.bjaso.cs3714finalproj.R;
import com.example.bjaso.cs3714finalproj.interfaces.HomeScreenInteraction;
import com.example.bjaso.cs3714finalproj.interfaces.ProfileInteraction;
import com.example.bjaso.cs3714finalproj.network.ProfileDataAsyncTask;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by root on 4/29/17.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {
    public static final String PROFILE_FRAGMENT = "profile_fragment";
    static final int PROFILE_REQUEST = 5;

    private HomeScreenInteraction activity;

    private String birthDay, lastName, firstName, gender, facebookId;
    Bitmap bitmap;

    private Button home;
    TextView nameView;
    TextView birthDayView;
    TextView genderView;
    private ImageView image;

    AccessToken token = AccessToken.getCurrentAccessToken();

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    public ProfileFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);



//        try {
//            bitmap = getFacebookProfilePicture(facebookId);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        image.setImageBitmap(bitmap);
        nameView = (TextView) view.findViewById(R.id.nameView);
        genderView = (TextView) view.findViewById(R.id.genderView);
        birthDayView = (TextView) view.findViewById(R.id.birthDayView);

        nameView.setText(firstName + " " + lastName);
        genderView.setText(gender);
        birthDayView.setText(birthDay);


        home = (Button) view.findViewById(R.id.homeButton);

        //Navigation actions
        home.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeScreenInteraction) {
            activity = (HomeScreenInteraction) context;

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HomeScreenInteraction");
        }
    }

    @Override
    public void onClick(View v) {
        if(v.equals(home))
        {
            activity.changeFragment(HomeFragment.HOME_FRAGMENT);
        }

    }

//    public static Bitmap getFacebookProfilePicture(String userID) throws IOException {
//        URL imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
//        Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
//
//        return bitmap;
//    }


}
