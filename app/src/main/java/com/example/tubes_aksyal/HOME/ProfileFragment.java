package com.example.tubes_aksyal.HOME;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tubes_aksyal.DALEM_PROFILE.update_password;
import com.example.tubes_aksyal.DALEM_PROFILE.update_username;
import com.example.tubes_aksyal.DataHelper;
import com.example.tubes_aksyal.LOGREG.login;
import com.example.tubes_aksyal.R;
import com.example.tubes_aksyal.SharePreferences;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    CardView usernameprof, passwordprof;
    private SharePreferences sharePreferences;
    Button logutbtn;
    TextView profusername;
    CircleImageView profileimg;
    DataHelper dataHelper;

//    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_CODE_UPDATE_USERNAME = 1;
    private static final int REQUEST_CODE_PICK_IMAGE = 2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        dataHelper = new DataHelper(requireContext());
        sharePreferences = new SharePreferences(requireContext());
        usernameprof = view.findViewById(R.id.update_username);
        passwordprof = view.findViewById(R.id.update_password);
        logutbtn = view.findViewById(R.id.logout);
        profusername = view.findViewById(R.id.username_profile);
        profileimg = view.findViewById(R.id.image_profile);


        // Dapatkan nilai username dari SharedPreferences
        String username = sharePreferences.getUsername();
        Log.d("NOTE", "INI TERCAP: " + username);
        // Setel nilai username ke TextView
        profusername.setText(username);

        usernameprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), update_username.class);
                startActivityForResult(intent, 1);
            }
        });

        passwordprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), update_password.class);
                startActivity(intent);
            }
        });

        logutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keluar = new Intent(getActivity(), login.class);
                sharePreferences.clearSession();
                startActivity(keluar);
            }
        });

        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_CODE_PICK_IMAGE);
            }
        });

        //nilai image update dari sharedPreferences
        String imagePath = sharePreferences.getImagePath();
        if (!imagePath.isEmpty()) {
            // Tampilkan gambar profil menggunakan Glide atau cara lainnya
            Glide.with(this)
                    .load(imagePath)
                    .into(profileimg);
        }

        return view;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_UPDATE_USERNAME) {
                Log.d("NOTE", "Ngaco");
                // Dapatkan nilai "username" dari Intent hasil kembali
                String updatedUsername = data.getStringExtra("username");

                // Update nilai username di SharedPreferences
                sharePreferences.setUsername(updatedUsername);

                // Setel nilai username ke TextView
                profusername.setText(updatedUsername);
            } else if (requestCode == REQUEST_CODE_PICK_IMAGE) {
                Log.d("NOTE", "bener");

                // Dapatkan URI gambar dari Intent hasil kembali
                Uri selectedImageUri = data.getData();

                // Setelah mendapatkan URI gambar, Anda dapat mengonversinya ke path
                String imagePath = getPathFromUri(selectedImageUri);

                Log.d("ProfileFragment", "Image Path: " + imagePath);

                // Panggil metode updateImage untuk menyimpan gambar ke basis data
                dataHelper.updateImage(sharePreferences.getUsername(), imagePath);

                // Tampilkan gambar yang baru dipilih
                Glide.with(this)
                        .load(imagePath)
                        .into(profileimg);
            }
        }
    }

    // Metode untuk mengonversi URI gambar menjadi path
    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = requireActivity().getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        } else {
            return uri.getPath();
        }
    }


}