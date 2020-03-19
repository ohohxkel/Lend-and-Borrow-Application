package com.example.labapp;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;

public class StudentReceipt extends AppCompatActivity {
    Button button_back, button_sample;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_receipt);



        button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentReceipt.this, StudentHome.class);
                startActivity(intent);
            }
        });
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
    }

    public void addItem(TextView returned_item, int width, int height) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(110,3, 0,0);


        returned_item.setLayoutParams(layoutParams);
        layout.addView(returned_item);
    }


}
