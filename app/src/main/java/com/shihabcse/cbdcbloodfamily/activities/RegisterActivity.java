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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shihabcse.cbdcbloodfamily.R;
import com.shihabcse.cbdcbloodfamily.models.RegisterUser;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private TextView textViewSelectDistrict;
    private ArrayList<String> arrayListDistrict;
    private Dialog dialog;

    private EditText editTextUserRegisterName, editTextUserRegisterEmail, editTextUserPassword,
            editTextUserConfirmPassword;
    private Button buttonRegister;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        editTextUserRegisterName = findViewById(R.id.editTextText_user_name);
        editTextUserRegisterEmail = findViewById(R.id.editTextText_user_register_email);
        editTextUserPassword = findViewById(R.id.editTextText_user_register_password);
        editTextUserConfirmPassword = findViewById(R.id.editTextText_user_register_confirm_password);

        progressDialog = new ProgressDialog(this);

        buttonRegister = findViewById(R.id.button_sign_up);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = editTextUserRegisterName.getText().toString().trim();
                final String email = editTextUserRegisterEmail.getText().toString().trim();
                final String password1 = editTextUserPassword.getText().toString().trim();
                final String password2 = editTextUserConfirmPassword.getText().toString().trim();

                // checking the input validation first
                if (isUserInputValidation(name, email, password1, password2) == false) {


                    //buttonRegister.setVisibility(View.VISIBLE);

                } else {

                    createUserAccount(name, email, password1);

                }
            }
        });
    }

    private void createUserAccount(final String name, final String email, final String password1) {

        // progress Dialog
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Please wait ...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            showMessage("Account Created");//Account has been created successfully

                            updateUserInfoWithoutPhoto(name, mAuth.getCurrentUser());
                            //After Creating account we need to update profile picture and name
                            // we need to check whether User picked an Image or not

                        } else {
                            progressDialog.dismiss();
                            showMessage("Account Creation Failed!" + task.getException());
                        }

                    }
                });

    }

    private void updateUserInfoWithoutPhoto(final String name, final FirebaseUser currentUser) {
        // Just we need to Update User Name
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();


        currentUser.updateProfile(profileUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //user info updated successfully
                            showMessage("Registration Complete");
                            updateUI();
                        }
                    }
                });

        /*RegisterUser registerUser = new RegisterUser(
                name,
                email,
                phoneNumber,
                bloodGroup,
                district,
                city,
                organization,
                bloodDonationStatus
        );*/

        // add all data to firebase Database
        //addDataToDatabase(registerUser);


    }

    private void updateUI() {
        Intent mainActivity = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    private void addDataToDatabase(RegisterUser registerUser) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("blood_donors");

        // get bloodDonor Unique ID and upload bloodDonor key
        // add bloodDonor Data to firebase Database
        myRef.child(currentUser.getUid()).setValue(registerUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

               // openInfoUpdatedDialog();
                showMessage("Information Added Successfully!");
                //buttonSubmit.setVisibility(View.VISIBLE);
                //progressBarSubmit.setVisibility(View.INVISIBLE);
                //editTextPhoneNumber.getText().clear();
                //editTextAddressDonor.getText().clear();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                // fail to Add data
                showMessage(e.getMessage());
                //buttonSubmit.setVisibility(View.VISIBLE);
                //progressBarSubmit.setVisibility(View.INVISIBLE);
                //editTextPhoneNumber.getText().clear();
                //editTextAddressDonor.getText().clear();

            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private boolean isUserInputValidation(String name, String email, String password1, String password2) {
        if (name.isEmpty()) {
            editTextUserRegisterName.setError("Please Enter Your Name!");
            editTextUserRegisterName.requestFocus();
            return false;
        }

        if (email.isEmpty()) {
            editTextUserRegisterEmail.setError("Please Enter Your Mail!");
            editTextUserRegisterEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextUserRegisterEmail.setError("Enter a Valid Email Address!");
            editTextUserRegisterEmail.requestFocus();
            return false;
        }

        if (password1.length() < 6) {
            editTextUserPassword.setError("Minimum 6 Characters Required!");
            editTextUserPassword.requestFocus();
            return false;
        }

        if (!password1.equals(password2)) {
            editTextUserConfirmPassword.setError("Password didn't match!");
            editTextUserConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void selectDistrictBySearch() {
        //Initialize Dialog
        dialog = new Dialog(RegisterActivity.this);
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_list_item_1, arrayListDistrict);
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
}