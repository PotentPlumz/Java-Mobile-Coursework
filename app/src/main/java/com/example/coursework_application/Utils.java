package com.example.coursework_application;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Utils {

    String user_firstname;
    String user_lastname;
    String user_email;

    HashMap<String, String> user_info_hashmap = new HashMap<>();
    HashMap<String, Boolean> score_tracker_from_firebase = new HashMap<>();


    ImagesInfoModel Pavilion = new ImagesInfoModel("Pavilion", "Cafeteria, Breakout Spaces, Employability, Lecture Theatres",
            "A really nice social space. Come and grab a coffee and chill or relax in the lounge areas.", R.drawable.clifton_pavilion, R.drawable.clifton_pavilion_longer);
    ImagesInfoModel Mary_Anne_Evans = new ImagesInfoModel("Mary Anne Evans", "Global Lounge, Lecture Theatres, IT Spaces, Breakout Areas",
            "In the school or Arts and Humanities, its a building with a modern touch, with several large IT spaces", R.drawable.mary_anne_evans, R.drawable.mary_anne_evans_longer);
    ImagesInfoModel Ada_Byron_King = new ImagesInfoModel("Ada Byron King",
            "Lecture Theatres, Breakout Spaces, Mock Classrooms","This building is home to Nottingham Institute of Education", R.drawable.ada_byron_king, R.drawable.ada_byron_king_longer);
    ImagesInfoModel John_Clare = new ImagesInfoModel("John Clare", "2 Large Lecture Theatres, Toilets",
            "A Building name after the English Poet who famously became known for his celebrations of the English countryside and sorrows at its disruption", R.drawable.john_clare, R.drawable.john_clare_longer);

    //undiscovered classes
    ImagesInfoModel Pavilion_undiscovered = new ImagesInfoModel("Undiscovered", "???", "???", R.drawable.clifton_pavilion, R.drawable.clifton_pavilion_longer);
    ImagesInfoModel Mary_Anne_Evans_undiscovered = new ImagesInfoModel("Undiscovered", "???", "???", R.drawable.mary_anne_evans, R.drawable.mary_anne_evans_longer);
    ImagesInfoModel Ada_Byron_King_undiscovered = new ImagesInfoModel("Undiscovered", "???","???", R.drawable.ada_byron_king, R.drawable.ada_byron_king_longer);
    ImagesInfoModel John_Clare_undiscovered = new ImagesInfoModel("Undiscovered", "???", "???", R.drawable.john_clare, R.drawable.john_clare_longer);

    ImagesInfoModel Detailed_Buildings_Class = new ImagesInfoModel("", "", "", 0, 0);



    private static Utils instance = null;

    public Utils() {}

    public static Utils getInstance() {
        if(instance == null) {
            instance = new Utils();
        }
        return instance;
    }
     FirebaseAuth login_Auth = FirebaseAuth.getInstance();


    public static boolean verify_password(String password1, String password2){

        return Objects.equals(password1, password2);

    }
    public static String fix_name_for_firebase(String name){
        name = name.replaceAll("\\s+","");
        name = name.substring(0,1).toUpperCase() + name.substring(1);

        return name;
    }


}










