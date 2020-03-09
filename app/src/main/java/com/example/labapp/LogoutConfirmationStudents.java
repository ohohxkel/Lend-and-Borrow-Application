package com.example.labapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogoutConfirmationStudents extends AppCompatActivity {
    Button button_confirm_logout, button_deny_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_confirmation_students);


        button_confirm_logout = findViewById(R.id.button_confirm_logout);
        button_confirm_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogoutConfirmationStudents.this, MainActivity.class);
                startActivity(intent);
            }
        });
        button_deny_logout = findViewById(R.id.button_deny_logout);
        button_deny_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogoutConfirmationStudents.this, StudentHome.class);
                startActivity(intent);
            }
        });
    }
}