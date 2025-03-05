package com.example.coursework_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class RegisterActivity extends AppCompatActivity {

    ImageView ImageView_NTU_gif;
    TextView Move_to_login_Textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ImageView_NTU_gif = findViewById(R.id.NTU_gif);
        Glide.with(this).load(R.drawable.video_turned_to_gif).into(ImageView_NTU_gif);

        Move_to_login_Textview = findViewById(R.id.move_to_login_textview);
        Move_to_login_Textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Move_to_Activity(MainActivity.class);
            }
        });


    }

    private void Move_to_Activity(Class class_name){
        Intent intent = new Intent(this, class_name);
        startActivity(intent);
    }
}