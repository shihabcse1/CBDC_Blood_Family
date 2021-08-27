package com.shihabcse.cbdcbloodfamily.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shihabcse.cbdcbloodfamily.R;
import com.shihabcse.cbdcbloodfamily.models.BloodDonor;
import com.shihabcse.cbdcbloodfamily.models.Coordinator;

public class CoordinatorActivity extends AppCompatActivity {

    private EditText editTextCoordinatorName, editTextCoordinatorContactNumber, editTextAppAdminPassword;
    private Button buttonSubmitCoordinator;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        editTextCoordinatorName = findViewById(R.id.editTextText_coordinator_name);
        editTextCoordinatorContactNumber = findViewById(R.id.editTextText_number_of_contact_number);
        editTextAppAdminPassword = findViewById(R.id.editTextText_app_admin_passoword);
        buttonSubmitCoordinator = findViewById(R.id.button_submit_coordinator);

        progressDialog = new ProgressDialog(this);

        buttonSubmitCoordinator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String coordinatorName = editTextCoordinatorName.getText().toString().trim();
                final String coordinatorContactNumber = editTextCoordinatorContactNumber.getText().toString().trim();
                final String appAdminPassword = editTextAppAdminPassword.getText().toString().trim();

                // checking the input validation first
                if (isUserInputValidation(coordinatorName, coordinatorContactNumber, appAdminPassword) == false) {


                    //buttonRegister.setVisibility(View.VISIBLE);

                } else {


                    Coordinator coordinator = new Coordinator(coordinatorName,
                            coordinatorContactNumber
                    );

                    addDataToDatabase(coordinator);

                }


            }
        });

    }

    private void addDataToDatabase(Coordinator coordinator) {

        // progress Dialog
        progressDialog.setTitle("তথ্য যুক্ত হচ্ছে...");
        progressDialog.setMessage("অনুগ্রহপূর্বক অপেক্ষা করুন");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("coordinators_info");
        // get bloodDonor Unique ID and upload bloodDonor key
        // add bloodDonor Data to firebase Database
        myRef.child(coordinator.getCoordinatorContactNumber()).setValue(coordinator).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                editTextCoordinatorName.getText().clear();
                editTextCoordinatorContactNumber.getText().clear();
                editTextAppAdminPassword.getText().clear();

            }
        });
    }

    private void updateUI() {
        Intent mainActivity = new Intent(CoordinatorActivity.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private boolean isUserInputValidation(String coordinatorName, String coordinatorContactNumber, String appAdminPassword) {
        if(coordinatorName.isEmpty()) {
            editTextCoordinatorName.setError("অনুগ্রহ করে সমন্বয়কের নাম লিখুন!");
            editTextCoordinatorName.requestFocus();
            return false;
        }

        if (coordinatorContactNumber.isEmpty()) {
            editTextCoordinatorContactNumber.setError("দয়া করে যোগাযোগ নম্বর লিখুন!");
            editTextCoordinatorContactNumber.requestFocus();
            return false;
        }

        if (!appAdminPassword.equals("f9f8f7!")) {
            Toast.makeText(this, "ভুল পাসওয়ার্ড!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}