package com.example.labapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminTransaction extends AppCompatActivity {

    Button add_button;
    LinearLayout layout;
    private ArrayList<UserBorrow> mUserBorrow = new ArrayList<>();
    //used for logging errors
    public static final String TAG = "AdminTransaction";

    private FirebaseFirestore fStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_transaction);

        add_button = findViewById(R.id.add_button);
        layout = findViewById(R.id.layout);
        fStore = FirebaseFirestore.getInstance();

        loadTransactions();
    }

    public void addText(TextView transaction_num, int width, int height) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(20, 10, 0, 10);

        transaction_num.setLayoutParams(layoutParams);
        layout.addView(transaction_num);
    }
    public void addText1(TextView transaction_yearsection, int width, int height) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(20, 10, 0, 0);

        transaction_yearsection.setLayoutParams(layoutParams);
        layout.addView(transaction_yearsection);
    }
    public void addText2(CheckBox transaction_status, int width, int height) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(5, 10, 0, 0);

        transaction_status.setLayoutParams(layoutParams);
        layout.addView(transaction_status);
    }
    public void addText3(Spinner transaction_items, int width, int height, List<String> items) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(20, 10, 0, 0);

        transaction_items.setLayoutParams(layoutParams);
        layout.addView(transaction_items);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_dropdown_layout,items);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        transaction_items.setAdapter(adapter);
    }
    public void addText4(TextView transaction_line, int width, int height) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(5, 5, 0, 20);

        transaction_line.setLayoutParams(layoutParams);
        layout.addView(transaction_line);
    }


    private void loadTransactions (){
        fStore.collectionGroup("Borrow")
                .orderBy("returned", Query.Direction.ASCENDING)
                .orderBy("borrowedDate", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() == null) {
                                Log.d(TAG, "QuerySnapshot is NULL");
                            }
                            if (task.getResult() != null) {
                                Log.d(TAG, "QuerySnapshot found " + task.getResult().size() + " documents");
                            }

                            for(QueryDocumentSnapshot documents : task.getResult()){
                                Log.d(TAG, documents.getId() + " => " + documents.getData());

                                UserBorrow userBorrow = documents.toObject(UserBorrow.class);
                                mUserBorrow.add(userBorrow);


                                //getting document fields
                                String docID = documents.getId();
                                Timestamp borrowedDate = userBorrow.getBorrowedDate();
                                Date bDate = borrowedDate.toDate();
                                String borrowedDateString = bDate.toString();
                                List<String> items = userBorrow.getItems();
                                boolean returned = userBorrow.isReturned();



                                //setting textViews from UserBorrow class
                                TextView transaction_num = new TextView(AdminTransaction.this);
                                transaction_num.setText(docID);
                                TextView transaction_yearsection = new TextView(AdminTransaction.this);
                                transaction_yearsection.setText(borrowedDateString);
                                CheckBox transaction_status = new CheckBox(AdminTransaction.this);
                                transaction_status.setChecked(returned);
                                transaction_status.setClickable(false);
                                transaction_status.setText("Returned");
                                Spinner transaction_items = new Spinner(AdminTransaction.this);
                                transaction_items.setTag("Items");
                                TextView transaction_line = new TextView(AdminTransaction.this);
                                transaction_line.setText("______________________________________________");

                                addText(transaction_num, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                addText1(transaction_yearsection, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                addText2(transaction_status, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                addText3(transaction_items, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, items);
                                addText4(transaction_line, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                            }

                        }
                    }
                });


    }

}
