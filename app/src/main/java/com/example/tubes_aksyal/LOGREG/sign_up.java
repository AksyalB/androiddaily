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

public class sign_up extends AppCompatActivity {

    protected Cursor cursor;
    DataHelper datahelper;
    Button registerbtn;
    EditText regusername, regpassword;

    TextView buttext_signup;

    boolean passvisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        datahelper = new DataHelper(this);
        regusername = findViewById(R.id.sign_up_username);
        regpassword = findViewById(R.id.sign_up_password);
        registerbtn = findViewById(R.id.button_signup);
        buttext_signup = findViewById(R.id.buttontext_signup);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regusern = regusername.getText().toString();
                String regpassw = regpassword.getText().toString();

                if(regusern.equals("")||regpassw.equals("")){
                    Toast.makeText(sign_up.this, "tolong isi username dan password", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean cekuser = datahelper.cekusername(regusern);
                    if(cekuser == false){
                        Boolean databaru = datahelper.insertData(regusern, regpassw);

                        if(databaru == true){
                            Toast.makeText(sign_up.this, "registrasi berhasil", Toast.LENGTH_SHORT).show();
                            Intent regtolog = new Intent(getApplicationContext(), login.class);
                            startActivity(regtolog);
                        }

                    } else {
                        Toast.makeText(sign_up.this, "username sudah digunakan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        buttext_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign_up = new Intent(getApplicationContext(),login.class);
                startActivity(sign_up);
            }
        });

        regpassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int right =2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=regpassword.getRight()-regpassword.getCompoundDrawables()[right].getBounds().width()){
                        int selection =regpassword.getSelectionEnd();
                        if(passvisible){
                            regpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility_off, 0);
                            regpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passvisible=false;
                        } else {
                            regpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility, 0);
                            regpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passvisible=true;

                        }
                        regpassword.setSelection(selection);
                        return true;

                    }
                }
                return false;
            }
        });
    }
}