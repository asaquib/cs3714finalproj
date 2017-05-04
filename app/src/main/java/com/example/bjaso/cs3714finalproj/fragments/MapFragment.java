package com.example.bjaso.cs3714finalproj.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bjaso.cs3714finalproj.R;
import com.example.bjaso.cs3714finalproj.interfaces.HomeScreenInteraction;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

import static android.content.ContentValues.TAG;


/**
 * Created by pejman on 4/18/2017.
 */

public class MapFragment extends Fragment implements OnClickListener {

    public static final String MAP_FRAGMENT = "map_fragment";
    private MapView mapView;
    private Button share;
    private Button home;
    private Button event;
    private Button users;
    private HomeScreenInteraction activity;
    public static final int REQ_PERMISSION = 2;

    private GoogleMap mep;

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
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mep = googleMap;
            }
        });
        //mep.getUiSettings().setMyLocationButtonEnabled(false);
        //if (checkPermission())
        //mep.setMyLocationEnabled(true);
        //else askPermission();


        MapsInitializer.initialize(this.getActivity());
        if (MapsInitializer.initialize(this.getActivity()) != ConnectionResult.SUCCESS) {
            Log.d(TAG, "Google Play Services not available.");
        }

        // Updates the location and zoom of the MapView
        //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
        //mep.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(13.0810,80.2740), 15.5f), 4000, null);

        //Navigation items
        home = (Button) view.findViewById(R.id.homeButton);
        event = (Button) view.findViewById(R.id.eventButton);
        users = (Button) view.findViewById(R.id.mapButton);

        mapView.setOnClickListener(this);
        share.setOnClickListener(this);

        //Navigation actions
        home.setOnClickListener(this);
        event.setOnClickListener(this);
        users.setOnClickListener(this);



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
        if(v.equals(mapView))
        {

        }
        if(v.equals(share))
        {

        }
    }

    private boolean checkPermission() {
        Log.d(TAG, "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED );
    }
    // Asks for permission
    private void askPermission() {
        Log.d(TAG, "askPermission()");
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[] { Manifest.permission.ACCESS_FINE_LOCATION },REQ_PERMISSION
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    if (checkPermission())
                        mep.setMyLocationEnabled(true);

                } else {
                    // Permission denied

                }
                break;
            }
        }
    }
}
