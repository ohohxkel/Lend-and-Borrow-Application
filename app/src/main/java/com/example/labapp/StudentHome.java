package com.example.labapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class StudentHome extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private TextView textViewName, textViewStudentNumber, textViewYearAndSection;
    private ArrayList<User> mUsers = new ArrayList<>();
    Button button_logout,button_student_scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        fStore = FirebaseFirestore.getInstance();
        textViewName = (TextView) findViewById(R.id.textView_name) ;
        textViewStudentNumber = (TextView) findViewById(R.id.textView_studentnumber);
        textViewYearAndSection = (TextView) findViewById(R.id.textView_yearandsection);
        mAuth = FirebaseAuth.getInstance();

        textViewName.setText(" ");
        textViewStudentNumber.setText(" ");
        textViewYearAndSection.setText(" ");



        String userID = mAuth.getCurrentUser().getUid().toString();
        CollectionReference users = fStore.collection("users");
        Query userss = users.whereEqualTo("userID", userID);

         userss.get()
                 .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                         if(task.isSuccessful()){

                             for( QueryDocumentSnapshot document: task.getResult()){
                                 User user = document.toObject(User.class);
                                 mUsers.add(user);

                                 String email = user.getEmail();
                                 String name = user.getName();
                                 String yearAndSection = user.getYearAndSection();
                                 String studentNumberString = user.getStudentNumber();
                                 Toast.makeText(StudentHome.this,"Successful loading" + name, Toast.LENGTH_SHORT);


                                textViewName.setText(name);
                                textViewYearAndSection.setText("BSCOE " + yearAndSection);
                                textViewStudentNumber.setText(studentNumberString);

                             }

                         } else {

                             Toast.makeText(StudentHome.this,"Unsuccessful loading", Toast.LENGTH_SHORT);
                         }
                     }
                 });

        button_logout= findViewById(R.id.button_logout);
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHome.this, LogoutConfirmationStudents.class);
                startActivity(intent);
            }
        });

        button_student_scanner = findViewById(R.id.button_student_scanner);
        button_student_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHome.this, StudentScanner.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed(){

    }
}
