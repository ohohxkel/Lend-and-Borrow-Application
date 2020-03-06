package com.example.labapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class AdminGenerate extends AppCompatActivity {
    EditText qr_value;
    Button button_generate, button_scan;
    ImageView qr_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_generate);

        qr_value = findViewById(R.id.qr_Input);
        button_generate = findViewById(R.id.button_generate);
        button_scan = findViewById(R.id.button_scan);
        qr_image = findViewById(R.id.qrcode_place_holder);

        button_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = qr_value.getText().toString();
                QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT,10);
            }
        });
    }
}
