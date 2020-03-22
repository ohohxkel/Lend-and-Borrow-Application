package com.example.labapp;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.camera.CenterCropStrategy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AdminInventory extends AppCompatActivity {
    LinearLayout layout, item_name;
    FloatingActionButton fab_category, fab_item;
    String category_text_input, item_text_input;
    Dialog qr_generate;
    ImageView qr_image;
    Button done;
    TextView new_item;

    FirebaseFirestore fStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inventory);

        layout=findViewById(R.id.layout);

        fab_item=findViewById(R.id.fab_item);
        fab_item.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){

                AlertDialog.Builder enterExistingCategory = new AlertDialog.Builder(AdminInventory.this);
                enterExistingCategory.setTitle("Enter item to add");

                final EditText existing_category = new EditText(AdminInventory.this);
                existing_category.setInputType(InputType.TYPE_CLASS_TEXT);
                enterExistingCategory.setView(existing_category);

                enterExistingCategory.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String existing_category_input = existing_category.getText().toString();

                        AlertDialog.Builder enterItemName = new AlertDialog.Builder(AdminInventory.this);
                        enterItemName.setTitle("Enter item to add");

                        final EditText item_text = new EditText(AdminInventory.this);
                        item_text.setInputType(InputType.TYPE_CLASS_TEXT);
                        enterItemName.setView(item_text);

                        //!!!
                        addItemOnDatabase(item_text_input, existing_category_input);

                        enterItemName.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int length = item_text.length();

                                //Display invalid dialog if string length == 0
                                if (length==0) {
                                    AlertDialog.Builder nullItem = new AlertDialog.Builder(AdminInventory.this);
                                    nullItem.setTitle("Invalid");

                                    nullItem.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which) {
                                            dialogInterface.cancel();
                                        }
                                    });
                                    nullItem.show();
                                }
                                else {
                                    item_text_input = item_text.getText().toString();

                                    item_name = new LinearLayout(AdminInventory.this);
                                    item_name.setBackgroundResource(R.drawable.rectangle_extended);
                                    addItemContainer(item_name, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    item_name.setVisibility(View.GONE);

                                    TextView item_name_input = new TextView(AdminInventory.this);
                                    item_name_input.setText(item_text_input);
                                    addItem(item_name_input, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                                    //generate new qr for new item
                                    qrGenerate();

                                }
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
                enterExistingCategory.show();

            }
        });

        fab_category=findViewById(R.id.fab_category);
        fab_category.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                AlertDialog.Builder enterCategoryName = new AlertDialog.Builder(AdminInventory.this);
                enterCategoryName.setTitle("Enter item category");

                final EditText category_text = new EditText(AdminInventory.this);
                category_text.setInputType(InputType.TYPE_CLASS_TEXT);
                enterCategoryName.setView(category_text);
                
                //!!!
                addCategoryOnDatabase(category_text_input);

                enterCategoryName.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        category_text_input=category_text.getText().toString();
                        Toast.makeText(AdminInventory.this, ""+category_text_input+" has been added", Toast.LENGTH_LONG).show();

                        Button category_name = new Button(AdminInventory.this);
                        category_name.setText(category_text_input);
                        category_name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        category_name.setAllCaps(true);
                        category_name.setGravity(Gravity.CENTER);
                        category_name.setBackgroundResource(R.drawable.rectangle);
                        category_name.setTextColor(getResources().getColor(android.R.color.black));
                        addCategory(category_name, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


                        category_name.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (item_name.getVisibility()==View.GONE) {
                                    item_name.setVisibility(View.VISIBLE);
                                }
                                else {
                                    item_name.setVisibility(View.GONE);
                                }

                            }
                        });

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
    }
    public void addCategory(TextView category_name, int width, int height) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(100,5, 0,0);


        category_name.setLayoutParams(layoutParams);
        layout.addView(category_name);
    }
    public void addItemContainer(LinearLayout item_name, int width, int height) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(100,-50, 0,0);


        item_name.setLayoutParams(layoutParams);
        layout.addView(item_name);
    }
    public void addItem(TextView item_name_input, int width, int height) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(500,30, 0,0);


        item_name_input.setLayoutParams(layoutParams);
        item_name.addView(item_name_input);
    }

    //Method that generates QR and show it in a dialog
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
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500);
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
                Toast.makeText(AdminInventory.this, "" + item_text_input + " has been added", Toast.LENGTH_LONG).show();
            }
        });
        qr_generate.show();

    }

    //Call method to add the item on database, considering the parameters needed

    public void addItemOnDatabase (String itemText, String categoryText){
        //initialization
        String itemTextDb = (String) itemText;
        String categoryTextDb = (String) categoryText;

        DocumentReference inventoryItems = fStore.collection("Inventory").document("Items");

        inventoryItems.update(categoryTextDb, FieldValue.arrayUnion(itemTextDb))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AdminInventory.this,"Successful", Toast.LENGTH_LONG);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminInventory.this,"Failure: \n" + e.toString(), Toast.LENGTH_LONG);

                    }
                });
    }

    public void addCategoryOnDatabase ( String categoryText){
        String categoryTextDb = (String) categoryText;
 //       boolean showCategoryDB = (boolean) showCategory;

        CollectionReference inventory = fStore.collection("Inventory");
        Map<String, Object> data = new HashMap<>();
        data.put(categoryTextDb, Arrays.asList(null));

        inventory.document("Items").set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(AdminInventory.this,"Successful", Toast.LENGTH_LONG);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminInventory.this,"Failure: \n" + e.toString(), Toast.LENGTH_LONG);

                    }
                });
    }


}
