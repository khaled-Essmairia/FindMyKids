package com.example.firebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddChildActivity extends AppCompatActivity {

    // creating variables for our button, edit text,
    // firebase database, database reference, progress bar.
    private Button addchildBtn;
    private TextInputEditText childNameEdt, childDescEdt, childdesc2Edt, bestSuitedEdt, childImgEdt, childLinkEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    private String childID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        // initializing all our variables.
        addchildBtn = findViewById(R.id.idBtnAddchild);
        childNameEdt = findViewById(R.id.idEdtchildName);
        childDescEdt = findViewById(R.id.idEdtchildDescription);
        childdesc2Edt = findViewById(R.id.idEdtchilddesc2);
        bestSuitedEdt = findViewById(R.id.idEdtSuitedFor);
        childImgEdt = findViewById(R.id.idEdtchildImageLink);
        childLinkEdt = findViewById(R.id.idEdtchildLink);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line creating our database reference.
        databaseReference = firebaseDatabase.getReference("childs");
        // adding click listener for our add child button.
        addchildBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                // getting data from our edit text.
                String childName = childNameEdt.getText().toString();
                String childDesc = childDescEdt.getText().toString();
                String childdesc2 = childdesc2Edt.getText().toString();
                String bestSuited = bestSuitedEdt.getText().toString();
                String childImg = childImgEdt.getText().toString();
                String childLink = childLinkEdt.getText().toString();
                childID = childName;
                // on below line we are passing all data to our modal class.
                ChildRVModal childRVModal = new ChildRVModal(childID, childName, childDesc, childdesc2, bestSuited, childImg, childLink);
                // on below line we are calling a add value event
                // to pass data to firebase database.
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // on below line we are setting data in our firebase database.
                        databaseReference.child(childID).setValue(childRVModal);
                        // displaying a toast message.
                        Toast.makeText(AddChildActivity.this, "child Added..", Toast.LENGTH_SHORT).show();
                        // starting a main activity.
                        startActivity(new Intent(AddChildActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on below line.
                        Toast.makeText(AddChildActivity.this, "Fail to add child..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
