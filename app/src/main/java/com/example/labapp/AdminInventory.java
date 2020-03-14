package com.example.labapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AdminInventory extends AppCompatActivity {
    Button button_back, button_load;
    TextView itemsAvailable;
    ArrayList<String> group;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference itemsRef = db.collection("Inventory");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inventory);

        itemsAvailable = (TextView) findViewById(R.id.itemsAvailable);

        displayItems();


        button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminInventory.this, AdminHome.class);
                startActivity(intent);
            }
        });


        button_load = findViewById(R.id.button_load);
        button_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminInventory.this, AdminInventory.class);
                startActivity(intent);
            }
        });

    }


    private void displayItems() {
        itemsRef.document("Items").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();


                    List<String> group =  (List<String>) document.get("tags");

                    itemsAvailable.setText(group.toString());

                    }

                }
            });
        };


    }










