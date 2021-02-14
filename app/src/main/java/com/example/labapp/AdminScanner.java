package com.example.labapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminScanner extends AppCompatActivity implements EditDialog.EditDialogListener{
    CodeScanner codeScanner;
    CodeScannerView scannView;
    TextView resultData, textViewTransaction, textViewStudentNum;
    Button button_admin_save, button_done;

    ArrayList<String> itemsAdd = new ArrayList<>();
    String item_values1, item_values2, item_values3, item_values4, item_values5;


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_scanner);
        scannView = findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this, scannView);
        resultData = findViewById(R.id.resultsOfQr);
        button_admin_save = findViewById(R.id.button_admin_save);
        button_done = findViewById(R.id.button_done);
        textViewTransaction = findViewById(R.id.transaction);
        textViewStudentNum = findViewById(R.id.studentNum);

        //num = (String) textViewStudentNum.getText();
        //trans = (String) textViewTransaction.getText();


        borrowItems();
        //returnItems();
        openDialog();

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

        scannView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScanner.startPreview();
            }
        });

        //Collections.sort(itemsAdd);


    }
    public void openDialog() {
        EditDialog editDialog = new EditDialog();
        editDialog.show(getSupportFragmentManager(), "edit dialog");
    }

    @Override
    public void applyText(String transaction, String studentNum) {
        textViewTransaction.setText(transaction);
        textViewStudentNum.setText(studentNum);

        String num = textViewStudentNum.getText().toString();
        String trans = textViewTransaction.getText().toString();

        final DocumentReference docRef = db.collection("users").document(num).collection("Borrow")
                .document(trans + "-" + num);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();

                    UserBorrow userBorrow = document.toObject(UserBorrow.class);
                    final ArrayList<String> items = userBorrow.getItems();

                    button_done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (itemsAdd.equals(items)) {

                                docRef.update("returned", true)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(AdminScanner.this, "Items are returned", Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        }
                    });



                } else {
                    Toast.makeText(AdminScanner.this, "Not found!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }


    public void borrowItems() {

        button_admin_save.setOnClickListener(new View.OnClickListener() {

            int i = 0;

            @Override
            public void onClick(View v) {
                i++;
                switch (i) {
                    case 1:
                        item_values1 = (String) resultData.getText();
                        itemsAdd.add(0, item_values1);
                        break;
                    case 2:
                        item_values2 = (String) resultData.getText();
                        itemsAdd.add(1, item_values2);
                        break;
                    case 3:
                        item_values3 = (String) resultData.getText();
                        itemsAdd.add(2, item_values3);
                        break;
                    case 4:
                        item_values4 = (String) resultData.getText();
                        itemsAdd.add(3, item_values4);
                        break;
                    case 5:
                        item_values5 = (String) resultData.getText();
                        itemsAdd.add(4, item_values5);
                        break;
                    default:
                }

            }

        });

        Collections.sort(itemsAdd);

    }

}
