package com.example.coursework_application;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class BuildingsDetailFragment extends Fragment {

    private View root_view;
    TextView name_textview;
    TextView services_textview;
    TextView desc_textview;
    ImageView imageView;

    ImagesInfoModel info_class;
    TextView go_back_textview;

    public BuildingsDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.fragment_buildings_detail, container, false);
        // Inflate the layout for this fragment

        info_class = Utils.getInstance().Detailed_Buildings_Class;

        name_textview = root_view.findViewById(R.id.buildings_details_name_textview);
        services_textview = root_view.findViewById(R.id.buildings_details_services_textview);
        desc_textview = root_view.findViewById(R.id.buildings_details_desc_textview);
        imageView = root_view.findViewById(R.id.building_detail_imageview);

        name_textview.setText(info_class.get_Building_name());
        services_textview.setText(info_class.get_Building_Services());
        desc_textview.setText(info_class.get_Building_Desc());
        imageView.setImageResource(info_class.getBuilding_image_bigger());

        go_back_textview = root_view.findViewById(R.id.building_detail_go_back);

        go_back_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity homeActivity = (HomeActivity) getActivity();
                homeActivity.replaceFragment(new BuildingsFragment());
            }
        });

        return root_view;
    }

    @Override
    public void onStart() {
        super.onStart();
        info_class = Utils.getInstance().Detailed_Buildings_Class;


    }
}