package com.example.bjaso.cs3714finalproj;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

/**
 * Created by bjaso on 4/4/2017.
 */

public class MainActivity extends Activity {

    private ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trail);

        image = (ImageView) findViewById(R.id.trailImage);
        image.setImageResource(R.drawable.huckleberry_trail);

        // Show settings menu
        /*setContentView(R.layout.fragment_settings);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.colors_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);*/
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
