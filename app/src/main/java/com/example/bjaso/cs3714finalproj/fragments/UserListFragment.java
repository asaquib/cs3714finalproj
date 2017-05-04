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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bjaso.cs3714finalproj.MainActivity;
import com.example.bjaso.cs3714finalproj.R;
import com.example.bjaso.cs3714finalproj.data.Users;
import com.example.bjaso.cs3714finalproj.database.DBController;
import com.example.bjaso.cs3714finalproj.interfaces.HomeScreenInteraction;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by pejman on 5/2/2017.
 */

public class UserListFragment extends Fragment  implements View.OnClickListener{
    public static final String USER_LIST_FRAGMENT = "users fragments";

    private DBController dbController;
    private ArrayList<Users> users;
    String list;
    private Button home;
    private Button event;
    private Button user;
    private ListView memberList;
    private TeamMemberArrayAdapter teamMemberListAdapter;
    private HomeScreenInteraction activity;

    public static UserListFragment newInstance() {
        UserListFragment fragment = new UserListFragment();
        return fragment;
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

    public UserListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick(View v) {
        if(v.equals(home))
        {
            activity.changeFragment(HomeFragment.HOME_FRAGMENT);
        }
        if(v.equals(event))
        {
            activity.changeFragment(UserListFragment.USER_LIST_FRAGMENT);
        }
        if(v.equals(user))
        {
            activity.changeFragment(UserListFragment.USER_LIST_FRAGMENT);
        }
    }


    private class TeamMemberArrayAdapter extends ArrayAdapter<Users> {
        private final Context context;
        private final ArrayList<Users> members;
        private int id;

        public TeamMemberArrayAdapter(Context context,  int id ,ArrayList members) {
            super(context, id, members);
            this.context = context;
            this.members=members;
            this.id = id;
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
                bmImage.setImageBitmap(Bitmap.createScaledBitmap(result,120,120, false));
                notifyDataSetChanged();
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
                notifyDataSetChanged();
            }
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.user_view, parent, false);

            TextView username = (TextView) rowView.findViewById(R.id.username);
            //TextView email = (TextView) rowView.findViewById(R.id.email);
            ImageView image = (ImageView) rowView.findViewById(R.id.userimage);


            username.setText(members.get(position).getUsername()+"");
            //email.setText(members.get(position).getEmail()+"");
            String imgURL = members.get(position).getUrl();
            Log.d("URL: ",imgURL);
            new DownloadImageTask(image).execute(imgURL);

            return rowView;
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.user_list, container, false);

        home = (Button) view.findViewById(R.id.homeButton);
        event = (Button) view.findViewById(R.id.eventButton);
        user = (Button) view.findViewById(R.id.mapButton);

        //Navigation actions
        home.setOnClickListener(this);
        event.setOnClickListener(this);
        user.setOnClickListener(this);
        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        dbController = activity.getDatabase_controller();
        users = dbController.readUsers();

        memberList = (ListView)view.findViewById(R.id.users);
        teamMemberListAdapter= new TeamMemberArrayAdapter(getActivity(), R.layout.user_view, users);
        memberList.setAdapter(teamMemberListAdapter);
    }
}
