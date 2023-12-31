package com.example.tubes_aksyal;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tubes_aksyal.HOME.UpdateData;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataAdapterView> {

    private Context context;
    private ArrayList id_id, username_id, hwyd_id, school_id, work_id, note_id;
    private ItemClickListener itemClickListener;
    int position;



    public DataAdapter(Context context, ArrayList id_id, ArrayList username_id, ArrayList hwyd_id, ArrayList school_id, ArrayList work_id, ArrayList note_id){
        this.context = context;
        this.id_id = id_id;
        this.username_id = username_id;
        this.hwyd_id = hwyd_id;
        this.school_id = school_id;
        this.work_id = work_id;
        this.note_id = note_id;

    }

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }


    @NonNull
    @Override
    public DataAdapter.DataAdapterView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listaddata, parent, false);
        return new DataAdapterView (view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.DataAdapterView holder, int position) {
        this.position = position;
        holder.usernametv.setText(String.valueOf(username_id.get(position)));
        holder.moodTextView.setText(String.valueOf(hwyd_id.get(position)));
        holder.schoolTextView.setText(String.valueOf(school_id.get(position)));
        holder.workTextView.setText(String.valueOf(work_id.get(position)));
        holder.noteTextView.setText(String.valueOf(note_id.get(position)));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateData.class);
                intent.putExtra("id", String.valueOf(id_id.get(position)));
                intent.putExtra("username", String.valueOf(username_id.get(position)));
                intent.putExtra("hwyd", String.valueOf(hwyd_id.get(position)));
                intent.putExtra("school", String.valueOf(school_id.get(position)));
                intent.putExtra("work", String.valueOf(work_id.get(position)));
                intent.putExtra("note", String.valueOf(note_id.get(position)));
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil metode onItemClick pada ItemClickListener
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return id_id.size();
    }

    public class DataAdapterView extends RecyclerView.ViewHolder {
        // Declare your TextViews here
        public TextView usernametv, moodTextView, schoolTextView, workTextView, noteTextView;
        public CardView cardView;
        public DataAdapterView (@NonNull View itemView) {
            super(itemView);
            // Initialize your TextViews here
            usernametv = itemView.findViewById(R.id.usernameid);
            moodTextView = itemView.findViewById(R.id.hwyddata);
            schoolTextView = itemView.findViewById(R.id.schooldata);
            workTextView = itemView.findViewById(R.id.workdata);
            noteTextView = itemView.findViewById(R.id.notedata);
            cardView = itemView.findViewById(R.id.itemlayout);

            itemView.setOnClickListener(null);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Panggil metode onItemClick pada ItemClickListener
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });

        }
    }
}
