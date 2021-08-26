package com.shihabcse.cbdcbloodfamily.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shihabcse.cbdcbloodfamily.R;
import com.shihabcse.cbdcbloodfamily.adapters.BloodRequestAdapter;
import com.shihabcse.cbdcbloodfamily.models.BloodRequestPatient;

import java.util.ArrayList;
import java.util.List;

public class SearchBloodRequestActivity extends AppCompatActivity {

    RecyclerView donorRecyclerView;
    BloodRequestAdapter bloodRequestAdapter;

    //private ProgressDialog progressDialog;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<BloodRequestPatient> bloodRequestPatientList;

    EditText editTextSearchInput;

    @Override
    protected void onStart() {
        // progress Dialog
        //progressDialog.setTitle("Loading Data");
        //progressDialog.setMessage("Please wait ...");
        //progressDialog.setCanceledOnTouchOutside(true);
        //progressDialog.show();

        super.onStart();

        // Get List Posts from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                bloodRequestPatientList = new ArrayList<>();

                for (DataSnapshot postSnap : snapshot.getChildren()) {

                    BloodRequestPatient bloodRequestPatient = postSnap.getValue(BloodRequestPatient.class);
                    bloodRequestPatientList.add(bloodRequestPatient);

                }

                //progressDialog.dismiss();

                bloodRequestAdapter = new BloodRequestAdapter(SearchBloodRequestActivity.this, bloodRequestPatientList);
                donorRecyclerView.setAdapter(bloodRequestAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Check your internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_blood_request);

        editTextSearchInput = findViewById(R.id.editText_search_input);

        donorRecyclerView = findViewById(R.id.recyclerView_item_search);
        donorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        donorRecyclerView.setHasFixedSize(true);

        //progressDialog = new ProgressDialog(getApplicationContext());

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("blood_request");

        editTextSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bloodRequestAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}