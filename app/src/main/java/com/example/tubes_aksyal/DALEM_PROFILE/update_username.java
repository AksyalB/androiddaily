package com.example.tubes_aksyal.DALEM_PROFILE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tubes_aksyal.DataHelper;
import com.example.tubes_aksyal.R;

public class update_username extends AppCompatActivity {

    private EditText unamelama, unamebaru;
    private Button btnsaveuname;
    private DataHelper dataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_username);

        dataHelper = new DataHelper(this);

        unamelama = findViewById(R.id.inputunamelama);
        unamebaru = findViewById(R.id.inputunamebaru);
        btnsaveuname = findViewById(R.id.buttonupdateuname);

        btnsaveuname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unmlama = unamelama.getText().toString();
                String unmbaru = unamebaru.getText().toString();
                Boolean cekupdateuname = dataHelper.updateuname(unmlama, unmbaru);
                if(cekupdateuname == true){
                    Intent profileuname = new Intent(getApplicationContext(), com.example.tubes_aksyal.HOME.ProfileFragment.class);
                    profileuname.putExtra("username", unmbaru);
                    startActivity(profileuname);
                    finish();
                    Toast.makeText(update_username.this, "Update username berhasil", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(update_username.this, "Update username gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}