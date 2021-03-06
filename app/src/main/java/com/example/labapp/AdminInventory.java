package com.example.labapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.LinkAddress;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminInventory extends AppCompatActivity {
    LinearLayout layout, item_name;
    FloatingActionButton fab_item;
    String category_text_input, item_text_input;
    Dialog qr_generate;
    ImageView qr_image;
    Button done;
    TextView new_item;

    FirebaseFirestore fStore;

    public static final String TAG="AdminInventory";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inventory);

        this.fStore = FirebaseFirestore.getInstance();

        layout=findViewById(R.id.layout);

        fab_item=findViewById(R.id.fab_item);
        fab_item.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){

                AlertDialog.Builder enterCategoryName = new AlertDialog.Builder(AdminInventory.this);
                enterCategoryName.setTitle("Enter category name");

                final EditText category_text = new EditText(AdminInventory.this);
                category_text.setInputType(InputType.TYPE_CLASS_TEXT);
                enterCategoryName.setView(category_text);

                enterCategoryName.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        category_text_input=category_text.getText().toString();



                        AlertDialog.Builder enterItemName = new AlertDialog.Builder(AdminInventory.this);
                        enterItemName.setTitle("Enter item to add");

                        final EditText item_text = new EditText(AdminInventory.this);
                        item_text.setInputType(InputType.TYPE_CLASS_TEXT);
                        enterItemName.setView(item_text);

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




                                    //generate new qr for new item
                                    qrGenerate();



                                    if (item_text_input != null){
                                        Log.d(TAG, item_text_input);
                                        if (category_text_input != null){
                                            Log.d(TAG, category_text_input);
                                            //!!!
                                            addItemOnDatabase(item_text_input, category_text_input);
                                        }
                                        else { Log.d(TAG, "NULL CATEGORYTEXT");}
                                    }
                                    else { 
                                        Log.d(TAG, "NULL ITEMTEXT");
                                    }

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

                enterCategoryName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                enterCategoryName.show();

            }
        });

        pullDataFromDatabase();

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
        layoutParams.setMargins(350,30, 0,0);


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
                        Toast.makeText(AdminInventory.this,"Added successfully", Toast.LENGTH_LONG);
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

    public void addItemTextViews (String itemText, String categoryText){
        String categoryTextt = (String) categoryText;

        String item_text_input = (String) itemText;

        item_name = new LinearLayout(AdminInventory.this);
        item_name.setBackgroundResource(R.drawable.rectangle);
        addItemContainer(item_name, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        TextView item_name_input = new TextView(AdminInventory.this);
        item_name_input.setText(item_text_input);

        addItem(item_name_input, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    public void addCategoryTextViews (final String categoryText, ArrayList<String> listItems){
        final ArrayList<String> itemsList = (ArrayList<String>) listItems;
        final String category_text_input = (String) categoryText;

        final Button category_name = new Button(AdminInventory.this);
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
                //checker if what button is being clicked, look at logcat euge while running on phone

            }
        });

    }


    public void pullDataFromDatabase (){
        final DocumentReference docRef= fStore.collection("Inventory").document("Items");

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot != null) {
                    Map<String, Object> mapDoc = documentSnapshot.getData();
                    List<String> docList = new ArrayList<>();


                    for(Map.Entry<String, Object> entry : mapDoc.entrySet()){
                        docList.add(entry.getKey().toString());
                    }

                    for (String category: docList){
                        //calls method for producing category boxes

                        ArrayList<String> list = (ArrayList<String>) documentSnapshot.get(category);
                        Log.d(TAG, "key " + category);


                        addCategoryTextViews(category, list);


                        for (String items: list){
                            //calls method for assigning text on items textViews

                            Log.d(TAG,"items " + items);

                            addItemTextViews(items, category);
                        }
                    }
                }
                if( documentSnapshot == null){
                    Toast.makeText(AdminInventory.this,"You have no existing items, add some by clicking the add button", Toast.LENGTH_LONG);
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminInventory.this,"You have no existing items, add some by clicking the add button", Toast.LENGTH_LONG);
                        Log.e(TAG, "ERROR:" + e);
                    }
                });
    }


}
