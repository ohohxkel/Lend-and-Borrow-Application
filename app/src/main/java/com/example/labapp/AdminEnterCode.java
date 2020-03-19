package com.example.labapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminEnterCode extends AppCompatActivity {
    Button button_enter;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button button0;
    Button button_delete;

    EditText editText_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_enter_passcode);
        editText_code=(EditText)findViewById(R.id.editText_code) ;

        button_enter = findViewById(R.id.button_enter);
        button1 = findViewById(R.id.button_1);
        button2 = findViewById(R.id.button_2);
        button3 = findViewById(R.id.button_3);
        button4 = findViewById(R.id.button_4);
        button5 = findViewById(R.id.button_5);
        button6 = findViewById(R.id.button_6);
        button7 = findViewById(R.id.button_7);
        button8 = findViewById(R.id.button_8);
        button9 = findViewById(R.id.button_9);
        button0 = findViewById(R.id.button_0);
        button_delete = findViewById(R.id.button_delete);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_code.setText(editText_code.getText().insert(editText_code.getText().length(),"1"));

            }

        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_code.setText(editText_code.getText().insert(editText_code.getText().length(),"2"));
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_code.setText(editText_code.getText().insert(editText_code.getText().length(),"3"));
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_code.setText(editText_code.getText().insert(editText_code.getText().length(),"4"));
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_code.setText(editText_code.getText().insert(editText_code.getText().length(),"5"));
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_code.setText(editText_code.getText().insert(editText_code.getText().length(),"6"));
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_code.setText(editText_code.getText().insert(editText_code.getText().length(),"7"));
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_code.setText(editText_code.getText().insert(editText_code.getText().length(),"8"));
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_code.setText(editText_code.getText().insert(editText_code.getText().length(),"9"));
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_code.setText(editText_code.getText().insert(editText_code.getText().length(),"0"));
            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_code.setText(editText_code.getText().delete(editText_code.getText().length()-1, editText_code.getText().length()));
            }
        });


        button_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(String.valueOf(editText_code.getText()))==1)
                {
                    Intent intent = new Intent(AdminEnterCode.this, AdminHome.class);
                    startActivity(intent);
                }
            }
        });






    }
}
