package com.example.bhattausers.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.bhattausers.MainActivity;
import com.example.bhattausers.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private EditText email, password;
    private TextView forgotPassword;
    private Button LoginButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        forgotPassword = findViewById(R.id.forgotPasswordTextVierw);
        LoginButton = findViewById(R.id.loginButton);

        //Forgot password clicked
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty()) {
                    email.requestFocus();
                    email.setError("Enter your password");
                } else if (!email.getText().toString().contains("@gmail.com")) {
                    email.requestFocus();
                    email.setError("Email is wrong");
                } else if (password.getText().toString().isEmpty()) {
                    password.requestFocus();
                    password.setError("Enter your password");
                } else if (password.getText().toString().length() < 8) {
                    password.requestFocus();
                    password.setError("Password more than 8 character");
                }else{
                   showProgressDialog();
                    LoginAccount();
                }
            }
        });
    }

    private void LoginAccount() {
        auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Error is "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void GotoRegisterActivity(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this, R.style.CustomDialog);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Logging in. Please wait.");
        progressDialog.show();
    }
}