package com.example.labapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class AdminTransaction extends AppCompatActivity {

    Button add_button;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_transaction);

        add_button = findViewById(R.id.add_button);
        layout = findViewById(R.id.layout);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button transaction_button = new Button(AdminTransaction.this);
                transaction_button.setBackgroundResource(R.drawable.button_transactions);

                addView(transaction_button, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        });
    }

    public void addView(Button transaction_button, int width, int height) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(0, 10, 0, 10);

        transaction_button.setLayoutParams(layoutParams);
        layout.addView(transaction_button);
    }
}
