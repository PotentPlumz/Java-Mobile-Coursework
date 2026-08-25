package com.example.coursework_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

public class Google_Maps_Adapter implements GoogleMap.InfoWindowAdapter {

    View view;
    Context context;


    public Google_Maps_Adapter(Context mcontext){
        context = mcontext;
        view = LayoutInflater.from(mcontext).inflate(R.layout.google_maps_info_window, null);
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {

    adapter_function(marker);

        return view;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        adapter_function(marker);
        return view;
    }

    private void adapter_function(@NonNull Marker marker){

        ImagesInfoModel Building_Details_Class = (ImagesInfoModel) marker.getTag();

        TextView building_name_textview = view.findViewById(R.id.google_maps_building_textview);
        TextView building_services_textview = view.findViewById(R.id.google_maps_services_textview);
        ImageView buillding_imageview = view.findViewById(R.id.google_maps_imageview);

        if (Building_Details_Class != null){

            building_name_textview.setText(Building_Details_Class.get_Building_name());
            building_services_textview.setText(Building_Details_Class.get_Building_Services());
            buillding_imageview.setImageResource(Building_Details_Class.getBuilding_image_bigger());
        }



    }
}
