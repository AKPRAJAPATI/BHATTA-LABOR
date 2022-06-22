package com.example.bhattausers.Splash_Screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bhattausers.MainActivity;
import com.example.bhattausers.R;


public class Splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 startActivity(new Intent(getApplicationContext(), MainActivity.class));
                 finish();
             }
         },3000);
    }
}