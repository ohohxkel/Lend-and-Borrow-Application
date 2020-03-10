package com.example.labapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class StudentScanner extends AppCompatActivity {
    CodeScanner codeScanner;
    CodeScannerView scannView;
    TextView resultData;
    Button button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_scanner);

        scannView = findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner( this, scannView);
        resultData = findViewById(R.id.resultsOfQr);
        button_save = findViewById(R.id.button_save);

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

        itemsName();

        scannView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScanner.startPreview();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    public void itemsName(){
        button_save.setOnClickListener(new View.OnClickListener() {
            int i = 0;
            String item_values1, item_values2, item_values3, item_values4, item_values5;

            @Override
            public void onClick(View v) {
                i++;
                if (i <= 5) {
                    switch (i) {
                        case 1: item_values1 = (String) resultData.getText();
                        break;
                        case 2: item_values2 = (String) resultData.getText();
                        break;
                        case 3: item_values3 = (String) resultData.getText();
                        break;
                        case 4: item_values4 = (String) resultData.getText();
                        break;
                        case 5: item_values5 = (String) resultData.getText();
                        break;
                    }
                }
                else {
                    i = 5;
                    openDialog();

                }
            }
            public void openDialog() {
                ExceedItem exceedItem = new ExceedItem();
                exceedItem.show(getSupportFragmentManager(), "Input Exceeds");
            }
        });
    }
}

