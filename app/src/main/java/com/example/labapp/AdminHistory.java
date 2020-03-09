package com.example.labapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
    private CheckBox checkBoxReturned;
    private ArrayList<UserBorrow> mUserBorrow = new ArrayList<>();
    public static final String TAG = "AdminHistory";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_history);

        fStore = FirebaseFirestore.getInstance();
        textViewTransactionNumber = (TextView)  findViewById(R.id.textViewTransactionNumber);
<<<<<<< Updated upstream
        textViewReturned = (TextView) findViewById(R.id.textViewReturned);
=======
>>>>>>> Stashed changes
        textViewDate = findViewById(R.id.textViewDate);
        checkBoxReturned = findViewById(R.id.check_returned);

<<<<<<< Updated upstream
        textViewReturned.setText("");
=======
>>>>>>> Stashed changes
        textViewDate.setText("");
        textViewTransactionNumber.setText("");

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
        fStore.collectionGroup("Borrow")
                .whereArrayContains("items", "extn-01")
                //.orderBy("borrowedDate", Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
<<<<<<< Updated upstream
                                Log.d(TAG, task.getResult().toString());
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                  Log.d(TAG, document.getId() + " => " + document.getData());
=======
                                if (task.getResult() == null){ Log.d(TAG, "NULL QUERY");}
                                if (task.getResult() != null){ Log.d(TAG, "QUERY HAS SOMETHING     " + task.getResult().size());}

                                for (QueryDocumentSnapshot documents : task.getResult()) {
                                  Log.d(TAG, documents.getId() + " => " + documents.getData());
>>>>>>> Stashed changes

                                  UserBorrow userBorrow = documents.toObject(UserBorrow.class);
                                  mUserBorrow.add(userBorrow);

                                  if(userBorrow != null) { Log.d(TAG, "userBorrow has something!");}
                                  if(userBorrow == null) { Log.d(TAG, "userBorrow has something!");}


<<<<<<< Updated upstream
=======

                                  String docID = documents.getId();

>>>>>>> Stashed changes
                                  Timestamp borrowedDate = userBorrow.getBorrowedDate();
                                    Date bDate = borrowedDate.toDate();
                                    String borrowedDateString = bDate.toString();

                                  boolean returned = userBorrow.isReturned();

<<<<<<< Updated upstream


                                  textViewReturned.setText(returnedString);
=======
>>>>>>> Stashed changes
                                  textViewDate.setText(borrowedDateString);
                                  textViewTransactionNumber.setText(docID);

                                  checkBoxReturned.setChecked(returned);


                               }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }

                    }
                });
    }



}
