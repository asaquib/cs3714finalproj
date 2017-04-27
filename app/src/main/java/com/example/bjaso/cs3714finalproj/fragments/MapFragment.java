package com.example.bjaso.cs3714finalproj.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bjaso.cs3714finalproj.R;
import com.example.bjaso.cs3714finalproj.interfaces.HomeScreenInteraction;
import com.google.android.gms.maps.MapView;


/**
 * Created by pejman on 4/18/2017.
 */

public class MapFragment extends Fragment implements OnClickListener {

    public static final String MAP_FRAGMENT = "map_fragment";
    private MapView mapView;
    private Button share;
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map, container, false);

        mapView = (MapView) view.findViewById(R.id.map);
        share = (Button) view.findViewById(R.id.share_location);

        //Navigation items
        home = (Button) view.findViewById(R.id.homeButton);
        event = (Button) view.findViewById(R.id.eventButton);
        map = (Button) view.findViewById(R.id.mapButton);

        mapView.setOnClickListener(this);
        share.setOnClickListener(this);

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
        if(v.equals(mapView))
        {

        }
        if(v.equals(share))
        {

        }
    }
}
