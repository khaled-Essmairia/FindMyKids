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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditChildActivity extends AppCompatActivity {

    // creating variables for our edit text, firebase database,
    // database reference, child rv modal,progress bar.
    private TextInputEditText childNameEdt, childDescEdt, childdesc2Edt, bestSuitedEdt, childImgEdt, childLinkEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ChildRVModal childRVModal;
    private ProgressBar loadingPB;
    // creating a string for our child id.
    private String childID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_child);
        // initializing all our variables on below line.
        Button addchildBtn = findViewById(R.id.idBtnAddchild);
        childNameEdt = findViewById(R.id.idEdtchildName);
        childDescEdt = findViewById(R.id.idEdtchildDescription);
        childdesc2Edt = findViewById(R.id.idEdtchilddesc2);
        bestSuitedEdt = findViewById(R.id.idEdtSuitedFor);
        childImgEdt = findViewById(R.id.idEdtchildImageLink);
        childLinkEdt = findViewById(R.id.idEdtchildLink);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line we are getting our modal class on which we have passed.
        childRVModal = getIntent().getParcelableExtra("child");
        Button deletechildBtn = findViewById(R.id.idBtnDeletechild);

        if (childRVModal != null) {
            // on below line we are setting data to our edit text from our modal class.
            childNameEdt.setText(childRVModal.getchildName());
            childdesc2Edt.setText(childRVModal.getchilddesc2());
            bestSuitedEdt.setText(childRVModal.getBestSuitedFor());
            childImgEdt.setText(childRVModal.getchildImg());
            childLinkEdt.setText(childRVModal.getchildLink());
            childDescEdt.setText(childRVModal.getchildDescription());
            childID = childRVModal.getchildId();
        }

        // on below line we are initialing our database reference and we are adding a child as our child id.
        databaseReference = firebaseDatabase.getReference("childs").child(childID);
        // on below line we are adding click listener for our add child button.
        addchildBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are making our progress bar as visible.
                loadingPB.setVisibility(View.VISIBLE);
                // on below line we are getting data from our edit text.
                String childName = childNameEdt.getText().toString();
                String childDesc = childDescEdt.getText().toString();
                String childdesc2 = childdesc2Edt.getText().toString();
                String bestSuited = bestSuitedEdt.getText().toString();
                String childImg = childImgEdt.getText().toString();
                String childLink = childLinkEdt.getText().toString();
                // on below line we are creating a map for
                // passing a data using key and value pair.
                Map<String, Object> map = new HashMap<>();
                map.put("childName", childName);
                map.put("childDescription", childDesc);
                map.put("childdesc2", childdesc2);
                map.put("bestSuitedFor", bestSuited);
                map.put("childImg", childImg);
                map.put("childLink", childLink);
                map.put("childId", childID);

                // on below line we are calling a database reference on
                // add value event listener and on data change method
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // making progress bar visibility as gone.
                        loadingPB.setVisibility(View.GONE);
                        // adding a map to our database.
                        databaseReference.updateChildren(map);
                        // on below line we are displaying a toast message.
                        Toast.makeText(EditChildActivity.this, "child Updated..", Toast.LENGTH_SHORT).show();
                        // opening a new activity after updating our coarse.
                        startActivity(new Intent(EditChildActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on toast.
                        Toast.makeText(EditChildActivity.this, "Fail to update child..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // adding a click listener for our delete child button.
        deletechildBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to delete a child.
                deletechild();
            }
        });

    }

    private void deletechild() {
        // on below line calling a method to delete the child.
        databaseReference.removeValue();
        // displaying a toast message on below line.
        Toast.makeText(this, "child Deleted..", Toast.LENGTH_SHORT).show();
        // opening a main activity on below line.
        startActivity(new Intent(EditChildActivity.this, MainActivity.class));
    }
}
