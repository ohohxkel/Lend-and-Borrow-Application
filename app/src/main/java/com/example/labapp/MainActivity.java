package com.example.labapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends AppCompatActivity {

    Button button_admin;
    Button button_student;
    Button button_hidden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_admin = findViewById(R.id.button_admin);
        button_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , AdminEnterCode.class);
                startActivity(intent);
            }
        });

        button_student =findViewById(R.id.button_student);
        button_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , StudentLogin.class);
                startActivity(intent);
            }
        });



        button_hidden = findViewById(R.id.button_hidden);
        button_hidden.setOnClickListener(new View.OnClickListener() {
            int i = 0;

            @Override
            public void onClick(View v) {
                i++;
                if (i == 5) {
                    i = 0;
                    button_admin.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    @Override
    public void onBackPressed(){

    }
}




