package com.example.coursework_application;

import static com.example.coursework_application.BuildingsFragment.check_number_of_explored_buiildings;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.StringValue;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    BottomNavigationView bnv;


    private static final int PERMISSION_REQUEST_CAMERA = 1;
    TextView username_textview;
    FirebaseFirestore FB_database = FirebaseFirestore.getInstance();
    FirebaseAuth login_Auth = FirebaseAuth.getInstance();
    TextView Explored_buildings_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        bnv = findViewById(R.id.bottomNavigationView);
        //Set this class as the selected item listener.
        bnv.setOnItemSelectedListener(this);
        //Set the default selected item.
        bnv.setSelectedItemId(R.id.Home);

        get_email_from_firebase();
        fetch_user_details();



    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Home) {
            replaceFragment(new HomeFragment());
            return true;
        } else if (item.getItemId() == R.id.Scan) {
            if (checkSelfPermission(Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED){
                requestCameraPerm();
            } else {
                initQRCodeScanner();
            }
            return false;
        } else if (item.getItemId() == R.id.Map) {
            replaceFragment(new MapFragment());
            return true;
        } else if (item.getItemId() == R.id.Profile){
            replaceFragment(new ProfileFragment());
            return true;
        }  else if (item.getItemId() == R.id.Buildings) {
            replaceFragment(new BuildingsFragment());
            return true;
        }
        return false;
    }


    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true).
                replace(R.id.maFragment, fragment).commit();

    }

    //Qr scanning code initiation
    private void initQRCodeScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setOrientationLocked(false);
        integrator.setCaptureActivity(CaptureActivityPortait.class);
        integrator.setPrompt("Scan a QR code");
        integrator.initiateScan();
    }

    //Camera Permissions
    private void requestCameraPerm(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CAMERA);}
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                initQRCodeScanner();
            } else {
                Toast.makeText(this, "Camera permission is required to scan QR codes",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,
                resultCode, data);
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.w("QR_TEST", result.getContents());
                update_firebase_after_scan(result.getContents());
            }
        }

    public void get_email_from_firebase(){
        String email;
        FirebaseUser current_user = login_Auth.getCurrentUser();
        if (current_user.getEmail() != null){
        Utils.getInstance().user_email = current_user.getEmail();}
    }
    public void fetch_user_details(){

        FB_database.collection("User_Info").document(Utils.getInstance().user_email).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

                            add_user_details_to_hashmap_from_firebase(document);

                            username_textview = findViewById(R.id.username_textview);
                            username_textview.setText(Utils.getInstance().user_firstname);

                            Explored_buildings_textview = findViewById(R.id.home_fragment_discovered_buildings_textview);
                            Explored_buildings_textview.setText(check_number_of_explored_buiildings() + "/" + Utils.getInstance().score_tracker_from_firebase.size());

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
    public void reset_user_info(){
        Utils.getInstance().score_tracker_from_firebase = new HashMap<String, Boolean>();
        Utils.getInstance().user_info_hashmap = new HashMap<String, String>();
    }
    public void add_user_details_to_hashmap_from_firebase(DocumentSnapshot document) {
        Utils.getInstance().user_firstname = document.getString("first_name");
        Utils.getInstance().user_lastname = document.getString("last_name");

        Utils.getInstance().score_tracker_from_firebase.put("Pavilion", Boolean.valueOf(document.getString("Pavilion")));
        Utils.getInstance().score_tracker_from_firebase.put("Mary Anne Evans", Boolean.valueOf(document.getString("Mary Anne Evans")));
        Utils.getInstance().score_tracker_from_firebase.put("Ada Byron King", Boolean.valueOf(document.getString("Ada Byron King")));
        Utils.getInstance().score_tracker_from_firebase.put("John Clare", Boolean.valueOf(document.getString("John Clare")));
    }
private void update_firebase_after_scan(String result){
        if (Utils.getInstance().score_tracker_from_firebase.containsKey(result)){

            if (Utils.getInstance().score_tracker_from_firebase.get(result) == false){

                Map<String, Object> update = new HashMap<>();
                update.put(result, String.valueOf(true));

                Utils.getInstance().score_tracker_from_firebase.replace(result, true);
                FB_database.collection("User_Info").document(Utils.getInstance().user_email).update(update);
                Toast.makeText(this, "You just discovered the " + result + " building!", Toast.LENGTH_LONG).show();
                bnv.setSelectedItemId(R.id.Buildings);
                replaceFragment(new BuildingsFragment());
            }
            else{
                Toast.makeText(this, "You have already discovered the " + result + " building", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this, "QR Code not recognised, please try again", Toast.LENGTH_LONG).show();
        }
}


}

