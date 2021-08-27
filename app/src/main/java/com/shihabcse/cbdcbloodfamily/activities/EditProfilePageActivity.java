package com.shihabcse.cbdcbloodfamily.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shihabcse.cbdcbloodfamily.R;
import com.shihabcse.cbdcbloodfamily.models.RegisterUser;
import com.shihabcse.cbdcbloodfamily.models.RegisterUserEdited;

import java.util.ArrayList;

public class EditProfilePageActivity extends AppCompatActivity {

    private CheckBox checkBoxDonationStatus;
    private EditText editTextUserPhoneNumber, editTextUserCountry, editTextUserCity, editTextUserOrganization;
    private Button buttonUpdate;

    private TextView textViewSelectDistrict;
    private ArrayList<String> arrayListDistrict;
    private Dialog dialog;

    private TextView textViewSelectBloodGroup;
    private ArrayList<String> arrayListSelectBloodGroup;
    private Dialog dialogBloodGroup;

    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_page);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        editTextUserPhoneNumber = findViewById(R.id.editTextText_phone_number);
        editTextUserCountry = findViewById(R.id.editTextText_user_country);
        textViewSelectDistrict = findViewById(R.id.textView_select_district);
        checkBoxDonationStatus = findViewById(R.id.checkBox_donate_status);
        editTextUserCity = findViewById(R.id.editTextText_user_city);
        editTextUserOrganization = findViewById(R.id.editTextText_user_organization);
        textViewSelectBloodGroup = findViewById(R.id.textView_select_blood_group_edited);
        buttonUpdate = findViewById(R.id.button_update_info);

        progressDialog = new ProgressDialog(this);

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


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String phoneNumber = editTextUserPhoneNumber.getText().toString().trim();
                final String district = textViewSelectDistrict.getText().toString().trim();
                final String bloodGroup = textViewSelectBloodGroup.getText().toString().trim();
                final String country = editTextUserCountry.getText().toString().trim();
                final String city = editTextUserCity.getText().toString().trim();
                String organizationEdit = editTextUserOrganization.getText().toString().trim();
                String bloodDonationStatus = "No";

                // checking the input validation first
                if (isUserInputValidation(phoneNumber, bloodGroup, country, district, city, organizationEdit) == false) {


                    //buttonRegister.setVisibility(View.VISIBLE);

                } else {

                    if(checkBoxDonationStatus.isChecked()){
                        bloodDonationStatus = "Yes";
                    }

                    if (organizationEdit.isEmpty()) {
                        organizationEdit = "N/A";
                    }

                    updateUserInfoWithoutPhoto(phoneNumber, bloodGroup, country, district, city, organizationEdit, bloodDonationStatus);

                }

            }
        });

    }

    private void updateUserInfoWithoutPhoto(String phoneNumber, String bloodGroup, String country, String district, String city, String organizationEdit, String bloodDonationStatus) {
        RegisterUserEdited registerUserEdited = new RegisterUserEdited(
                currentUser.getUid(),
                currentUser.getDisplayName(),
                currentUser.getEmail(),
                phoneNumber,
                bloodGroup,
                country,
                district,
                city,
                organizationEdit,
                bloodDonationStatus
        );

        // add all data to firebase Database
        addDataToDatabase(registerUserEdited);
    }

    private void selectBloodGroupBySearch() {
        //Initialize Dialog
        dialogBloodGroup = new Dialog(EditProfilePageActivity.this);
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
        ArrayAdapter<String> arrayAdapterBloodGroup = new ArrayAdapter<>(EditProfilePageActivity.this, android.R.layout.simple_list_item_1, arrayListSelectBloodGroup);
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

    private void selectDistrictBySearch() {
        //Initialize Dialog
        dialog = new Dialog(EditProfilePageActivity.this);
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EditProfilePageActivity.this, android.R.layout.simple_list_item_1, arrayListDistrict);
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



    private void addDataToDatabase(RegisterUserEdited registerUserEdited) {
        // progress Dialog
        progressDialog.setTitle("তথ্য যুক্ত হচ্ছে...");
        progressDialog.setMessage("অনুগ্রহপূর্বক অপেক্ষা করুন");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("register_users_info");
        // get bloodDonor Unique ID and upload bloodDonor key
        // add bloodDonor Data to firebase Database
        myRef.child(currentUser.getUid()).setValue(registerUserEdited).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                //openInfoUpdatedDialog();
                showMessage("তথ্য ডাটাবেসে যুক্ত হয়েছে!");
                progressDialog.dismiss();
                updateUI();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                // fail to Add data
                showMessage(e.getMessage());
                //buttonSubmit.setVisibility(View.VISIBLE);
                //progressBarSubmit.setVisibility(View.INVISIBLE);
                //editTextPhoneNumber.getText().clear();
                //editTextAddressDonor.getText().clear();

            }
        });
    }

    private void updateUI() {
        Intent mainActivity = new Intent(EditProfilePageActivity.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private boolean isUserInputValidation(String phoneNumber, String bloodGroup, String country, String district, String city, String organizationEdit) {

        if (phoneNumber.isEmpty()) {
            editTextUserPhoneNumber.setError("দয়া করে ফোন নম্বর লিখুন!");
            editTextUserPhoneNumber.requestFocus();
            return false;
        }

        if (bloodGroup.equals("Select blood group") || bloodGroup.equals("রক্তের গ্রুপ")) {
            Toast.makeText(this, "রক্তের গ্রুপ নির্বাচন করুন", Toast.LENGTH_LONG).show();
            return false;
        }

        if (country.isEmpty()) {
            editTextUserCountry.setError("দয়া করে দেশের নাম লিখুন!");
            editTextUserCountry.requestFocus();
            return false;
        }

        if (district.equals("Select your district") || district.equals("জেলা নির্বাচন")) {
            Toast.makeText(this, "জেলা নির্বাচন করুন", Toast.LENGTH_LONG).show();
            return false;
        }

        if (city.isEmpty()) {
            editTextUserCity.setError("দয়া করে শহর/থানার নাম লিখুন!");
            editTextUserCity.requestFocus();
            return false;
        }

        return true;
    }

}