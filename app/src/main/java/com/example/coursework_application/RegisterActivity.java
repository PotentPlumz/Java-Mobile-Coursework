package com.example.coursework_application;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    ImageView ImageView_NTU_gif;
    TextView Move_to_login_Textview;
    FirebaseAuth loginAuth;
    Button Register_button;

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

        loginAuth = Utils.getInstance().login_Auth;

        Register_button = findViewById(R.id.Register_button);
        Register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupbuttonclicked();
            }
        });


    }

    private void Move_to_Activity(Class class_name){
        Intent intent = new Intent(this, class_name);
        startActivity(intent);
    }



    public void signupbuttonclicked(){
        boolean validation = true;

        EditText email = findViewById(R.id.edittext_email_register);
        EditText password1 = findViewById(R.id.edittext_password1_register);
        EditText password2 = findViewById(R.id.edittext_password2_register);
        EditText first_name = findViewById(R.id.edittext_firstname_register);
        EditText last_name = findViewById(R.id.edittext_lastname_register);

        String email_string = email.getText().toString();
        String password1_string = password1.getText().toString();
        String password2_string = password2.getText().toString();
        String first_name_string = first_name.getText().toString();
        String last_name_string = last_name.getText().toString();


        if (!password1_string.equals(password2_string)){
            Toast.makeText(this, "Passwords need to match!", Toast.LENGTH_SHORT).show();
            validation = false;
        }
        if (first_name_string.length() < 2 || last_name_string.length() < 2) {
            Toast.makeText(this, "Name length must be at least 2 characters", Toast.LENGTH_SHORT).show();
            validation = false;
        }

        if (validation == true) {
            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
            signup(email_string, password1_string);
        }

    }

    public void signup(String email, String password){
        loginAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d("MainActivity", "createUserWithEmail:success");
                                    FirebaseUser user = loginAuth.getCurrentUser();

                                    Toast.makeText(RegisterActivity.this,
                                            "Authentication success. Use an intent to move to a new activity",
                                            Toast.LENGTH_SHORT).show();
//user has been signed in, use an intent to move to the next activity.
                                    } else {
                                    // If sign in fails, display a message to the user.
                                            Log.w("MainActivity", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this,
                                            "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
    }





}