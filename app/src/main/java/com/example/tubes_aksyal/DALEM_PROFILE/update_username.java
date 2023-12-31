package com.example.tubes_aksyal.DALEM_PROFILE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tubes_aksyal.DataHelper;
import com.example.tubes_aksyal.HOME.Home;
import com.example.tubes_aksyal.R;
import com.example.tubes_aksyal.SharePreferences;

public class update_username extends AppCompatActivity {

    private EditText unamelama, unamebaru;
    private Button btnsaveuname;
    private DataHelper dataHelper;

    private SharePreferences sharePreferences;

    ImageButton back_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_username);

        dataHelper = new DataHelper(this);
        sharePreferences = new SharePreferences(this);
        unamelama = findViewById(R.id.inputunamelama);
        unamebaru = findViewById(R.id.inputunamebaru);
        btnsaveuname = findViewById(R.id.buttonupdateuname);
        back_user = findViewById(R.id.userback);

        btnsaveuname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unmlama = unamelama.getText().toString();
                String unmbaru = unamebaru.getText().toString();
                Boolean cekupdateuname = dataHelper.updateuname(unmlama, unmbaru);

                if (cekupdateuname) {
                    // Buat Intent untuk mengirim hasil kembali
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("username", unmbaru);

                    // Set hasil OK dan kirimkan hasil kembali
                    setResult(RESULT_OK, resultIntent);

                    // Simpan username baru ke SharedPreferences
                    sharePreferences.setUsername(unmbaru);

                    // Tampilkan log dan pindah ke Home
                    Log.d("NOTE", "Username baru: " + unmbaru);
                    Intent backToHomeIntent = new Intent(update_username.this, Home.class);
                    backToHomeIntent.putExtra("showprofilefragment", true);
                    startActivity(backToHomeIntent);
                    finish(); // Selesaikan aktivitas saat tombol ditekan
                    Toast.makeText(update_username.this, "Update username berhasil", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(update_username.this, "Update username gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backuser = new Intent(getApplicationContext(), Home.class);
                backuser.putExtra("showprofilefragment", true);
                startActivity(backuser);
            }
        });
    }
}