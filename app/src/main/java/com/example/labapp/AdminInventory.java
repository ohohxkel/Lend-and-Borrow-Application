package com.example.labapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AdminInventory extends AppCompatActivity {
    Button button_back, button_load;
    TextView itemsAvailable;


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


    //display items available from the Inventory (firestore)
    public void displayItems() {
        itemsRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data = "";

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    User user = documentSnapshot.toObject(User.class);
                    for (String tag : user.getTags()) {
                        data += "\n-" + tag;
                    }
                }
                itemsAvailable.setText(data);
            }
        });
    }
}










