package com.example.labapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firestore.admin.v1beta1.Progress;


public class StudentLogin extends AppCompatActivity implements View.OnClickListener {

    Button button_login;

    private FirebaseAuth mAuth;
    private EditText editTextEmail, editTextPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = (EditText) findViewById(R.id.login_student_email);
        editTextPassword = (EditText) findViewById(R.id.login_student_password);
        progressDialog = new ProgressDialog(this);

        button_login = findViewById(R.id.button_login);
        button_login.setOnClickListener(this);

    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stop from registering further code hekhek
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            //stop from registering further code hekhek
            return;
        }

        progressDialog.setMessage("Logging in");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            progressDialog.hide();
                            Toast.makeText(StudentLogin.this, "Login Successfully" , Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(StudentLogin.this, StudentHome.class);
                            startActivity(intent);

                        } else {
                            String e = ((FirebaseAuthException) task.getException()).getErrorCode();
                            Toast.makeText(StudentLogin.this, "Login Failed" + e, Toast.LENGTH_SHORT).show();
                            Log.e("LoginActivity", "Failed Login");
                        }


                    }
                });
    }


        @Override
        public void onClick(View view) {
            if(view == button_login) {
                loginUser();
            }

        }}


