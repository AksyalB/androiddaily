package com.example.tubes_aksyal.HOME;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tubes_aksyal.DataAdapter;
import com.example.tubes_aksyal.DataHelper;
import com.example.tubes_aksyal.ItemClickListener;
import com.example.tubes_aksyal.R;
import com.example.tubes_aksyal.SharePreferences;

import java.util.ArrayList;


public class homeFragment extends Fragment {

    TextView homeusername;
    private SharePreferences sharePreferences;
    ImageView homeprof;
    DataHelper dataHelper;
    RecyclerView recyclerView;
    DataAdapter dataAdapter;
    ArrayList<String> id_id, username_id, hwyd_id, school_id, work_id, note_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        dataHelper = new DataHelper(requireContext());
        sharePreferences = new SharePreferences(requireContext());
        recyclerView = view.findViewById(R.id.rc_view);
        homeusername = view.findViewById(R.id.home_username);
        homeprof = view.findViewById(R.id.home_profile);


        //data yang akan ditampilkan di recycleview
        id_id = new ArrayList<>();
        username_id = new ArrayList<>();
        hwyd_id = new ArrayList<>();
        school_id = new ArrayList<>();
        work_id = new ArrayList<>();
        note_id = new ArrayList<>();
        storearray();


        dataAdapter = new DataAdapter(requireContext(), id_id, username_id, hwyd_id, school_id, work_id, note_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(dataAdapter);

        dataAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Panggil metode yang menangani klik item di sini
                handleItemClick(position);
            }
        });

        // Dapatkan nilai username dari SharedPreferences
        String username = sharePreferences.getUsername();
        // Setel nilai username ke TextView
        homeusername.setText(username);

        //dapatkan data dari sharepreference buat gambar
        String imagePath = sharePreferences.getImagePath();
        if (!imagePath.isEmpty()) {
            // Load the image using Glide or any other image-loading library
            Glide.with(requireContext())
                    .load(imagePath)
                    .into(homeprof);
        }
        return view;
    }
    private void storearray(){
        Cursor cursor = dataHelper.getData();
        Log.d("CursorCount", "data row yg ada di cursor: " + cursor.getCount());
        if(cursor.getCount() == 0){
            Toast.makeText(requireContext(), "data tidak ada", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                id_id.add(cursor.getString(0));  // Add ID
                username_id.add(cursor.getString(1));
                hwyd_id.add(cursor.getString(2));
                school_id.add(cursor.getString(3));
                work_id.add(cursor.getString(4));
                note_id.add(cursor.getString(5));

                Log.d("RowData", "ID: " + cursor.getString(0) +
                        ", Username: " + cursor.getString(1) +
                        ", Hwyd: " + cursor.getString(2) +
                        ", School: " + cursor.getString(3) +
                        ", Work: " + cursor.getString(4) +
                        ", Note: " + cursor.getString(5));
            }
        }

    }
    private void handleItemClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("pilihan")
                .setItems(new CharSequence[]{"Update Data", "Hapus Data"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent updatedata = new Intent(getActivity(), UpdateData.class);
                                updatedata.putExtra("username", position);
                                startActivity(updatedata);
                                break;
                            case 1:
                                deleteData(position);
                                break;

                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle cancellation if needed
                    }
                });

        builder.create().show();
    }

    private void deleteData(int position) {
        // Get the ID of the item to be deleted based on its position in the RecyclerView
        String itemIdToDelete = id_id.get(position).toString();

        dataHelper.deletedata(requireContext(), itemIdToDelete);

        // item yang dihapus
        id_id.remove(position);
        username_id.remove(position);
        hwyd_id.remove(position);
        school_id.remove(position);
        work_id.remove(position);
        note_id.remove(position);

        // untuk memberitahu bahwa data sudah dihapus dan diupdate jumlah rownya
        dataAdapter.notifyDataSetChanged();
    }





}