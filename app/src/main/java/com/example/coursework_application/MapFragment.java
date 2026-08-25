package com.example.coursework_application;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    ImagesInfoModel Pavilion_info = Utils.getInstance().Pavilion;
    ImagesInfoModel MAE_info = Utils.getInstance().Mary_Anne_Evans;
    ImagesInfoModel ABK_info = Utils.getInstance().Ada_Byron_King;
    ImagesInfoModel JC_info = Utils.getInstance().John_Clare;

    ImagesInfoModel Pavilion_info_un = Utils.getInstance().Pavilion_undiscovered;
    ImagesInfoModel MAE_info_un = Utils.getInstance().Mary_Anne_Evans_undiscovered;
    ImagesInfoModel ABK_info_un = Utils.getInstance().Ada_Byron_King_undiscovered;
    ImagesInfoModel JC_info_un = Utils.getInstance().John_Clare_undiscovered;


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng NTU_Clifton_latlng = new LatLng(52.91185, -1.18531);
        LatLng Pavilion_latlng = new LatLng(52.91210, -1.18563);
        LatLng MAE_latlng = new LatLng(52.91150, -1.18431);
        LatLng ABK_latlng = new LatLng(52.91122, -1.18495);
        LatLng JC_latlng = new LatLng(52.91155, -1.18539);





        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NTU_Clifton_latlng, 17));

        mMap.setInfoWindowAdapter(new Google_Maps_Adapter(getContext()));

        //generates all markers
        Marker Pavilion_marker = mMap.addMarker(new MarkerOptions().position(Pavilion_latlng));
        Marker MAE_marker = mMap.addMarker(new MarkerOptions().position(MAE_latlng));
        Marker ABK_marker = mMap.addMarker(new MarkerOptions().position(ABK_latlng));
        Marker JC_marker = mMap.addMarker(new MarkerOptions().position(JC_latlng));

        //setups markers with the default info and colours
        Pavilion_marker.setTag(Pavilion_info_un);
        Pavilion_marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        MAE_marker.setTag((MAE_info_un));
        MAE_marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        ABK_marker.setTag(ABK_info_un);
        ABK_marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        JC_marker.setTag(JC_info_un);
        JC_marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));


        //if users discovered them it will override the colour and content
        if (Utils.getInstance().score_tracker_from_firebase.get("Pavilion") == true){
            Pavilion_marker.setTag(Pavilion_info);
            Pavilion_marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        }
        if (Utils.getInstance().score_tracker_from_firebase.get("Mary Anne Evans") == true){
            MAE_marker.setTag(MAE_info);
            MAE_marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        }
        if (Utils.getInstance().score_tracker_from_firebase.get("Ada Byron King") == true){
            ABK_marker.setTag(ABK_info);
            ABK_marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        }
        if (Utils.getInstance().score_tracker_from_firebase.get("John Clare") == true){
            JC_marker.setTag(JC_info);
            JC_marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        }

    }

}