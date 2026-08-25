package com.example.coursework_application;

import static com.example.coursework_application.BuildingsFragment.check_number_of_explored_buiildings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment {

    private View root_view;
    TextView username_textview;
    LinearLayout Now_layout;
    LinearLayout Tutorial_layout;
    LinearLayout Maze_Layout;
    TextView Explored_buildings_textview;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment

        set_on_click_listeners_layout_home();


        return root_view;
    }

    @Override
    public void onStart() {
        super.onStart();


            username_textview = root_view.findViewById(R.id.username_textview);
            if (username_textview.getText() != null){
                username_textview.setText(Utils.getInstance().user_firstname);
            }

        Explored_buildings_textview = root_view.findViewById(R.id.home_fragment_discovered_buildings_textview);
            if (Explored_buildings_textview.getText() != null) {
                Explored_buildings_textview.setText(check_number_of_explored_buiildings() + "/" + Utils.getInstance().score_tracker_from_firebase.size());
            }

    }
    private void set_on_click_listeners_layout_home(){

        Now_layout = root_view.findViewById(R.id.NTU_now_button);
        Tutorial_layout = root_view.findViewById(R.id.tutorial_button);
        Maze_Layout = root_view.findViewById(R.id.maze_map_button);

        Now_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://my.ntu.ac.uk/dashboard/student"));
                startActivity(intent);
            }
        });
        Tutorial_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity homeActivity = (HomeActivity) getActivity();
                homeActivity.replaceFragment(new TutorialFragment());
            }
        });
        Maze_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://use.mazemap.com/#v=1&config=NTU&campusid=745&zlevel=1&center=-1.186040,52.911767&zoom=15.8"));
                startActivity(intent);
            }
        });


    }
}