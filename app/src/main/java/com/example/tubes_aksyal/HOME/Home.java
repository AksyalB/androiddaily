package com.example.tubes_aksyal.HOME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.tubes_aksyal.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Home extends AppCompatActivity {

    BottomNavigationView bottomnavi;
    FloatingActionButton floatbot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomnavi = findViewById(R.id.bottomnav);
        floatbot = findViewById(R.id.floatbottom);
        replacefragment(new homeFragment());
        bottomnavi.setBackground(null);

        bottomnavi.setOnItemSelectedListener(item -> {

            int id = item.getItemId();
            if(id == R.id.home){
                replacefragment(new homeFragment());

            } else if (id==R.id.profile){
                replacefragment(new ProfileFragment());

            }
            return true;
        });

        floatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();

                TambahdataFragment tambahdataFragment = new TambahdataFragment();

                Bundle bundle = new Bundle();
                String username = intent.getStringExtra("username");; // Ganti dengan data yang ingin Anda kirim
                bundle.putString("USERNAME", username); // Memberi data string ke Bundle dengan kunci tertentu

                // Menetapkan Bundle ke fragment
                tambahdataFragment.setArguments(bundle);

                replacefragment(new TambahdataFragment());
            }
        });

        intentfragmentprofile();

    }

    private void replacefragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void intentfragmentprofile() {
        // Mendapatkan informasi dari Intent
        boolean showProfileFragment = getIntent().getBooleanExtra("showprofilefragment", false);

        if (showProfileFragment) {
            // Membuat instance dari ProfileFragment
            ProfileFragment profileFragment = new ProfileFragment();
            // Memulai transaksi fragment untuk menampilkan ProfileFragment
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, profileFragment);
            fragmentTransaction.addToBackStack(null); // Agar dapat kembali ke update_password jika diperlukan
            fragmentTransaction.commit();
        }
    }
}

