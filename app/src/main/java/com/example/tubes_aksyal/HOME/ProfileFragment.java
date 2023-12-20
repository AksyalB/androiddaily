package com.example.tubes_aksyal.HOME;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tubes_aksyal.DALEM_PROFILE.update_password;
import com.example.tubes_aksyal.DALEM_PROFILE.update_username;
import com.example.tubes_aksyal.R;


public class ProfileFragment extends Fragment {

    CardView usernameprof, passwordprof;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        usernameprof = view.findViewById(R.id.update_username);
        passwordprof = view.findViewById(R.id.update_password);

        usernameprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), update_username.class);
                startActivity(intent);
            }
        });

        passwordprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), update_password.class);
                startActivity(intent);
            }
        });

        return view;


    }


}