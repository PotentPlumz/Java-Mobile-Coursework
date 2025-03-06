package com.example.coursework_application;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {



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


        BottomNavigationView bnv = findViewById(R.id.bottomNavigationView);
        //Set this class as the selected item listener.
        bnv.setOnItemSelectedListener(this);
        //Set the default selected item.
        bnv.setSelectedItemId(R.id.Home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Home) {
            replaceFragment(new HomeFragment());
            return true;
        } else if (item.getItemId() == R.id.Scan) {
            replaceFragment(new ScanFragment());
            return true;
        } else if (item.getItemId() == R.id.Map) {
            replaceFragment(new MapFragment());
            return true;
        } else if (item.getItemId() == R.id.Profile){
            replaceFragment(new ProfileFragment());
            return true;
        }
        return false;
    }


    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true).
                replace(R.id.maFragment, fragment).commit();
    }
}