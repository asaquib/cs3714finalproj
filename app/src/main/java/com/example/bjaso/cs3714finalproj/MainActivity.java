package com.example.bjaso.cs3714finalproj;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


/**
 * Created by bjaso on 4/4/2017.
 */

public class MainActivity extends Activity implements View.OnClickListener{

    private android.support.v4.app.Fragment TrailFragment, eventFragment, mapFragment;
    private ImageView image;
    private Button map;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {

    }
}
