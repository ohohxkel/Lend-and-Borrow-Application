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

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AdminInventory extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button button_back, button_submit;
    Spinner values;
    String item_values,item_values1, item_values2;
    EditText item,item2,item3,qrlist;

    ArrayList<String> itemsAdd;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference itemsRef = db.collection("Inventory");
    CollectionReference usersRef = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inventory);
        item = findViewById(R.id.item);
        item2 = findViewById(R.id.item2);
        item3 = findViewById(R.id.item3);
        button_submit = findViewById(R.id.button_submit);
        qrlist = findViewById(R.id.qrlist);


        Spinner mySpinner = (Spinner) findViewById(R.id.spinner_extension);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AdminInventory.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.idCode_extension));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        mySpinner.setOnItemSelectedListener(onItemSelectedListener0);

        Spinner mySpinner1 = (Spinner) findViewById(R.id.spinner_projector);

        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(AdminInventory.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.idCode_projector));
        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner1.setAdapter(myAdapter1);
        mySpinner1.setOnItemSelectedListener(onItemSelectedListener1);

        Spinner mySpinner2 = (Spinner) findViewById(R.id.spinner_laptop);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(AdminInventory.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.idCode_laptop));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner2.setAdapter(myAdapter2);
        mySpinner2.setOnItemSelectedListener(onItemSelectedListener2);



        button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminInventory.this, AdminHome.class);
                startActivity(intent);
            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ArrayList<String> itemsAdd = new ArrayList<String>();
                itemsAdd = new ArrayList<>();

                itemsAdd.add(0, item_values);
                itemsAdd.add(1,item_values1);
                itemsAdd.add(2, item_values2);
                qrlist.setText(itemsAdd.toString());

                String user = "2018-00560-MN-0";
                for (int i = 0; i < itemsAdd.size(); i++) {

                    itemsRef.document("Items").update("tags", FieldValue.arrayRemove(itemsAdd.get(i)));
                    usersRef.document(user).update("Borrow", FieldValue.arrayUnion(itemsAdd.get(i)));
                }


            }
        });
    }


    OnItemSelectedListener onItemSelectedListener0 = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            item_values = parent.getItemAtPosition(position).toString();
            item.setText(item_values);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    OnItemSelectedListener onItemSelectedListener1 = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            item_values1 = parent.getItemAtPosition(position).toString();
            item2.setText(item_values1);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    OnItemSelectedListener onItemSelectedListener2 = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            item_values2 = parent.getItemAtPosition(position).toString();
            item3.setText(item_values2);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
