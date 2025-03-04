package com.example.coursework_application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;


public class MainActivity extends AppCompatActivity {

    ImageView ImageView_NTU_gif;
    TextView Move_to_Register_Textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });

        ImageView_NTU_gif = findViewById(R.id.NTU_gif);
        Glide.with(this).load(R.drawable.video_turned_to_gif).into(ImageView_NTU_gif);


        //Move to register page
        Move_to_Register_Textview = findViewById(R.id.move_to_register_textview);
        Move_to_Register_Textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Move_to_Register_Activity();
            }
        });





    }

    private void Move_to_Register_Activity(){
        Intent intent = new Intent(this, Register_Activity.class);
        startActivity(intent);

    }
}