package com.example.bjaso.cs3714finalproj.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bjaso.cs3714finalproj.MapsActivity;
import com.example.bjaso.cs3714finalproj.R;
import com.example.bjaso.cs3714finalproj.interfaces.HomeScreenInteraction;

/**
 * Created by pejman on 4/27/2017.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    static final int MAP_REQUEST = 1;
    static final int FB_REQUEST = 2;
    public static final String HOME_FRAGMENT = "home_fragment";
    private Button home;
    private Button event;
    private Button users;
    private Button advButton;
    private HomeScreenInteraction activity;
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home, container, false);

        //Navigation items
        home = (Button) view.findViewById(R.id.homeButton);
        event = (Button) view.findViewById(R.id.eventButton);
        users = (Button) view.findViewById(R.id.mapButton);
        advButton = (Button) view.findViewById(R.id.advButton);

        //Navigation actions
        home.setOnClickListener(this);
        event.setOnClickListener(this);
        users.setOnClickListener(this);

        advButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateMapActivity();
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.equals(home))
        {
            activity.changeFragment(HomeFragment.HOME_FRAGMENT);
        }
        if(v.equals(event))
        {
            activity.changeFragment(EventFragment.EVENT_FRAGMENT);
        }
        if(v.equals(users))
        {
            activity.changeFragment(UserListFragment.USER_LIST_FRAGMENT);
        }

    }

    public void initiateMapActivity() {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        getActivity().startActivityForResult(intent, MAP_REQUEST);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == MAP_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                String ID = data.getStringExtra("result");
//                Log.d("Result", ID);
//            }
//        }
//    }
}
