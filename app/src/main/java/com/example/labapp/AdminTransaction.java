package com.example.labapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class AdminTransaction extends AppCompatActivity {

    Button add_button;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_transaction);

        add_button = findViewById(R.id.add_button);
        layout = findViewById(R.id.layout);

        add_button.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {

                TextView transaction_num = new TextView(AdminTransaction.this);
                transaction_num.setText("Transaction Number");
                TextView transaction_yearsection = new TextView(AdminTransaction.this);
                transaction_yearsection.setText("Year & Section");
                CheckBox transaction_status = new CheckBox(AdminTransaction.this);
                transaction_status.setText("Returned");
                Spinner transaction_items = new Spinner(AdminTransaction.this);
                transaction_items.setTag("Items");
                TextView transaction_line = new TextView(AdminTransaction.this);
                transaction_line.setText("______________________________________________");

                addText(transaction_num, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                addText1(transaction_yearsection, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                addText2(transaction_status, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                addText3(transaction_items, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                addText4(transaction_line, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            }
        });
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
    public void addText3(Spinner transaction_items, int width, int height) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(20, 10, 0, 0);

        transaction_items.setLayoutParams(layoutParams);
        layout.addView(transaction_items);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.itemsAdd, R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        transaction_items.setAdapter(adapter);
    }
    public void addText4(TextView transaction_line, int width, int height) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(5, 5, 0, 20);

        transaction_line.setLayoutParams(layoutParams);
        layout.addView(transaction_line);
    }

}
