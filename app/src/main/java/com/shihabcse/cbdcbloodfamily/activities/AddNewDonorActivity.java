package com.shihabcse.cbdcbloodfamily.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shihabcse.cbdcbloodfamily.R;
import com.shihabcse.cbdcbloodfamily.models.BloodDonor;
import com.shihabcse.cbdcbloodfamily.models.BloodRequestPatient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddNewDonorActivity extends AppCompatActivity {

    private CheckBox checkBoxDonationStatus;

    private TextView textViewLastDonationDate;

    private TextView textViewSelectDistrict;

    private ArrayList<String> arrayListDistrict;
    private Dialog dialog;

    private EditText editTextBloodDonorName, editTextBloodDonorContactNumber;
    private EditText editTextOrganization;
    private Button buttonSubmit;

    private TextView textViewSelectBloodGroup;
    private ArrayList<String> arrayListSelectBloodGroup;
    private Dialog dialogBloodGroup;

    String dayOfDonation, monthOfDonation, yearOfDonation;

    DatePickerDialog.OnDateSetListener setListener;

    private AdView mAdView;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_donor);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adViewnew);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        checkBoxDonationStatus = findViewById(R.id.checkBox_donate_status);
        editTextBloodDonorName = findViewById(R.id.editTextText_blood_donor_name);
        editTextBloodDonorContactNumber = findViewById(R.id.editTextText_number_of_contact_number);
        textViewSelectBloodGroup = findViewById(R.id.textView_select_blood_group);
        textViewSelectDistrict = findViewById(R.id.textView_select_district);
        textViewLastDonationDate = findViewById(R.id.textView_blood_last_donation_date);
        editTextOrganization = findViewById(R.id.editTextText_blood_donor_organization);
        buttonSubmit = findViewById(R.id.button_submit);

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

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                textViewLastDonationDate.setText(date);

                dayOfDonation = String.valueOf(dayOfMonth);
                monthOfDonation = String.valueOf(month);
                yearOfDonation = String.valueOf(year);

            }
        };

        // for date picker Dialog box taking current day, month year to display
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        textViewLastDonationDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewDonorActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String bloodDonorName = editTextBloodDonorName.getText().toString().trim();
                final String contactNumber = editTextBloodDonorContactNumber.getText().toString().trim();
                final String district = textViewSelectDistrict.getText().toString().trim();
                final String bloodGroup = textViewSelectBloodGroup.getText().toString().trim();
                String lastBloodDonationDate = textViewLastDonationDate.getText().toString().trim();
                String bloodDonorOrganization = editTextOrganization.getText().toString().trim();
                String bloodDonationStatus = "No";

                // checking the input validation first
                if (isUserInputValidation(bloodDonorName, contactNumber, district, bloodGroup) == false) {

                    //buttonRegister.setVisibility(View.VISIBLE);

                } else {

                    if (bloodDonorOrganization.isEmpty()) {
                        bloodDonorOrganization = "N/A";
                    }

                    if (lastBloodDonationDate.equals("Select Last Donation Date")) {
                        lastBloodDonationDate = "N/A";
                    }


                    if(checkBoxDonationStatus.isChecked()){
                        bloodDonationStatus = "Yes";
                    }

                    BloodDonor bloodDonor = new BloodDonor(bloodDonorName,
                            contactNumber,
                            district,
                            bloodGroup,
                            lastBloodDonationDate,
                            bloodDonorOrganization,
                            bloodDonationStatus
                    );

                    addDataToDatabase(bloodDonor);

                }
            }
        });

    }

    private void selectDistrictBySearch() {
        //Initialize Dialog
        dialog = new Dialog(AddNewDonorActivity.this);
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddNewDonorActivity.this, android.R.layout.simple_list_item_1, arrayListDistrict);
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
        dialogBloodGroup = new Dialog(AddNewDonorActivity.this);
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
        ArrayAdapter<String> arrayAdapterBloodGroup = new ArrayAdapter<>(AddNewDonorActivity.this, android.R.layout.simple_list_item_1, arrayListSelectBloodGroup);
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

    private void addDataToDatabase(BloodDonor bloodDonor) {

        // progress Dialog
        progressDialog.setTitle("Adding Information");
        progressDialog.setMessage("Please wait ...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference addDonorReference = database.getReference("blood_donors");

        // storing in realtime database according to current time
        String key = bloodDonor.getBloodDonorContactNumber();

        addDonorReference.child(key).setValue(bloodDonor).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("New Donor Added");
                progressDialog.dismiss();
                updateUI();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                showMessage("Failed to Add Donor");
            }
        });

    }

    private void updateUI() {
        Intent mainActivity = new Intent(AddNewDonorActivity.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    private void showMessage(String message) {
        Toast.makeText(AddNewDonorActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isUserInputValidation(String bloodDonorName, String contactNumber, String address, String bloodGroup) {

        if(bloodDonorName.isEmpty()) {
            editTextBloodDonorName.setError("Please enter donor's name!");
            editTextBloodDonorName.requestFocus();
            return false;
        }

        if (contactNumber.isEmpty()) {
            editTextBloodDonorContactNumber.setError("Please enter contact number!");
            editTextBloodDonorContactNumber.requestFocus();
            return false;
        }

        if (address.equals("Select District")) {
            Toast.makeText(this, "Select District", Toast.LENGTH_LONG).show();
            return false;
        }

        if (bloodGroup.equals("Select blood group")) {
            Toast.makeText(this, "Select Blood Group", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }
}