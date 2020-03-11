package com.example.labapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class AdminInventory extends AppCompatActivity{
    Button button_back, button_submit;
    Spinner values;
    String item_values1, item_values2,item_values3, item_values4,item_values5, item_values6,item_values7;
    String currentUserID;
    EditText item,item2,item3,qrlist;

    ArrayList<String> itemsAdd;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference itemsRef = db.collection("Inventory");
    CollectionReference usersRef = db.collection("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inventory);


        button_submit = findViewById(R.id.button_submit);
        qrlist = findViewById(R.id.qrlist);

        StudentScanner getData = new StudentScanner();

        getData.itemNames();


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


    public void borrowItems(){
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemsAdd = new ArrayList<>();


                itemsAdd.add(0, item_values1);
                itemsAdd.add(1, item_values2);
                itemsAdd.add(2, item_values3);
                itemsAdd.add(3, item_values4);
                itemsAdd.add(4, item_values4);


                qrlist.setText(itemsAdd.toString());

                //get the items from array, add and remove it from certain documents in firebase
                for (int i = 0; i < itemsAdd.size(); i++) {

                    itemsRef.document("Items").update("tags", FieldValue.arrayRemove(itemsAdd.get(i)));
                    usersRef.document(currentUserID).update("Borrow", FieldValue.arrayUnion(itemsAdd.get(i)));
                }


            }
        });
    }





}
