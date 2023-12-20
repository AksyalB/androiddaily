package com.example.tubes_aksyal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tubes_aksyal.LOGREG.login;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                startActivity(new Intent(SplashScreen.this, login.class));
                finish();
             }
         }, 2000);
    }
}
