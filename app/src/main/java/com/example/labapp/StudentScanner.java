package com.example.labapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;

import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.Result;

import java.sql.Array;
import java.util.ArrayList;

public class StudentScanner extends AppCompatActivity {
    CodeScanner codeScanner;
    CodeScannerView scannView;
    TextView resultData, itemsArray;
    Button button_save, button_submit;


    ArrayList<String> itemsAdd = new ArrayList<>();
    String item_values1, item_values2, item_values3, item_values4, item_values5;




    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    CollectionReference itemsRef = db.collection("Inventory");
    CollectionReference usersRef = db.collection("users");
    CollectionReference borrowRef = db.collection("Borrow");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_scanner);

        mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid().toString();

        Query userDocs = usersRef.whereEqualTo("userID", userID);

        scannView = findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this, scannView);
        resultData = findViewById(R.id.resultsOfQr);
        button_save = findViewById(R.id.button_save);
        button_submit = findViewById(R.id.button_submit);
        itemsArray = findViewById(R.id.itemsArray);

        button_submit = findViewById(R.id.button_submit);


        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultData.setText(result.getText());
                    }
                });
            }
        });

        borrowItems();

        scannView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScanner.startPreview();
            }
        });


        userDocs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for( QueryDocumentSnapshot document: queryDocumentSnapshots) {

                    //get current user's student number

                    User user = document.toObject(User.class);
                    final String currentUserID = user.getStudentNumber();

                    button_submit.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            for (int i = 0; i < itemsAdd.size(); i++) {
                                itemsRef.document("Items").update("tags", FieldValue.arrayRemove(itemsAdd.get(i)));
                                usersRef.document(currentUserID).collection("Borrow")
                                        .document("1-" + currentUserID)
                                        .update("items", FieldValue.arrayUnion(itemsAdd.get(i)));
                            }
                        }

                    });


                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    //this method let the app gets the items borrowed
    //directly add and remove items to and from certain fields in the database
    public void borrowItems() {


        button_save.setOnClickListener(new View.OnClickListener() {

            int i = 0;

            @Override
            public void onClick(View v) {
                i++;
                if (i <= 5) {
                    switch (i) {
                        case 1:
                            item_values1 = (String) resultData.getText();
                            itemsAdd.add(0, item_values1);
                            break;
                        case 2:
                            item_values2 = (String) resultData.getText();
                            itemsAdd.add(0, item_values2);
                            break;
                        case 3:
                            item_values3 = (String) resultData.getText();
                            itemsAdd.add(0, item_values3);
                            break;
                        case 4:
                            item_values4 = (String) resultData.getText();
                            itemsAdd.add(3, item_values4);
                            break;
                        case 5:
                            item_values5 = (String) resultData.getText();
                            itemsAdd.add(3, item_values5);
                            break;
                    }
                } else {
                    i = 5;
                    openDialog();

                }

            }

            private void openDialog() {
                ExceedItem exceedItem = new ExceedItem();
                exceedItem.show(getSupportFragmentManager(), "Input Exceeds");
            }
        });

    }


}
