package com.example.bjaso.cs3714finalproj.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bjaso.cs3714finalproj.R;
import com.example.bjaso.cs3714finalproj.interfaces.HomeScreenInteraction;

/**
 * Created by pejman on 4/18/2017.
 */

public class EventFragment extends Fragment implements View.OnClickListener{

    public static final String EVENT_FRAGMENT = "event_fragment";
    private EditText trailName;
    private EditText Description;
    private Button addFriends;
    private Button createEvent;
    private Button home;
    private Button event;
    private Button map;
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

        View view = inflater.inflate(R.layout.event, container, false);
        trailName = (EditText) view.findViewById(R.id.trail_name);
        Description = (EditText) view.findViewById(R.id.description);
        addFriends = (Button) view.findViewById(R.id.add_friends);
        createEvent = (Button) view.findViewById(R.id.create_event);

        //Navigation items
        home = (Button) view.findViewById(R.id.homeButton);
        event = (Button) view.findViewById(R.id.eventButton);
        map = (Button) view.findViewById(R.id.mapButton);

        addFriends.setOnClickListener(this);
        createEvent.setOnClickListener(this);

        //Navigation actions
        home.setOnClickListener(this);
        event.setOnClickListener(this);
        map.setOnClickListener(this);

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
        if(v.equals(map))
        {
            activity.changeFragment(MapFragment.MAP_FRAGMENT);
        }
        if(v.equals(addFriends))
        {

        }
        if(v.equals(createEvent))
        {

        }
    }
}
