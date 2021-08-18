package com.shihabcse.cbdcbloodfamily.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.shihabcse.cbdcbloodfamily.adapters.CoordinatorAdapter;
import com.shihabcse.cbdcbloodfamily.models.BloodRequestPatient;
import com.shihabcse.cbdcbloodfamily.models.Coordinator;

import java.util.ArrayList;
import java.util.List;

public class SearchCoordinatorActivity extends AppCompatActivity {

    RecyclerView coordinatorRecyclerView;
    CoordinatorAdapter coordinatorAdapter;
    //private ProgressDialog progressDialog;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<Coordinator> coordinatorList;

    EditText editTextSearchInputCoordinator;

    @Override
    protected void onStart() {
        super.onStart();

        // Get List Posts from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                coordinatorList = new ArrayList<>();

                for (DataSnapshot postSnap : snapshot.getChildren()) {

                    Coordinator coordinator = postSnap.getValue(Coordinator.class);
                    coordinatorList.add(coordinator);

                }

                //progressDialog.dismiss();

                coordinatorAdapter = new CoordinatorAdapter(getApplicationContext(), coordinatorList);
                coordinatorRecyclerView.setAdapter(coordinatorAdapter);

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
        setContentView(R.layout.activity_search_coordinator);

        editTextSearchInputCoordinator = findViewById(R.id.editText_search_input_coordinator);

        coordinatorRecyclerView = findViewById(R.id.recyclerView_item_search_coordinator);
        coordinatorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        coordinatorRecyclerView.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("coordinators_info");

        editTextSearchInputCoordinator.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                coordinatorAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}