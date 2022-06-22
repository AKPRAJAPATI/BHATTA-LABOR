package com.example.bhattausers.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bhattausers.DetailActivity;
import com.example.bhattausers.Models.registerModel;
import com.example.bhattausers.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class mainAdapter extends RecyclerView.Adapter<mainAdapter.mainViewHolder> {

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    private Context context;
    private ArrayList<registerModel> arrayList;

    public mainAdapter(Context context, ArrayList<registerModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public mainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new mainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull mainViewHolder holder, int position) {


        registerModel model = arrayList.get(position);
        holder.username.setText(model.getName());
        holder.userNumber.setText(model.getPhone());
        Picasso.get().load(model.getImageUrl()).into(holder.userProfile);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("mera_name",model.getName());
                intent.putExtra("meri_profile",model.getImageUrl());
                intent.putExtra("authid",model.getUserId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class mainViewHolder extends RecyclerView.ViewHolder {
        CircleImageView userProfile;
        TextView username, userNumber;

        public mainViewHolder(@NonNull View itemView) {
            super(itemView);

            userProfile = itemView.findViewById(R.id.userListImage);
            username = itemView.findViewById(R.id.userlistName);
            userNumber = itemView.findViewById(R.id.userlistPhoneNumebr);

        }
    }
}
