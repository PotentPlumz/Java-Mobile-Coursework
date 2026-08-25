package com.example.coursework_application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    //video link for the gif playback https://www.youtube.com/watch?v=tPdCwUc50dU&ab_channel=FavourNdakara


    private Button login_button;
    ImageView ImageView_NTU_gif;
    TextView Move_to_Register_Textview;
    private FirebaseAuth loginAuth;
    FirebaseFirestore FB_database = FirebaseFirestore.getInstance();

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


        //Move to register page intent
        Move_to_Register_Textview = findViewById(R.id.move_to_register_textview);
        Move_to_Register_Textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Move_to_Activity(RegisterActivity.class);
            }
        });

            //Firebase login authentication
            loginAuth = Utils.getInstance().login_Auth;

            login_button = findViewById(R.id.login_button);

            login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        sign_in_method();
                    }
                    catch (Exception e){
                        Toast.makeText(MainActivity.this, "Please enter your details", Toast.LENGTH_SHORT).show();
                    }


                }
            });

    }


    public void Move_to_Activity(Class class_name){
        Intent intent = new Intent(this, class_name);
        startActivity(intent);
    }


    //Uses the on start method to check if user is logged in and direct them to the home activity
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = loginAuth.getCurrentUser();
        if(currentUser != null){
           Move_to_Activity(HomeActivity.class);
        }
    }

@Override
    public void onPause() {
    super.onPause();
}

//Sign in proceedure
    public void sign_in_method()
    {
        EditText email_txt = findViewById(R.id.edittext_email_login);
        EditText passwd_txt = findViewById(R.id.edittext_password_login);
        String email = email_txt.getText().toString();
        String password = passwd_txt.getText().toString();

        loginAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = loginAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("MainActivity", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this,
                                    "Login failed, check your internet connection and login details.",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }

}