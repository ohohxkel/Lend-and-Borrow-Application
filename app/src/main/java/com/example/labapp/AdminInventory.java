package com.example.labapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class AdminInventory extends AppCompatActivity {
    LinearLayout layout;
    FloatingActionButton fab_category, fab_item;
    String category_text_input, item_text_input;
    Dialog qr_generate;
    ImageView qr_image;
    Button done;
    TextView new_item;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inventory);

        layout=findViewById(R.id.layout);


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

                        TextView category_name = new TextView(AdminInventory.this);
                        category_name.setText(category_text_input);
                        category_name.setAllCaps(true);
                        category_name.setTextColor(getResources().getColor(android.R.color.black));
                        addCategory(category_name, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
                        //generate new qr for new item

                        TextView item_name = new TextView(AdminInventory.this);
                        item_name.setText(item_text_input);
                        item_name.setTextColor(getResources().getColor(android.R.color.darker_gray));
                        addItem(item_name, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


                        qrGenerate();
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
    public void addCategory(TextView category_name, int width, int height) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(100,5, 0,0);


        category_name.setLayoutParams(layoutParams);
        layout.addView(category_name);
    }
    public void addItem(TextView item_name, int width, int height) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(100,5, 0,0);


        item_name.setLayoutParams(layoutParams);
        layout.addView(item_name);
    }

    public void qrGenerate() {

        qr_generate = new Dialog(AdminInventory.this);
        qr_generate.setContentView(R.layout.customdialog);
        qr_generate.setTitle("Generated QR Code");

        done = (Button)qr_generate.findViewById(R.id.done);
        new_item = (TextView)qr_generate.findViewById(R.id.new_item);
        qr_image = (ImageView)qr_generate.findViewById(R.id.qrcode_place_holder);

        new_item.setText(item_text_input);
        String text = new_item.getText().toString();

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 700, 700);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qr_image.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        done.setEnabled(true);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qr_generate.cancel();
            }
        });
        qr_generate.show();

    }


}
