package com.example.coursework_application;

import static com.example.coursework_application.BuildingsFragment.check_number_of_explored_buiildings;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

View root_view;

Button logout_button;
Button edit_profile_button;
Button update_name_button;
Button update_password_button;

FirebaseFirestore FB_database = FirebaseFirestore.getInstance();


EditText edittext_firstname;
EditText editText_lastname;
EditText editText_email;
EditText editText_old_Password;
EditText editText_password1;
EditText editText_password2;

TextView email_warning_textview;

boolean editable = false;
boolean verified = false;



Drawable edit_icon;

FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();


    public ProfileFragment() {
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
        root_view = inflater.inflate(R.layout.fragment_profile, container, false);

        logout_button = root_view.findViewById(R.id.logout_button);

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth loginAuth = Utils.getInstance().login_Auth;
                HomeActivity homeActivity = (HomeActivity) getActivity();

                loginAuth.signOut();
                homeActivity.reset_user_info();

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        //fetches email from home activity method and adds to Utils class
        HomeActivity homeActivity = (HomeActivity) getActivity();
        homeActivity.get_email_from_firebase();

        //gets all my view IDs
        edittext_firstname = root_view.findViewById(R.id.edittext_firstname_profile);
        editText_lastname = root_view.findViewById(R.id.edittext_lastname_profile);
        editText_email = root_view.findViewById(R.id.edittext_email_profile);
        editText_old_Password = root_view.findViewById(R.id.edittext_old_password_profile);
        editText_password1 = root_view.findViewById(R.id.edittext_password1_profile);
        editText_password2 = root_view.findViewById(R.id.edittext_password2_profile);

        //sets text from Utils class
       edittext_firstname.setText(Utils.getInstance().user_firstname);
       editText_lastname.setText(Utils.getInstance().user_lastname);
       editText_email.setText(Utils.getInstance().user_email);

       //Makes the email field read only
       editText_email.setInputType(InputType.TYPE_NULL);
       editText_email.setTextIsSelectable(false);
       editText_email.setTypeface(null, Typeface.ITALIC);

       //locks the password fields at the start
        editText_old_Password.setInputType(InputType.TYPE_NULL);
        editText_old_Password.setTextIsSelectable(false);
        editText_old_Password.setText("");

        editText_password1.setInputType(InputType.TYPE_NULL);
        editText_password1.setTextIsSelectable(false);
        editText_password1.setText("");

        editText_password2.setInputType(InputType.TYPE_NULL);
        editText_password2.setTextIsSelectable(false);
        editText_password2.setText("");


        //toggles the editability to the first and last name fields
        make_edittexts_uneditable(edittext_firstname);
        make_edittexts_uneditable(editText_lastname);

        edit_icon = ContextCompat.getDrawable(getContext(), R.drawable.edit_icon);


        //edit text fields
       edit_profile_button = root_view.findViewById(R.id.edit_profile_button);
       edit_profile_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               if (!editable){
                    make_all_editable();
               }
               else {
                    make_all_uneditable();
               }
           }
       });



       //Alerts the user that they cannot edit their email address if they click on the field.
        email_warning_textview = root_view.findViewById(R.id.email_warning_textview);
        editText_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email_warning_textview.setText(("Sorry, the email cannot be changed."));
            }
        });

        update_name_button = root_view.findViewById(R.id.Update_ProfileDetails_Button);
        update_password_button = root_view.findViewById(R.id.Update_ProfilePassword_Button);

        update_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String old_password = editText_old_Password.getText().toString();
                String password1 = editText_password1.getText().toString();
                String password2 = editText_password2.getText().toString();


                if (old_password.isEmpty() || password1.isEmpty() || password2.isEmpty()){
                    Toast.makeText(homeActivity, "Update password cannot be done with empty fields", Toast.LENGTH_LONG).show();
                    return;
                }

                //reauthenticates the user ready to update their password
                AuthCredential credential = EmailAuthProvider.getCredential(Utils.getInstance().user_email, old_password);
                current_user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()){
                            verified = false;
                            Toast.makeText(homeActivity, "Old password incorrect", Toast.LENGTH_LONG).show();
                        }
                        else {
                            verified = true;
                        }
                    }
                });


                if (!Utils.verify_password(password1, password2)){
                    Toast.makeText(getContext(), "Passwords need to match!", Toast.LENGTH_SHORT).show();
                    verified = false;
                }

                if (verified){
                    current_user.updatePassword(password1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(homeActivity, "Password updated successfully", Toast.LENGTH_LONG).show();
                                make_all_uneditable();
                            }
                            else {
                                Toast.makeText(homeActivity, "There was a problem updating your password.", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
            }
        });

        update_name_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String first_name = edittext_firstname.getText().toString();
                String last_name = editText_lastname.getText().toString();

                first_name = Utils.fix_name_for_firebase(first_name);
                last_name = Utils.fix_name_for_firebase(last_name);

                if (first_name.length() < 2 || last_name.length() < 2 ){
                    Toast.makeText(homeActivity, "Names need to be at least two characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> update = new HashMap<>();
                update.put("first_name", first_name);
                update.put("last_name", last_name);

                FB_database.collection("User_Info").document(Utils.getInstance().user_email).update(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(homeActivity, "Name changed successfully.", Toast.LENGTH_LONG).show();
                            make_all_uneditable();


                            fetch_user_details2();

                        }
                        else {
                            Toast.makeText(homeActivity, "An error occurred with the name change.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


        return root_view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void make_edittexts_uneditable(EditText editText){
        editText.setInputType(InputType.TYPE_NULL);
        editText.setTextIsSelectable(false);
        editText.setTypeface(null, Typeface.ITALIC);
        editText.setCompoundDrawablesWithIntrinsicBounds(null, null,null , null);



    }
    private void make_edittexts_editable(EditText editText) {
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextIsSelectable(true);
        editText.setTypeface(null, Typeface.NORMAL);
        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, edit_icon, null);
    }

        private void make_all_editable(){
            make_edittexts_editable(edittext_firstname);
            make_edittexts_editable(editText_lastname);

            //resets password fields
            editText_old_Password.setInputType(InputType.TYPE_CLASS_TEXT);
            editText_old_Password.setTextIsSelectable(true  );

            editText_password1.setInputType(InputType.TYPE_CLASS_TEXT);
            editText_password1.setTextIsSelectable(true  );

            editText_password2.setInputType(InputType.TYPE_CLASS_TEXT);
            editText_password2.setTextIsSelectable(true);

            editable = true;

        }
        private void make_all_uneditable(){
            make_edittexts_uneditable(edittext_firstname);
            make_edittexts_uneditable(editText_lastname);

            edittext_firstname.setText(Utils.getInstance().user_firstname);
            editText_lastname.setText(Utils.getInstance().user_lastname);

            //resets the password fields

            editText_old_Password.setInputType(InputType.TYPE_NULL);
            editText_old_Password.setTextIsSelectable(false);
            editText_old_Password.setText("");

            editText_password1.setInputType(InputType.TYPE_NULL);
            editText_password1.setTextIsSelectable(false);
            editText_password1.setText("");

            editText_password2.setInputType(InputType.TYPE_NULL);
            editText_password2.setTextIsSelectable(false);
            editText_password2.setText("");

            editable = false;
        }
    public void fetch_user_details2(){

        FB_database.collection("User_Info").document(Utils.getInstance().user_email).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

                            Utils.getInstance().user_firstname = document.getString("first_name");
                            Utils.getInstance().user_lastname = document.getString("last_name");

                            edittext_firstname.setText(Utils.getInstance().user_firstname);
                            editText_lastname.setText(Utils.getInstance().user_lastname);

                            if (document.exists()) {
                                Log.d("Utils", "DocumentSnapshot data: " +
                                        document.getData()); } else {
                                Log.d("Utils", "No such document");
                            }
                        } else {
                            Log.d("Utils", "get failed with ",
                                    task.getException());
                        }
                    }
                });

    }

}