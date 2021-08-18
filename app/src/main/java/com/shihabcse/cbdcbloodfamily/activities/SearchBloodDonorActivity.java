package com.shihabcse.cbdcbloodfamily.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shihabcse.cbdcbloodfamily.R;
import com.shihabcse.cbdcbloodfamily.adapters.BloodDonorAdapter;
import com.shihabcse.cbdcbloodfamily.adapters.BloodRequestAdapter;
import com.shihabcse.cbdcbloodfamily.models.BloodDonor;
import com.shihabcse.cbdcbloodfamily.models.BloodRequestPatient;

import java.util.ArrayList;
import java.util.List;

public class SearchBloodDonorActivity extends AppCompatActivity {

    String blood, dist;


    RecyclerView bloodDonorRecyclerView;
    BloodDonorAdapter bloodDonorAdapter;
    EditText editTextSearchInputNew;

    //private ProgressDialog progressDialog;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<BloodDonor> bloodDonorstList;


    @Override
    protected void onStart() {
        super.onStart();

        // Get List Posts from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                bloodDonorstList = new ArrayList<>();

                for (DataSnapshot postSnap : snapshot.getChildren()) {

                    BloodDonor bloodDonor = postSnap.getValue(BloodDonor.class);
                    bloodDonorstList.add(bloodDonor);

                }

                //progressDialog.dismiss();

                bloodDonorAdapter = new BloodDonorAdapter(getApplicationContext(), bloodDonorstList);
                bloodDonorRecyclerView.setAdapter(bloodDonorAdapter);

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
        setContentView(R.layout.activity_search_blood_donor);

        editTextSearchInputNew = findViewById(R.id.editText_search_input_new);
        bloodDonorRecyclerView = findViewById(R.id.recyclerView_item_blood_donor_new);
        bloodDonorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bloodDonorRecyclerView.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("blood_donors");




        //blood = getIntent().getExtras().getString("blood");
        //dist = getIntent().getExtras().getString("dist");


        editTextSearchInputNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextSearchInputNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CharSequence b = "b+";
                //bloodDonorAdapter.getFilter().filter(b);
            }
        });

        //CharSequence cs = blood;

        //filterResult();

    }

    private void filterResult() {

    }

}