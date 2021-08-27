package com.shihabcse.cbdcbloodfamily.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
import com.shihabcse.cbdcbloodfamily.models.BloodDonor;

import java.util.ArrayList;
import java.util.List;

public class SearchBloodDonorConditionActivity extends AppCompatActivity {

    private TextView textViewSelectDistrict;
    private ArrayList<String> arrayListDistrict;
    private Dialog dialog;

    private InterstitialAd mInterstitialAd;

    private TextView textViewSelectBloodGroup;
    private ArrayList<String> arrayListSelectBloodGroup;
    private Dialog dialogBloodGroup;

    private Button buttonSearch;

    RecyclerView bloodDonorRecyclerView;
    BloodDonorAdapter bloodDonorAdapter;
    //EditText editTextSearchInputNew;

    private LinearLayout linearLayout;

    //private ProgressDialog progressDialog;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<BloodDonor> bloodDonorstList;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mInterstitialAd != null) {
            mInterstitialAd.show(SearchBloodDonorConditionActivity.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }

    }

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

                bloodDonorAdapter = new BloodDonorAdapter(SearchBloodDonorConditionActivity.this, bloodDonorstList);
                bloodDonorRecyclerView.setAdapter(bloodDonorAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "আপনার ইন্টারনেট সংযোগ চেক করুন!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_blood_donor_condition);

        //editTextSearchInputNew = findViewById(R.id.editText_search_input_new);
        linearLayout = findViewById(R.id.linearLayout);
        bloodDonorRecyclerView = findViewById(R.id.recyclerView);
        bloodDonorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bloodDonorRecyclerView.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("blood_donors");

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-6210048043141055/8469669698", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.i("TAG", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i("TAG", loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });


        textViewSelectDistrict = findViewById(R.id.textView_select_district);
        textViewSelectBloodGroup = findViewById(R.id.textView_select_blood_group_edited);
        buttonSearch = findViewById(R.id.button_search);

        arrayListSelectBloodGroup = new ArrayList<>();
        arrayListSelectBloodGroup.add("A+");
        arrayListSelectBloodGroup.add("A-");
        arrayListSelectBloodGroup.add("B+");
        arrayListSelectBloodGroup.add("B-");
        arrayListSelectBloodGroup.add("O+");
        arrayListSelectBloodGroup.add("O-");
        arrayListSelectBloodGroup.add("AB+");
        arrayListSelectBloodGroup.add("AB-");
        textViewSelectBloodGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBloodGroupBySearch();
            }
        });


        arrayListDistrict = new ArrayList<>();
        arrayListDistrict.add("Bagerhat");
        arrayListDistrict.add("Chuadanga");
        arrayListDistrict.add("Jessore");
        arrayListDistrict.add("Jhenaidah");
        arrayListDistrict.add("Khulna");
        arrayListDistrict.add("Kushtia");
        arrayListDistrict.add("Magura");
        arrayListDistrict.add("Meherpur");
        arrayListDistrict.add("Narail");
        arrayListDistrict.add("Satkhira ");
        arrayListDistrict.add("Bandarban");
        arrayListDistrict.add("Brahmanbaria");
        arrayListDistrict.add("Chandpur");
        arrayListDistrict.add("Chittagong");
        arrayListDistrict.add("Comilla");
        arrayListDistrict.add("Cox's Bazar");
        arrayListDistrict.add(" Feni");
        arrayListDistrict.add("Khagrachari");
        arrayListDistrict.add("Lakshmipur");
        arrayListDistrict.add("Bogura");
        arrayListDistrict.add("Jaipurhat");
        arrayListDistrict.add("Naogaon");
        arrayListDistrict.add("Natore");
        arrayListDistrict.add("Noakhali");
        arrayListDistrict.add("Rangamati");
        arrayListDistrict.add("Barguna");
        arrayListDistrict.add("Barisal");
        arrayListDistrict.add("Bhola");
        arrayListDistrict.add("Dhaka");
        arrayListDistrict.add("Jhalokati");
        arrayListDistrict.add("Patuakhali");
        arrayListDistrict.add("Nawabganj");
        arrayListDistrict.add("Pabna");
        arrayListDistrict.add("Rajshahi");
        arrayListDistrict.add("Sirajganj");
        arrayListDistrict.add("Faridpur");
        arrayListDistrict.add("Gazipur");
        arrayListDistrict.add("Gopalganj");
        arrayListDistrict.add("Kishoreganj");
        arrayListDistrict.add("Madaripur");
        arrayListDistrict.add("Manikganj");
        arrayListDistrict.add("Munshiganj");
        arrayListDistrict.add("Narayanganj");
        arrayListDistrict.add("Narsingdi");
        arrayListDistrict.add("Rajbari");
        arrayListDistrict.add("Shariatpur");
        arrayListDistrict.add("Tangail");
        arrayListDistrict.add("Dinajpur");
        arrayListDistrict.add("Gaibandha");
        arrayListDistrict.add("Kurigram");
        arrayListDistrict.add("Lalmonirhat");
        arrayListDistrict.add("Nilphamari");
        arrayListDistrict.add("Panchagarh");
        arrayListDistrict.add("Rangpur");
        arrayListDistrict.add("Thakurgaon");
        arrayListDistrict.add("Habiganj");
        arrayListDistrict.add("Jamalpur");
        arrayListDistrict.add("Moulvibazar");
        arrayListDistrict.add("Sunamganj");
        arrayListDistrict.add(" Sylhet");
        arrayListDistrict.add("Mymensingh");
        arrayListDistrict.add("Netrokona");
        arrayListDistrict.add("Sherpur");
        textViewSelectDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectDistrictBySearch();

            }
        });



        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String bloodGroup = textViewSelectBloodGroup.getText().toString().trim();
                final String district = textViewSelectDistrict.getText().toString().trim();


                if (isUserInputValidation(bloodGroup, district) == false) {

                } else {

                    //Intent searchBloodDonorActivity = new Intent(SearchBloodDonorConditionActivity.this, SearchBloodDonorActivity.class);
                    //searchBloodDonorActivity.putExtra("blood", bloodGroup);
                    //searchBloodDonorActivity.putExtra("dist", district);
                    linearLayout.setVisibility(View.INVISIBLE);
                    bloodDonorRecyclerView.setVisibility(View.VISIBLE);

                    CharSequence joinString = bloodGroup+":"+district;
                    bloodDonorAdapter.getFilter().filter(joinString);

                    if (bloodDonorAdapter.getItemCount() == 0){
                        //linearLayout.setVisibility(View.VISIBLE);
                        Toast.makeText(SearchBloodDonorConditionActivity.this, "No Data Found", Toast.LENGTH_LONG).show();
                    }


                }

            }
        });

    }

    private boolean isUserInputValidation(String bloodGroup, String district) {

        if (bloodGroup.equals("Select blood group") || bloodGroup.equals("রক্তের গ্রুপ")) {
            Toast.makeText(this, "রক্তের গ্রুপ নির্বাচন করুন", Toast.LENGTH_LONG).show();
            return false;
        }

        if (district.equals("Select District") || district.equals("জেলা নির্বাচন")) {
            Toast.makeText(this, "জেলা নির্বাচন করুন", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void selectDistrictBySearch() {
        //Initialize Dialog
        dialog = new Dialog(SearchBloodDonorConditionActivity.this);
        //set custom Dialog
        dialog.setContentView(R.layout.dialog_searable_district_spinner);
        //set custom height and weight
        dialog.getWindow().setLayout(700, 900);
        //set transparent background
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //show Dialog
        dialog.show();

        //Initialize and assign
        EditText editText = dialog.findViewById(R.id.edit_text_search);
        ListView listView = dialog.findViewById(R.id.listView_district);

        //Initialize arry adapter
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SearchBloodDonorConditionActivity.this, android.R.layout.simple_list_item_1, arrayListDistrict);
        //set adapter
        listView.setAdapter(arrayAdapter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //When item selected from list
                //set selected item on Text view
                textViewSelectDistrict.setText(arrayAdapter.getItem(position));
                //Dismiss dialog
                dialog.dismiss();
            }
        });
    }

    private void selectBloodGroupBySearch() {
        //Initialize Dialog
        dialogBloodGroup = new Dialog(SearchBloodDonorConditionActivity.this);
        //set custom Dialog
        dialogBloodGroup.setContentView(R.layout.dialog_searable_blood_group_spinner);
        //set custom height and weight
        dialogBloodGroup.getWindow().setLayout(700, 900);
        //set transparent background
        dialogBloodGroup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //show Dialog
        dialogBloodGroup.show();

        //Initialize and assign
        EditText editTextBloodGroup = dialogBloodGroup.findViewById(R.id.edit_text_search_blood_group);
        ListView listViewBloodGroup = dialogBloodGroup.findViewById(R.id.listView_blood_group);

        //Initialize arry adapter
        ArrayAdapter<String> arrayAdapterBloodGroup = new ArrayAdapter<>(SearchBloodDonorConditionActivity.this, android.R.layout.simple_list_item_1, arrayListSelectBloodGroup);
        //set adapter
        listViewBloodGroup.setAdapter(arrayAdapterBloodGroup);
        editTextBloodGroup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayAdapterBloodGroup.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listViewBloodGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //When item selected from list
                //set selected item on Text view
                textViewSelectBloodGroup.setText(arrayAdapterBloodGroup.getItem(position));
                //Dismiss dialog
                dialogBloodGroup.dismiss();
            }
        });
    }
}