package com.example.labapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class AdminHistory extends AppCompatActivity{
    Button button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_history);

        Spinner spinner = findViewById(R.id.spinner_slot1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.itemsAdd, R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner.setAdapter(adapter);

        button_back= findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHistory.this, AdminHome.class);
                startActivity(intent);
            }
        });
    }

}
