package com.example.labapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

public class AdminInventory extends AppCompatActivity {

    FloatingActionButton fab_category, fab_item;
    String category_text_input, item_text_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inventory);

        fab_category=findViewById(R.id.fab_category);
        fab_category.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                AlertDialog.Builder enterCategoryName = new AlertDialog.Builder(AdminInventory.this);
                enterCategoryName.setTitle("Enter item category");

                final EditText category_text = new EditText(AdminInventory.this);
                category_text.setInputType(InputType.TYPE_CLASS_TEXT);
                enterCategoryName.setView(category_text);

                enterCategoryName.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        category_text_input=category_text.getText().toString();
                        Toast.makeText(AdminInventory.this, ""+category_text_input+" has been added", Toast.LENGTH_LONG).show();
                    }
                });

                enterCategoryName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                enterCategoryName.show();
            }
        });
        fab_item=findViewById(R.id.fab_item);
        fab_item.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                AlertDialog.Builder enterItemName = new AlertDialog.Builder(AdminInventory.this);
                enterItemName.setTitle("Enter item to add");

                final EditText item_text = new EditText(AdminInventory.this);
                item_text.setInputType(InputType.TYPE_CLASS_TEXT);
                enterItemName.setView(item_text);

                enterItemName.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        item_text_input=item_text.getText().toString();
                        Toast.makeText(AdminInventory.this, ""+item_text_input+" has been added", Toast.LENGTH_LONG).show();
                    }
                });

                enterItemName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                enterItemName.show();
            }
        });
    }
}
