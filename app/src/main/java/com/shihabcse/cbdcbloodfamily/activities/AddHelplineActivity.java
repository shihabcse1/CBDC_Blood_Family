package com.shihabcse.cbdcbloodfamily.activities;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shihabcse.cbdcbloodfamily.R;
import com.shihabcse.cbdcbloodfamily.models.HelpLine;

import java.util.ArrayList;

public class AddHelplineActivity extends AppCompatActivity {

    private EditText editTextAmbulanceName;
    private EditText editTextAmbulancePhoneNumber;
    private EditText editTextAmbulanceUniversityOrUpazila;
    private TextView ambulanceSelectDistrict;
    private ArrayList<String> arrayListDistrict;
    private Dialog dialog;
    private CheckBox checkBoxAmbulanceStatus;
    private Button buttonAmbulanceSubmit;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_helpline);

        checkBoxAmbulanceStatus = findViewById(R.id.checkBox_ambulance_status);
        editTextAmbulanceName = findViewById(R.id.editTextText_ambulance_name);
        editTextAmbulancePhoneNumber = findViewById(R.id.editTextText_ambulance_contact_number);
        editTextAmbulanceUniversityOrUpazila = findViewById(R.id.editTextText_upozila_or_university);
        ambulanceSelectDistrict = findViewById(R.id.textView_select_district);
        buttonAmbulanceSubmit = findViewById(R.id.button_submit_helpline);
        progressDialog = new ProgressDialog(this);

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
        ambulanceSelectDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDistrictBySearch();
            }
        });

        buttonAmbulanceSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String ambulanceName = editTextAmbulanceName.getText().toString().trim();
                final String ambulancePhoneNumber = editTextAmbulancePhoneNumber.getText().toString().trim();
                final String district = ambulanceSelectDistrict.getText().toString().trim();
                final String ambulanceUniversityOrUpazila = editTextAmbulanceUniversityOrUpazila.getText().toString().trim();
                String bloodDonationStatus = "No";

                if (isUserInputValidation(ambulanceName, ambulancePhoneNumber, district, ambulanceUniversityOrUpazila) == false) {

                    //buttonRegister.setVisibility(View.VISIBLE);

                } else {
                    if (checkBoxAmbulanceStatus.isChecked()) {
                        bloodDonationStatus = "Yes";
                    }
                    HelpLine helpLine = new HelpLine(ambulanceName,
                            ambulancePhoneNumber,
                            ambulanceUniversityOrUpazila,
                            district,
                            bloodDonationStatus
                    );
                    addDataToDatabase(helpLine);
                }
            }
        });

    }

    private void addDataToDatabase(HelpLine helpLine) {
        // progress Dialog
        progressDialog.setTitle("তথ্য যুক্ত হচ্ছে...");
        progressDialog.setMessage("অনুগ্রহপূর্বক অপেক্ষা করুন!");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference addAmbulanceReference = database.getReference("help_lines");

        // storing in realtime database according to current time
        String key = helpLine.getAmbulanceContactNumber();

        addAmbulanceReference.child(key).setValue(helpLine).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("হেল্পলাইন ডাটাবেজে যুক্ত হয়েছে!");
                progressDialog.dismiss();
                updateUI();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                showMessage("দুঃখিত! ডাটাবেজে যুক্ত করা যায়নি");
            }
        });
    }

    private void updateUI() {
        Intent mainActivity = new Intent(AddHelplineActivity.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isUserInputValidation(String ambulanceName, String ambulancePhoneNumber, String ambulanceSelectDistrict, String ambulanceUniversityOrUpazila) {

        if (ambulanceName.isEmpty()) {
            editTextAmbulanceName.setError("দয়া করে অ্যাম্বুলেন্সের নাম লিখুন!");
            editTextAmbulanceName.requestFocus();
            return false;
        }

        if (ambulancePhoneNumber.isEmpty()) {
            editTextAmbulancePhoneNumber.setError("দয়া করে যোগাযোগ নম্বর লিখুন!");
            editTextAmbulancePhoneNumber.requestFocus();
            return false;
        }

        if (ambulanceUniversityOrUpazila.isEmpty()) {
            editTextAmbulanceUniversityOrUpazila.setError("অনুগ্রহ করে উপজেলা বা বিশ্ববিদ্যালয়ের নাম লিখুন!");
            editTextAmbulanceUniversityOrUpazila.requestFocus();
            return false;
        }

        if (ambulanceSelectDistrict.equals("Select District") || ambulanceSelectDistrict.equals("জেলা নির্বাচন")) {
            Toast.makeText(this, "জেলা নির্বাচন করুন", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }

    private void selectDistrictBySearch() {
        //Initialize Dialog
        dialog = new Dialog(AddHelplineActivity.this);
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddHelplineActivity.this, android.R.layout.simple_list_item_1, arrayListDistrict);
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
                ambulanceSelectDistrict.setText(arrayAdapter.getItem(position));
                //Dismiss dialog
                dialog.dismiss();
            }
        });
    }
}