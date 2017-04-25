package com.example.bjaso.cs3714finalproj.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bjaso.cs3714finalproj.R;

/**
 * Created by bjaso on 4/18/2017.
 */

public class TrailFragment extends Fragment {
    private ImageView image;

    public static TrailFragment newInstance() {
        TrailFragment fragment = new TrailFragment();
        return fragment;
    }

    public TrailFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trail, container, false);

        image = (ImageView) view.findViewById(R.id.trailImage);
        image.setImageResource(R.drawable.huckleberry_trail);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
