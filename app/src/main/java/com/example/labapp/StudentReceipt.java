package com.example.labapp;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class StudentReceipt extends AppCompatActivity {
    Button button_back, button_sample;
    LinearLayout layout;

    TextView textViewTransactionNumber, textViewDate, textViewName, textViewYearAndSection, textViewStudentNumber;
    String studNum;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    public ArrayList<User> mUsers = new ArrayList<>();
    public ArrayList<UserBorrow> mTransactions = new ArrayList<>();

    private ProgressDialog progressDialog;

    public static final String TAG = "StudentReceipt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_receipt);



        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        textViewTransactionNumber = (TextView) findViewById(R.id.text_transaction_number) ;
        textViewDate = (TextView) findViewById(R.id.text_date) ;

        textViewName = (TextView) findViewById(R.id.textView_name);
        textViewYearAndSection = (TextView) findViewById(R.id.textView_yearandsection);
        textViewStudentNumber = (TextView) findViewById(R.id.textView_studentnumber);


        textViewTransactionNumber.setText("");
        textViewDate.setText("");


        progressDialog = new ProgressDialog(this);


        // just to see if its duplicating
        button_sample = findViewById(R.id.button_sample);
        button_sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layout = findViewById(R.id.layout);

                TextView returned_item = new TextView(StudentReceipt.this);
                //put item variable here
                returned_item.setText("Iteeeemsss");

                addItem(returned_item, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            }
        });

        button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentReceipt.this, StudentHome.class);
                startActivity(intent);
            }
        });

        showReceipt();
    }

    public void showReceipt(){
        final FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();

        CollectionReference users = fStore.collection("users");
        Query userss = users.whereEqualTo("userID", userID);


        progressDialog.setMessage("Loading e-receipt");
        progressDialog.show();
        userss.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                mUsers.add(user);

                                String email = user.getEmail();
                                String name = user.getName();
                                String yearAndSection = user.getYearAndSection();
                                String studentNumberString = user.getStudentNumber();
                                Toast.makeText(StudentReceipt.this, "Successful loading" + name, Toast.LENGTH_SHORT);


                                textViewName.setText(name);
                                textViewYearAndSection.setText("BSCOE " + yearAndSection);
                                textViewStudentNumber.setText(studentNumberString);
                            }
                            String studentNumber = textViewStudentNumber.getText().toString();
                            DocumentReference docRef = fStore.collection("users").document(studentNumber);
                            Query usersss = docRef.collection("Borrow").whereEqualTo("returned", false)
                                    .orderBy("borrowedDate", Query.Direction.ASCENDING);

                            usersss.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot documents : task.getResult()) {
                                            UserBorrow userBorrow = documents.toObject(UserBorrow.class);
                                            mTransactions.add(userBorrow);

                                            String transactionNumber = documents.getId();
                                            Timestamp date = userBorrow.getBorrowedDate();
                                            String tDate = date.toDate().toString();
                                            List<String> items = userBorrow.getItems();
                                            Log.d(TAG, items.toString());
                                            for (String itemss : items) {


                                                layout = findViewById(R.id.layout);

                                                TextView returned_item = new TextView(StudentReceipt.this);
                                                //put item variable here
                                                returned_item.setText(itemss);

                                                addItem(returned_item, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                                            }

                                            textViewTransactionNumber.setText(transactionNumber);
                                            textViewDate.setText(tDate);


                                        }
                                    }
                                }
                            });

                            progressDialog.hide();
                        }
                    }
                });

    }

    public void addItem(TextView returned_item, int width, int height) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(110,3, 0,0);


        returned_item.setLayoutParams(layoutParams);
        layout.addView(returned_item);
    }


}
