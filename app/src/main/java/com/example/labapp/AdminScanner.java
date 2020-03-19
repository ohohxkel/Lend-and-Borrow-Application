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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.Result;

import java.util.ArrayList;

public class AdminScanner extends AppCompatActivity {
    CodeScanner codeScanner;
    CodeScannerView scannView;
    TextView resultData;
    Button button_admin_save, button_done;

    ArrayList<String> itemsAdd = new ArrayList<>();
    String item_values1, item_values2, item_values3, item_values4, item_values5;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference itemsRef = db.collection("Inventory");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_scanner);
        scannView = findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this, scannView);
        resultData = findViewById(R.id.resultsOfQr);
        button_admin_save = findViewById(R.id.button_admin_save);
        button_done = findViewById(R.id.button_submit);

        borrowItems();

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

        button_done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (int i = 0; i < itemsAdd.size(); i++) {
                    itemsRef.document("Items").update("tags", FieldValue.arrayUnion(itemsAdd.get(i)));
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
                    default:
                }


            }

        });

    }

}
