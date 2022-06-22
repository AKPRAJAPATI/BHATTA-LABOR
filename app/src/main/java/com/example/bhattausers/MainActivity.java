package com.example.bhattausers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bhattausers.Adapter.mainAdapter;
import com.example.bhattausers.Models.registerModel;
import com.example.bhattausers.authentication.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    private ConstraintLayout userNotFoundLayout;
    private RecyclerView recyclerView;
    private mainAdapter adapter;
    private ArrayList<registerModel> arrayList;

    //upper top mai toolbar data
    private CircleImageView userprofileCircleImageView;
    private TextView userNameTextview;
    private TextView userPhoneNumberTextView;


    private String adminAuthId;
    private String get_adminAuthId;
    String username,userNumber, profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        checkUsers();
        getAdminAuthId();

        getOurProfile();
        //////////////////////////////////////////////////FIND IDS///////////////////////////////////////////////////////
        userNotFoundLayout = findViewById(R.id.constraintNotUserFoundLayout);
        recyclerView = findViewById(R.id.recyclerviewUserList);

        userprofileCircleImageView = findViewById(R.id.userProfile);
        userNameTextview = findViewById(R.id.userName);
        userPhoneNumberTextView = findViewById(R.id.userNumber);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<>();

        databaseReference.child("Muneem").child(get_adminAuthId).child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    userNotFoundLayout.setVisibility(View.GONE);
                    arrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        registerModel model = dataSnapshot.getValue(registerModel.class);
                        if (!auth.getUid().equals(model.getUserId())){
                            arrayList.add(model);
                        }
                    }
                } else {
                    recyclerView.setVisibility(View.GONE);
                    userNotFoundLayout.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.keepSynced(true);
        adapter = new mainAdapter(getApplicationContext(),arrayList);
        recyclerView.setAdapter(adapter);
    }

    private void checkUsers() {
        if (firebaseUser == null) {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        }
    }

    private void getAdminAuthId() {
        SharedPreferences sharedPreferences = getSharedPreferences("admin_authId", MODE_PRIVATE);
        get_adminAuthId = sharedPreferences.getString("adminauthid", "");

    }

    private void getOurProfile() {
        databaseReference.child("Muneem").child(get_adminAuthId).child("users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (snapshot.exists()){
               username = snapshot.child("name").getValue(String.class);
               userNumber = snapshot.child("phone").getValue(String.class);
               profile = snapshot.child("imageUrl").getValue(String.class);

               userNameTextview.setText(username);
               userPhoneNumberTextView.setText(userNumber);
               Picasso.get().load(profile).into(userprofileCircleImageView);

               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error is "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void GOTO_OUR_INFORMATION(View view) {
        Intent intent = new Intent(getApplicationContext(),ourDetailActivity.class);
        intent.putExtra("mera_name",username);
        intent.putExtra("meri_profile",profile);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}