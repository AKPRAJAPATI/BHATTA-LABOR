package com.example.bhattausers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;


    private String username;
    private String userProfile;
    private String userId;
    private String adminAuthid;
    private long rate_d,cutsofpachhisa_d,total_ents_d,katiEnts_d,money_d;

    private CircleImageView userDetailProfile;
    private TextView userDetailname;
    private TextView bharaiRate;
    private TextView cutsofPachhisa;
    private TextView total_ents;
    private TextView katiGayiEnts;
    private TextView money;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();
        getAdminId();

        username = getIntent().getStringExtra("mera_name");
        userId = getIntent().getStringExtra("authid");
        userProfile = getIntent().getStringExtra("meri_profile");

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        //////////id finding////////////////
        userDetailProfile = findViewById(R.id.userDetailImage);
        userDetailname = findViewById(R.id.userAddName);
        bharaiRate = findViewById(R.id.addRateOfBharai);
        cutsofPachhisa = findViewById(R.id.addCutsOfPchhissa);
        total_ents = findViewById(R.id.addTotalEnts);
        katiGayiEnts = findViewById(R.id.addKatiGayiEnts);
        money = findViewById(R.id.addMoney);

        getUserData();

        /////////////////////////////////////


    }

    private void getUserData() {
        databaseReference.child("Muneem").child(adminAuthid).child("users").child(userId).child("Store_data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
           if (snapshot.exists()){
               rate_d = snapshot.child("bharai_rate").getValue(long.class);
               cutsofpachhisa_d = snapshot.child("cuts_of_pachhisa").getValue(long.class);
               total_ents_d = snapshot.child("total_ents").getValue(long.class);
               katiEnts_d = snapshot.child("kati_gayi_ents").getValue(long.class);
               money_d = snapshot.child("money").getValue(long.class);

               bharaiRate.setText(String.valueOf(rate_d));
               cutsofPachhisa.setText(String.valueOf(cutsofpachhisa_d));

               money.setText(String.valueOf(money_d));
               total_ents.setText(String.valueOf(total_ents_d));
               katiGayiEnts.setText(String.valueOf(katiEnts_d));


           }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getAdminId() {
        SharedPreferences sharedPreferences = getSharedPreferences("admin_authId", MODE_PRIVATE);
        adminAuthid = sharedPreferences.getString("adminauthid", "");
    }
}