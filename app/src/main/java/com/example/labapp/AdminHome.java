package com.example.labapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHome extends AppCompatActivity {
    Button button_admin_register, button_admin_generate, button_admin_history, button_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        button_admin_register= findViewById(R.id.button_admin_register);
        button_admin_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, AdminRegister.class);
                startActivity(intent);
            }
        });
        button_admin_generate= findViewById(R.id.button_admin_generate);
        button_admin_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, AdminGenerate.class);
                startActivity(intent);
            }
        });
        button_admin_history= findViewById(R.id.button_admin_history);
        button_admin_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, AdminHistory.class);
                startActivity(intent);
            }
        });
        button_logout= findViewById(R.id.button_logout);
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, LogoutConfirmation.class);
                startActivity(intent);
            }
        });
    }
}
