package com.example.coursework_application;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BuildingsFragment extends Fragment {
    View root_view;

    ImagesInfoModel Pavilion;
    ImagesInfoModel Mary_Anne_Evans;
    ImagesInfoModel Ada_Byron_King;
    ImagesInfoModel John_Clare;

    TextView explored_buildings_textview;

    public BuildingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root_view = inflater.inflate(R.layout.fragment_buildings, container, false);
        // Inflate the layout for this fragment


        //Assign the listview we created in activity_custom_list_objects to a variable
        ListView custom_list_view = root_view.findViewById(R.id.image_custom_listview);

        //Create an empty list using our Information model
        ArrayList<ImagesInfoModel> building_list = new ArrayList<>();

        Pavilion = Utils.getInstance().Pavilion;
        Mary_Anne_Evans = Utils.getInstance().Mary_Anne_Evans;
        Ada_Byron_King = Utils.getInstance().Ada_Byron_King;
        John_Clare = Utils.getInstance().John_Clare;

        if (Utils.getInstance().score_tracker_from_firebase.get("Pavilion") == false){
            Pavilion = Utils.getInstance().Pavilion_undiscovered;
        }
        if (Utils.getInstance().score_tracker_from_firebase.get("Mary Anne Evans") == false){
            Mary_Anne_Evans = Utils.getInstance().Mary_Anne_Evans_undiscovered;
        }
        if (Utils.getInstance().score_tracker_from_firebase.get("Ada Byron King") == false){
            Ada_Byron_King = Utils.getInstance().Ada_Byron_King_undiscovered;
        }
        if (Utils.getInstance().score_tracker_from_firebase.get("John Clare") == false){
            John_Clare = Utils.getInstance().John_Clare_undiscovered;
        }


        building_list.add(Pavilion); building_list.add(Mary_Anne_Evans); building_list.add(Ada_Byron_King);
        building_list.add(John_Clare);

        //Define our custom adapter
        ImagesAdapter BuildingAdapter = new ImagesAdapter(requireContext(), 0, building_list);
        //Assign the customer adapter to the list view
        custom_list_view.setAdapter(BuildingAdapter);

        custom_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                        Utils.getInstance().Detailed_Buildings_Class = Pavilion;
                    }
                if (i == 1) {
                        Utils.getInstance().Detailed_Buildings_Class = Mary_Anne_Evans;
                    }
                if (i == 2) {
                        Utils.getInstance().Detailed_Buildings_Class = Ada_Byron_King;
                    }
                if (i == 3) {
                        Utils.getInstance().Detailed_Buildings_Class = John_Clare;
                }

                HomeActivity homeActivity = (HomeActivity) getActivity();
                homeActivity.replaceFragment(new BuildingsDetailFragment());
            }
        });


        explored_buildings_textview = root_view.findViewById(R.id.explored_buildings_textview);

        explored_buildings_textview.setText(check_number_of_explored_buiildings()+"/"+ Utils.getInstance().score_tracker_from_firebase.size());

        return root_view;
    }

    public static int check_number_of_explored_buiildings() {
        int number = 0;

        for (boolean i : Utils.getInstance().score_tracker_from_firebase.values()) {
            if (i == true){
                number++;
            }

        }
        return number;
    }


}