package com.example.tubes_aksyal.HOME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.tubes_aksyal.DataHelper;
import com.example.tubes_aksyal.R;
import com.example.tubes_aksyal.SharePreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UpdateData extends AppCompatActivity {

    TextView dateskrng;
    RadioGroup hwyd, school, workradio;
    EditText note;
    private DataHelper dbhelper;
    Button savedata;
    Cursor cursor;
    String username;
    private SharePreferences sharePreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        dbhelper = new DataHelper(this);
        dateskrng = findViewById(R.id.date2);
        hwyd = (RadioGroup) findViewById(R.id.daymood2);
        school = (RadioGroup) findViewById(R.id.sekolah2);
        workradio = (RadioGroup) findViewById(R.id.kerja2);
        savedata =  findViewById(R.id.btnupdate);
        note =  findViewById(R.id.crtactivity2);
        sharePreferences = new SharePreferences(this);

        username = sharePreferences.getUsername();

        SQLiteDatabase db = dbhelper.getWritableDatabase();
        cursor = db.rawQuery("select * from addata where username = " + getIntent().getStringExtra("username"), null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){

            cursor.moveToPosition(0);
            int hwydValue = cursor.getInt(0);
            int schoolValue = cursor.getInt(1);
            int workValue = cursor.getInt(2);
            note.setText(cursor.getString(3));

            // Assuming you have RadioGroups and RadioButtons in your layout
            RadioGroup hwydRadioGroup = findViewById(R.id.daymood2);
            RadioGroup schoolRadioGroup = findViewById(R.id.sekolah2);
            RadioGroup workRadioGroup = findViewById(R.id.kerja2);

            // Set checked status for RadioButtons
            setRadioButtonCheckedStatus(hwydRadioGroup, hwydValue);
            setRadioButtonCheckedStatus(schoolRadioGroup, schoolValue);
            setRadioButtonCheckedStatus(workRadioGroup, workValue);

            Log.d("cekdata", "hwydValue: " + hwydValue);
            Log.d("cekdata", "schoolValue: " + schoolValue);
            Log.d("cekdata", "workValue: " + workValue);

        }



        savedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataUpdateToSQLite();
            }
        });

        String datenow = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
        TextView dateskrng = findViewById(R.id.date2); // replace with your actual TextView ID
        dateskrng.setText(datenow);
    }

    private void saveDataUpdateToSQLite() {
        // Ambil nilai yang dipilih dari RadioGroup
        int selectedHwydId = hwyd.getCheckedRadioButtonId();
        int selectedSchoolId = school.getCheckedRadioButtonId();
        int selectedWorkId = workradio.getCheckedRadioButtonId();

        Log.e("cekdata2", String.valueOf(selectedHwydId));

        // Lakukan sesuatu dengan nilai yang dipilih (contoh: dapatkan id dari RadioButton yang dipilih)
        String selectedHwydText = getSelectedRadioButtonText(selectedHwydId);
        String selectedSchoolText = getSelectedRadioButtonText(selectedSchoolId);
        String selectedWorkText = getSelectedRadioButtonText(selectedWorkId);

        String noteText = note.getText().toString();

        Log.e("MY", username);
        Log.e("MY", selectedHwydText);
        Log.e("MY", selectedSchoolText);
        Log.e("MY", selectedWorkText);
        Log.e("MY", noteText);


        // Simpan data ke SQLite atau tempat penyimpanan data lainnya
        try {
            dbhelper.updatedata(username, selectedHwydText, selectedSchoolText, selectedWorkText, noteText);
        } catch (Exception e) {
            Log.d("NOTE", e.toString());
        }
    }

    private String getSelectedRadioButtonText(int selectedId) {
        RadioButton selectedRadioButton = findViewById(selectedId);
        return selectedRadioButton != null ? selectedRadioButton.getText().toString() : "";
    }

    private void setRadioButtonCheckedStatus(RadioGroup radioGroup, int checkedValue) {
        // Iterate through each RadioButton in the RadioGroup
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View radioButton = radioGroup.getChildAt(i);
            if (radioButton instanceof RadioButton) {
                // Check the RadioButton if its tag matches the checkedValue
                RadioButton currentRadioButton = (RadioButton) radioButton;
                if (Integer.parseInt(currentRadioButton.getTag().toString()) == checkedValue) {
                    currentRadioButton.setChecked(true);
                }
            }
        }
    }




    }