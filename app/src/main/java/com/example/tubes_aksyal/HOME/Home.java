package com.example.tubes_aksyal.HOME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
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
            replacefragment(new TambahdataFragment());
            }
        });

    }

    private void replacefragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}