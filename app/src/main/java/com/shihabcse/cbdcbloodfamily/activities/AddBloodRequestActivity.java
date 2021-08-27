package com.shihabcse.cbdcbloodfamily.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shihabcse.cbdcbloodfamily.R;
import com.shihabcse.cbdcbloodfamily.models.BloodRequestPatient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddBloodRequestActivity extends AppCompatActivity {

    private EditText editTextPaitentName, editTextBloodQuantity, editTextPatientType, editTextPatientPhoneNumber, editTextPatientCity;
    private TextView textViewSelectBloodNeededDate;
    private Button buttonSubmitPatient;

    private AdView mAdView;

    private TextView textViewSelectDistrict;
    private ArrayList<String> arrayListDistrict;
    private Dialog dialog;

    private ProgressDialog progressDialog;

    private TextView textViewSelectBloodGroup;
    private ArrayList<String> arrayListSelectBloodGroup;
    private Dialog dialogBloodGroup;

    private DatabaseReference bloodRequestDatabaseReference;
    int totalBloodRequests = 0;

    String dayOfDonation, monthOfDonation, yearOfDonation;

    DatePickerDialog.OnDateSetListener setListener;

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    //Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blood_request);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //initialize
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        progressDialog = new ProgressDialog(this);

        editTextPaitentName = findViewById(R.id.editTextText_patient_name);
        editTextBloodQuantity = findViewById(R.id.editTextText_blood_quantity);
        textViewSelectBloodGroup = findViewById(R.id.textView_select_blood_group_patient);
        textViewSelectDistrict = findViewById(R.id.textView_select_district_patient);
        editTextPatientCity = findViewById(R.id.editTextText_patient_city);
        textViewSelectBloodNeededDate = findViewById(R.id.textView_blood_needed_date);
        editTextPatientType = findViewById(R.id.editTextText_patient_type);
        editTextPatientPhoneNumber = findViewById(R.id.editTextText_patient_phone_number);
        buttonSubmitPatient = findViewById(R.id.button_submit_patient);

        bloodRequestDatabaseReference = FirebaseDatabase.getInstance().getReference().child("blood_request");


        bloodRequestDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    totalBloodRequests = (int) snapshot.getChildrenCount();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bloodRequestDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                notification();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        buttonSubmitPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String patientName = editTextPaitentName.getText().toString().trim();
                final String quantityOfBlood = editTextBloodQuantity.getText().toString().trim();
                final String patientType = editTextPatientType.getText().toString().trim();
                final String patientPhoneNumber = editTextPatientPhoneNumber.getText().toString().trim();
                final String bloodGroup = textViewSelectBloodGroup.getText().toString().trim();
                final String district = textViewSelectDistrict.getText().toString().trim();
                final String city = editTextPatientCity.getText().toString().trim();
                final String bloodNeededDate = textViewSelectBloodNeededDate.getText().toString().trim();

                // checking the input validation first
                if (isUserInputValidation(patientName, quantityOfBlood, patientType, patientPhoneNumber, bloodGroup, district, city, bloodNeededDate) == false) {


                    //buttonRegister.setVisibility(View.VISIBLE);

                } else {

                    BloodRequestPatient bloodRequestPatient = new BloodRequestPatient(currentUser.getUid(),
                            patientName,
                            quantityOfBlood,
                            bloodGroup,
                            patientType,
                            patientPhoneNumber,
                            district,
                            city,
                            bloodNeededDate
                    );

                    addDataToDatabase(bloodRequestPatient);

                }

            }
        });


        // for date picker Dialog box taking current day, month year to display
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

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

        textViewSelectBloodNeededDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddBloodRequestActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                textViewSelectBloodNeededDate.setText(date);

                dayOfDonation = String.valueOf(dayOfMonth);
                monthOfDonation = String.valueOf(month);
                yearOfDonation = String.valueOf(year);

            }
        };


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


    }

    private void notification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")
                .setContentText("CDBC Blood Family")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setAutoCancel(true)
                .setContentText("Someone needs Blood. Please check blood request");

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());

    }

    private void addDataToDatabase(BloodRequestPatient bloodRequestPatient) {

        // progress Dialog
        progressDialog.setTitle("তথ্য যুক্ত হচ্ছে...");
        progressDialog.setMessage("অনুগ্রহপূর্বক অপেক্ষা করুন");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference donationHistoryReference = database.getReference("blood_request");

        // storing in realtime database according to current time
        //Calendar calForTime = Calendar.getInstance();
        //SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss", Locale.US);
        //String key = currentTime.format(calForTime.getTime());
        String key = bloodRequestPatient.getPatientPhoneNumber();

        donationHistoryReference.child(key).setValue(bloodRequestPatient).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("রক্তের অনুরোধ ডাটাবেজে যুক্ত হয়েছে");
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
        Intent mainActivity = new Intent(AddBloodRequestActivity.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    private void showMessage(String message) {
        Toast.makeText(AddBloodRequestActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isUserInputValidation(String patientName, String quantityOfBlood, String patientType, String patientPhoneNumber, String bloodGroup, String district, String city, String bloodNeededDate) {

        if (patientName.isEmpty()) {
            editTextPaitentName.setError("Please enter patient name!");
            editTextPaitentName.requestFocus();
            return false;
        }

        if (quantityOfBlood.isEmpty()) {
            editTextBloodQuantity.setError("Please enter blood quantity!");
            editTextBloodQuantity.requestFocus();
            return false;
        }

        if (patientType.isEmpty()) {
            editTextPatientType.setError("Please enter patient type!");
            editTextPatientType.requestFocus();
            return false;
        }

        if (patientPhoneNumber.isEmpty()) {
            editTextPatientPhoneNumber.setError("Please enter patient's Phone Number!");
            editTextPatientPhoneNumber.requestFocus();
            return false;
        }

        if (bloodGroup.equals("Select blood group") || bloodGroup.equals("রক্তের গ্রুপ")) {
            Toast.makeText(this, "Select Your Blood Group", Toast.LENGTH_LONG).show();
            return false;
        }

        if (district.equals("Select your district") || district.equals("জেলা নির্বাচন")) {
            Toast.makeText(this, "Select Your District", Toast.LENGTH_LONG).show();
            return false;
        }

        if (city.isEmpty()) {
            editTextPatientCity.setError("Please enter patient's City!");
            editTextPatientCity.requestFocus();
            return false;
        }

        if (bloodNeededDate.equals("Select Date")) {
            Toast.makeText(this, "Select Date", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }

    private void selectDistrictBySearch() {
        //Initialize Dialog
        dialog = new Dialog(AddBloodRequestActivity.this);
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddBloodRequestActivity.this, android.R.layout.simple_list_item_1, arrayListDistrict);
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
        dialogBloodGroup = new Dialog(AddBloodRequestActivity.this);
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
        ArrayAdapter<String> arrayAdapterBloodGroup = new ArrayAdapter<>(AddBloodRequestActivity.this, android.R.layout.simple_list_item_1, arrayListSelectBloodGroup);
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