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
import com.shihabcse.cbdcbloodfamily.adapters.OrganizationAdapter;
import com.shihabcse.cbdcbloodfamily.models.Organization;

import java.util.ArrayList;
import java.util.List;

public class SearchOrganizationActivity extends AppCompatActivity {

    RecyclerView organizationRecyclerView;
    OrganizationAdapter organizationAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<Organization> organizationsList;

    EditText editTextSearchInputOrganization;

    @Override
    protected void onStart() {
        super.onStart();

        // Get List Posts from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                organizationsList = new ArrayList<>();

                for (DataSnapshot postSnap : snapshot.getChildren()) {

                    Organization organization = postSnap.getValue(Organization.class);
                    organizationsList.add(organization);

                }

                //progressDialog.dismiss();

                organizationAdapter = new OrganizationAdapter(SearchOrganizationActivity.this, organizationsList);
                organizationRecyclerView.setAdapter(organizationAdapter);

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
        setContentView(R.layout.activity_search_organization);

        editTextSearchInputOrganization = findViewById(R.id.editText_search_input_organization);

        organizationRecyclerView = findViewById(R.id.recyclerView_item_search_organization);
        organizationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        organizationRecyclerView.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("organization_list");

        editTextSearchInputOrganization.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                organizationAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}