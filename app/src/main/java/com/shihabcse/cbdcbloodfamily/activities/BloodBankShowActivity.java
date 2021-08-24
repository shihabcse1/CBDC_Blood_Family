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
import com.shihabcse.cbdcbloodfamily.adapters.BloodBankAdapter;
import com.shihabcse.cbdcbloodfamily.adapters.HelplineAdapter;
import com.shihabcse.cbdcbloodfamily.models.BloodBank;
import com.shihabcse.cbdcbloodfamily.models.HelpLine;

import java.util.ArrayList;
import java.util.List;

public class BloodBankShowActivity extends AppCompatActivity {

    RecyclerView bloodBankRecyclerView;
    BloodBankAdapter bloodBankAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<BloodBank> bloodBankList;

    EditText editTextSearchInputBloodBank;

    @Override
    protected void onStart() {
        super.onStart();

        // Get List Posts from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                bloodBankList = new ArrayList<>();

                for (DataSnapshot postSnap : snapshot.getChildren()) {

                    BloodBank bloodBank = postSnap.getValue(BloodBank.class);
                    bloodBankList.add(bloodBank);

                }
                //progressDialog.dismiss();
                bloodBankAdapter = new BloodBankAdapter(getApplicationContext(), bloodBankList);
                bloodBankRecyclerView.setAdapter(bloodBankAdapter);

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
        setContentView(R.layout.activity_blood_bank_show);

        editTextSearchInputBloodBank = findViewById(R.id.editText_search_input_blood_bank);
        bloodBankRecyclerView = findViewById(R.id.recyclerView_item_show_blood_bank);
        bloodBankRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bloodBankRecyclerView.setHasFixedSize(true);
        bloodBankRecyclerView.setAdapter(bloodBankAdapter);//this line added for checking

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("blood_banks");

        editTextSearchInputBloodBank.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bloodBankAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}