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
import com.shihabcse.cbdcbloodfamily.models.BloodBank;

public class AddBloodBankActivity extends AppCompatActivity {

    private EditText editTextBloodBankName;
    private EditText editTextBloodBankContactNo;
    private EditText editTextBloodBankAddress;
    private Button buttonBloodBankSubmit;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blood_bank);

        editTextBloodBankName = findViewById(R.id.editText_blood_bank_name);
        editTextBloodBankContactNo = findViewById(R.id.editText_blood_bank_contact_number);
        editTextBloodBankAddress = findViewById(R.id.editText_blood_bank_address);
        buttonBloodBankSubmit = findViewById(R.id.button_submit_blood_bank);
        progressDialog = new ProgressDialog(this);

        buttonBloodBankSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String bloodBankName = editTextBloodBankName.getText().toString().trim();
                final String bloodBankContactNumber = editTextBloodBankContactNo.getText().toString().trim();
                final String bloodBankAddress = editTextBloodBankAddress.getText().toString().trim();

                if (isUserInputValidation(bloodBankName, bloodBankContactNumber, bloodBankAddress)) {
                    BloodBank bloodBank = new BloodBank(bloodBankName,
                            bloodBankContactNumber,
                            bloodBankAddress
                    );
                    addDataToDatabase(bloodBank);
                }
            }
        });
    }

    private void addDataToDatabase(BloodBank bloodBank) {
        // progress Dialog
        progressDialog.setTitle("তথ্য যুক্ত হচ্ছে...");
        progressDialog.setMessage("অনুগ্রহপূর্বক অপেক্ষা করুন");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference bloodBankReference = database.getReference("blood_banks");

        // storing in realtime database according to current time
        String key = bloodBank.getBloodBankContactNumber();

        bloodBankReference.child(key).setValue(bloodBank).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("ব্লাড ব্যাংক যুক্ত হয়েছে!");
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
        Intent mainActivity = new Intent(AddBloodBankActivity.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isUserInputValidation(String bloodBankName, String bloodBankContactNumber, String bloodBankAddress) {
        if(bloodBankName.isEmpty()) {
            editTextBloodBankName.setError("ব্লাড ব্যাংকের নাম লিখুন!");
            editTextBloodBankName.requestFocus();
            return false;
        }
        if (bloodBankContactNumber.isEmpty()) {
            editTextBloodBankContactNo.setError("দয়া করে ফোন নম্বর লিখুন!");
            editTextBloodBankContactNo.requestFocus();
            return false;
        }
        if (bloodBankAddress.isEmpty()) {
            editTextBloodBankAddress.setError("দয়া করে আপনার ঠিকানা লিখুন!");
            editTextBloodBankAddress.requestFocus();
            return false;
        }
        return true;
    }
}