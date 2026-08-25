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

import java.util.List;

public class ImagesAdapter extends ArrayAdapter<ImagesInfoModel>

{

    public ImagesAdapter(@NonNull Context context, int resource, @NonNull List<ImagesInfoModel> objects)
    {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        //We have not told Java what the convertView is yet, so we need to assign this to our XML file we created.
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.images_list, parent, false);
        }
        //Get the individual item from the list based on the position of the list.
        ImagesInfoModel images_info_model = getItem(position);

        //Grab the title and desc items from the XML file
        TextView building_name = convertView.findViewById(R.id.building_name_textview);
        TextView building_services = convertView.findViewById(R.id.building_services_textview);
        ImageView building_image = convertView.findViewById(R.id.building_image_imageView);

        //If the positions isnt null, set the data using the getters we created earlier to the textviews
        if(images_info_model != null)
        {
            building_name.setText(images_info_model.get_Building_name());
            building_services.setText(images_info_model.get_Building_Services());
            building_image.setImageResource(images_info_model.getBuilding_image());
        }
        //Finally, return the convertView we have created here
        return convertView;



    }
}
