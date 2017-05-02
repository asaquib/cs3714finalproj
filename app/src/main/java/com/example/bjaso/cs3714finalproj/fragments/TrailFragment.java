package com.example.bjaso.cs3714finalproj.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

import com.example.bjaso.cs3714finalproj.MainActivity;
import com.example.bjaso.cs3714finalproj.R;
import com.example.bjaso.cs3714finalproj.interfaces.HomeScreenInteraction;

import java.io.InputStream;

/**
 * Created by bjaso on 4/18/2017.
 */

public class TrailFragment extends Fragment implements View.OnClickListener{
    public static final String TRAIL_FRAGMENT = "trail_fragment";

    private String trailPhotoReference;
    private String trailName;
    private ImageView image;
    private TextView title;
    private Button home;
    private Button event;
    private Button map;
    private HomeScreenInteraction activity;
    public static TrailFragment newInstance() {
        TrailFragment fragment = new TrailFragment();
        return fragment;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
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
    public TrailFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trail, container, false);
        MainActivity activity = (MainActivity) getActivity();
        trailPhotoReference = activity.getPhotoReference();
        trailName = activity.getName();

        //image = (ImageView) view.findViewById(R.id.trailImage);
        title = (TextView) view.findViewById(R.id.trailName);
        Log.d("trailName", trailName);
        title.setText(trailName);
        //image.setImageResource(R.drawable.huckleberry_trail);
        //Navigation items
        home = (Button) view.findViewById(R.id.homeButton);
        event = (Button) view.findViewById(R.id.eventButton);
        map = (Button) view.findViewById(R.id.mapButton);

        String url = getPhotoUrl(trailPhotoReference, 1080);
        new DownloadImageTask((ImageView) view.findViewById(R.id.trailImage))
                .execute(url);

        //Navigation actions
        home.setOnClickListener(this);
        event.setOnClickListener(this);
        map.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    private String getPhotoUrl(String photoReference, int maxWidth) {
        StringBuilder googlePhotosUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo?");
        googlePhotosUrl.append("maxwidth=" + maxWidth);
        googlePhotosUrl.append("&photoreference=" + photoReference);
        googlePhotosUrl.append("&key=" + getString(R.string.google_maps_key));
        Log.d("getPhotoUrl", googlePhotosUrl.toString());
        return (googlePhotosUrl.toString());
    }
}
