package com.example.tubes_aksyal.HOME;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.tubes_aksyal.DataAdapter;
import com.example.tubes_aksyal.DataHelper;
import com.example.tubes_aksyal.R;
import com.example.tubes_aksyal.SharePreferences;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class TambahdataFragment extends Fragment {

    TextView dateskrng;
    RadioGroup hwyd, school, workradio;
    EditText note;
    private DataHelper dbhelper;
    Button savedata;
    String username;
    private SharePreferences sharePreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambahdata, container, false);

        dbhelper = new DataHelper(requireContext());
        dateskrng = view.findViewById(R.id.date);
        hwyd = (RadioGroup)view.findViewById(R.id.daymood);
        school = (RadioGroup)view.findViewById(R.id.sekolah);
        workradio = (RadioGroup)view.findViewById(R.id.kerja);
        savedata = view.findViewById(R.id.btnsave);
        note = view.findViewById(R.id.crtactivity);
        sharePreferences = new SharePreferences(requireContext());

        username = sharePreferences.getUsername();

        savedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveDataToSQLite();
            }
        });

        String datenow = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
        dateskrng.setText(datenow);
        return view;
    }

    private void saveDataToSQLite() {
        // Ambil nilai yang dipilih dari RadioGroup
        int selectedHwydId = hwyd.getCheckedRadioButtonId();
        int selectedSchoolId = school.getCheckedRadioButtonId();
        int selectedWorkId = workradio.getCheckedRadioButtonId();

//        Log.e("MY", String.valueOf(selectedHwydId));

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
            dbhelper.saveData(username, selectedHwydText, selectedSchoolText, selectedWorkText, noteText);
        } catch (Exception e) {
            Log.d("NOTE", e.toString());
        }
    }

    private String getSelectedRadioButtonText(int selectedId) {
        RadioButton selectedRadioButton = getView().findViewById(selectedId);
        return selectedRadioButton != null ? selectedRadioButton.getText().toString() : "";
    }
}