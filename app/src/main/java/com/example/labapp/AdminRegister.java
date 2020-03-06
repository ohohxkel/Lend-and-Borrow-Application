package com.example.labapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.proto.TargetGlobal;

import java.util.HashMap;
import java.util.Map;

import io.opencensus.tags.Tag;

public class AdminRegister extends AppCompatActivity implements View.OnClickListener {
    Button button_back;

    Button buttonConfirmRegistration;
    private EditText editTextEmail, editTextPassword, editTextFirstName, editTextLastName ,
            editTextStudentNumber, editTextYearAndSection;
   // private TextView textViewSignIn;
    String userID;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);
        final String TAG = AdminRegister.class.getName();

        //Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);

        //Initialize EditText by ids
        editTextFirstName = (EditText) findViewById(R.id.input_student_firstname);
        editTextLastName = (EditText) findViewById(R.id.input_student_lastname);
        editTextEmail = (EditText) findViewById(R.id.input_student_email);
        editTextPassword = (EditText) findViewById(R.id.input_student_password);
        editTextStudentNumber = (EditText) findViewById(R.id.input_student_number);
        editTextYearAndSection = (EditText) findViewById(R.id.input_student_yearandsection);

        //textViewSignIn = (TextView)  findViewById(R.id.textView2)
        buttonConfirmRegistration = (Button) findViewById(R.id.button_confirm);
        button_back = findViewById(R.id.button_back);


        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminRegister.this, AdminHome.class);
                startActivity(intent);
            }
        });

        buttonConfirmRegistration.setOnClickListener(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            //go to login page
        }
    }


    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String name = editTextLastName.getText().toString().trim() +", "+ editTextFirstName.getText().toString().trim();
        final String yearAndSection = editTextYearAndSection.getText().toString().trim();
        final String studentNumber = editTextStudentNumber.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stop from registering further code hekhek
            return;
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(name)) {
            editTextLastName.setError("Name required");
            editTextLastName.requestFocus();
            editTextFirstName.setError("Name required");
            editTextFirstName.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(studentNumber)) {
            editTextStudentNumber.setError("Student number required");
            editTextStudentNumber.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(yearAndSection)) {
            editTextYearAndSection.setError("Year and Section required");
            editTextYearAndSection.requestFocus();
            return;
        }



        progressDialog.setMessage("Registering student");
        progressDialog.show();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            // User user = new User(name, email, studentNumber, yearAndSec);
                            userID = mAuth.getCurrentUser().getUid();
                            Map <String, Object> user = new HashMap<>();
                            user.put("userID", userID);
                            user.put("name", name);
                            user.put("email", email);
                            user.put("studentNumber", studentNumber);
                            user.put("yearAndSection", yearAndSection);
                            DocumentReference documentReference = fStore.collection("users").document(studentNumber);

                              documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG", "onSuccess: User Profile is created for " + name);
                                    }
                                });


                            progressDialog.hide();
                            Toast.makeText(AdminRegister.this, "Registered Successfully",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(AdminRegister.this, AdminHome.class);
                            startActivity(intent);
                        }
                        else {
                            String e = ((FirebaseAuthException) task.getException()).getErrorCode();
                            Toast.makeText(AdminRegister.this, "Registration Failed" + e,Toast.LENGTH_SHORT).show();
                            Log.e("RegistrationActivity", "Failed Registration");
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if(view == buttonConfirmRegistration) {
            registerUser();
        }

    }











}
