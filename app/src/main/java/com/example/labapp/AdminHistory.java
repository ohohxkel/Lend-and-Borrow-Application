package com.example.labapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Query.Direction;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.Timestamp;


import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class AdminHistory extends AppCompatActivity{
    Button button_back;

    private FirebaseFirestore fStore;
    private TextView textViewTransactionNumber, textViewReturned, textViewDate;
    private ArrayList<UserBorrow> mUserBorrow = new ArrayList<>();
    public static final String TAG = "AdminHistory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_history);

        fStore = FirebaseFirestore.getInstance();
        textViewTransactionNumber = (TextView)  findViewById(R.id.textViewTransactionNumber);
        textViewReturned = (TextView) findViewById(R.id.textViewReturned);
        textViewDate = findViewById(R.id.textViewDate);

        textViewReturned.setText("");
        textViewDate.setText("");

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

        loadTransactions();

    }

    private void loadTransactions(){
        CollectionReference users = fStore.collection("users");
        fStore.collectionGroup("Borrow")
                .whereEqualTo("returned", "false")
                .orderBy("borrowedDate", Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG, task.getResult().toString());
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                  Log.d(TAG, document.getId() + " => " + document.getData());

                                  UserBorrow userBorrow = document.toObject(UserBorrow.class);
                                  mUserBorrow.add(userBorrow);


                                  Timestamp borrowedDate = userBorrow.getBorrowedDate();
                                    Date bDate = borrowedDate.toDate();
                                    String borrowedDateString = bDate.toString();

                                  boolean returned = userBorrow.isReturned();
                                    String returnedString = Boolean.toString(returned);



                                  textViewReturned.setText(returnedString);
                                  textViewDate.setText(borrowedDateString);


                               }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }

                    }
                });
    }



}
