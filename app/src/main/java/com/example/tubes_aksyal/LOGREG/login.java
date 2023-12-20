package com.example.tubes_aksyal.LOGREG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tubes_aksyal.DataHelper;
import com.example.tubes_aksyal.R;

public class login extends AppCompatActivity {

    protected Cursor cursor;
    DataHelper datahelper;
    Button loginbtn;
    EditText logusername, logpassword;

    TextView buttext_signin;
    boolean passvisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        datahelper = new DataHelper(this);
        logusername = findViewById(R.id.sign_in_username);
        logpassword = findViewById(R.id.sign_in_password);
        loginbtn = findViewById(R.id.button_signin);
        buttext_signin = findViewById(R.id.buttontext_signin);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String logusern = logusername.getText().toString();
                String logpassw = logpassword.getText().toString();

                if(logusern.equals("")||logpassw.equals("")){
                    Toast.makeText(login.this, "tolong isi username dan password", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean cekuserdanpass = datahelper.cekuserdanpass(logusern,logpassw);
                    if(cekuserdanpass == true){
                        Intent home = new Intent(getApplicationContext(), com.example.tubes_aksyal.HOME.Home.class);
                        home.putExtra("username", logusern);
                        home.putExtra("password", logpassw);
                        startActivity(home);
                        finish();
                        Toast.makeText(login.this, "login berhasil", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(login.this, "login gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        buttext_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign_up = new Intent(getApplicationContext(), com.example.tubes_aksyal.LOGREG.sign_up.class);
                startActivity(sign_up);
            }
        });

        logpassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int right =2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=logpassword.getRight()-logpassword.getCompoundDrawables()[right].getBounds().width()){
                        int selection =logpassword.getSelectionEnd();
                        if(passvisible){
                            logpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility_off, 0);
                            logpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passvisible=false;
                        } else {
                            logpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility, 0);
                            logpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passvisible=true;
                            
                        }
                        logpassword.setSelection(selection);
                        return true;
                        
                    }
                }
                return false;
            }
        });

    }

}