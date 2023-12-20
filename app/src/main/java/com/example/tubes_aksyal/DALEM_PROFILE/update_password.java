package com.example.tubes_aksyal.DALEM_PROFILE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tubes_aksyal.DataHelper;
import com.example.tubes_aksyal.HOME.ProfileFragment;
import com.example.tubes_aksyal.LOGREG.login;
import com.example.tubes_aksyal.R;

public class update_password extends AppCompatActivity {

    private EditText passlama, passbaru;
    private Button btnsave;
    private DataHelper dataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        dataHelper = new DataHelper(this);

        passlama = findViewById(R.id.inputpasslama);
        passbaru = findViewById(R.id.inputpassbaru);
        btnsave = findViewById(R.id.buttonupdatepass);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwdlama = passlama.getText().toString();
                String pwdbaru = passbaru.getText().toString();
                Boolean cekupdatepass = dataHelper.updatepass(pwdlama, pwdbaru);
                if(cekupdatepass == true){
                    Intent profile = new Intent(getApplicationContext(), com.example.tubes_aksyal.HOME.ProfileFragment.class);
                    profile.putExtra("password", pwdbaru);
                    startActivity(profile);
                    finish();
                    Toast.makeText(update_password.this, "Update password berhasil", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(update_password.this, "Update password gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}