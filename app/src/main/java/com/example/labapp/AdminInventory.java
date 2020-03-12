package com.example.labapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminInventory extends AppCompatActivity {
    Button button_back, button_submit;
    String item_values1, item_values2, item_values3, item_values4;
    String currentUserID;


    ArrayList<String> itemsAdd;
    ArrayList<String> group;

    TextView displayItems;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference itemsRef = db.collection("Inventory");

    CollectionReference usersRef = db.collection("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inventory);

        displayItems = (TextView) findViewById(R.id.textDisplayItems);


        StudentScanner getData = new StudentScanner();

        getData.itemsName();


        button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminInventory.this, AdminHome.class);
                startActivity(intent);
            }
        });

        borrowItems();


    }


    public void borrowItems() {
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User();
                String currentUserID = user.getStudentNumber();

                itemsAdd = new ArrayList<>();


                itemsAdd.add(0, item_values1);
                itemsAdd.add(1, item_values2);
                itemsAdd.add(2, item_values3);
                itemsAdd.add(3, item_values4);
                itemsAdd.add(4, item_values4);


                //get the items from array, add and remove it from certain documents in firebase
                for (int i = 0; i < itemsAdd.size(); i++) {

                    itemsRef.document("Items").update("tags", FieldValue.arrayRemove(itemsAdd.get(i)));
                    usersRef.document(currentUserID).update("Borrow", FieldValue.arrayUnion(itemsAdd.get(i)));


                }


            }
        });


    }

    private void displayItems(View view) {
        itemsRef.document("Items").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    List<String> group = (List<String>) document.get("tags");

                    displayItems.setText(group.toString());
                }

            }
        });

    }
}










