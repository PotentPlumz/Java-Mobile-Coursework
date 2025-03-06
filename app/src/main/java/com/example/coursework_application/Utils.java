package com.example.coursework_application;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Utils {


    private static Utils instance = null;

    public Utils() {}

    public static Utils getInstance() {
        if(instance == null) {
            instance = new Utils();
        }
        return instance;
    }
    FirebaseAuth login_Auth = FirebaseAuth.getInstance();
}



