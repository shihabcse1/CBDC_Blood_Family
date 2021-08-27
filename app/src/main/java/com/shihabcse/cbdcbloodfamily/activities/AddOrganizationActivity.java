package com.shihabcse.cbdcbloodfamily.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shihabcse.cbdcbloodfamily.R;
import com.shihabcse.cbdcbloodfamily.models.Organization;
import com.shihabcse.cbdcbloodfamily.models.RegisterUserEdited;

import java.util.ArrayList;

public class AddOrganizationActivity extends AppCompatActivity {

    private EditText editTextOrganizationName, editTextAdminName, editTextAdminContactNumber;
    private Button buttonSubmitAdmin;

    private ProgressDialog progressDialog;

    ImageView profileImage;

    static int PRE_REQ_CODE = 1;
    static int REQUEST_CODE = 1;
    Uri pickedImgUri;

    private TextView textViewSelectDistrict;
    private ArrayList<String> arrayListDistrict;
    private Dialog dialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_organization);

        progressDialog = new ProgressDialog(this);

        //initialize the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();

        editTextOrganizationName = findViewById(R.id.editTextText_organization_name);
        editTextAdminName = findViewById(R.id.editTextText_admin_name);
        editTextAdminContactNumber = findViewById(R.id.editTextText_number_of_admin_contact_number);
        buttonSubmitAdmin = findViewById(R.id.button_submit_admin);
        profileImage = findViewById(R.id.imageViewOrganizationLogo);

        buttonSubmitAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String organizationName = editTextOrganizationName.getText().toString().trim();
                final String organizationAdmin = editTextAdminName.getText().toString().trim();
                final String organizationAdminContactNumber = editTextAdminContactNumber.getText().toString().trim();
                String organizationDistrict = textViewSelectDistrict.getText().toString().trim();

                // checking the input validation first
                if (isUserInputValidation(organizationName, organizationAdmin, organizationAdminContactNumber, organizationDistrict) == false) {

                    buttonSubmitAdmin.setVisibility(View.VISIBLE);

                } else {

                    if (pickedImgUri != null) {
                        addOrganizationInfoWithoutPhoto(organizationName, organizationAdmin, organizationAdminContactNumber, organizationDistrict);
                    } else {
                        addOrganizationInfoWithoutPhoto(organizationName, organizationAdmin, organizationAdminContactNumber, organizationDistrict);
                    }

                }

            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check the Platform(sdk)-->Check the Permission-->Explain the permission-->Request the permission-->Handle the Response

                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestPermission();
                } else {
                    openGallery();
                }

            }
        });

        textViewSelectDistrict = findViewById(R.id.textView_select_district);

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


    }

    private void addOrganizationInfoWithoutPhoto(String organizationName, String organizationAdmin, String organizationAdminContactNumber, String organizationDistrict) {
        Organization organization = new Organization(
                organizationName,
                organizationAdmin,
                organizationAdminContactNumber,
                organizationDistrict
        );

        // add all data to firebase Database
        addDataToDatabase(organization);
    }

    private void addDataToDatabase(Organization organization) {

        // progress Dialog
        progressDialog.setTitle("তথ্য যুক্ত হচ্ছে...");
        progressDialog.setMessage("অনুগ্রহপূর্বক অপেক্ষা করুন");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("organization_list");
        // get bloodDonor Unique ID and upload bloodDonor key
        // add bloodDonor Data to firebase Database

        String key = organization.getOrganizationName();

        myRef.child(key).setValue(organization).addOnSuccessListener(new OnSuccessListener<Void>() {
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

                // fail to Add data
                showMessage(e.getMessage());
                editTextOrganizationName.getText().clear();
                editTextAdminName.getText().clear();
                editTextAdminContactNumber.getText().clear();

            }
        });
    }

    private void updateUI() {
        Intent mainActivity = new Intent(AddOrganizationActivity.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(AddOrganizationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(AddOrganizationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(this, "Please accept the required permission", Toast.LENGTH_SHORT).show();

            } else {

                ActivityCompat.requestPermissions(AddOrganizationActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PRE_REQ_CODE);
            }

        } else {
            openGallery();
        }
    }

    private void openGallery() {
        // Open gallery intend and wait for user pick and Image
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUEST_CODE);
    }


    // Override method for Gallery intend for check request

    //TODO Remember NullableS
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            pickedImgUri = data.getData();
            profileImage.setImageURI(pickedImgUri);
        }

    }

    private boolean isUserInputValidation(String organizationName, String organizationAdmin, String organizationAdminContactNumber, String organizationDistrict) {

        if (organizationName.isEmpty()) {
            editTextOrganizationName.setError("অনুগ্রহ করে প্রতিষ্ঠানের নাম লিখুন!");
            editTextOrganizationName.requestFocus();
            return false;
        }
        if (organizationAdmin.isEmpty()) {
            editTextAdminName.setError("অ্যাডমিনের নাম লিখুন!");
            editTextAdminName.requestFocus();
            return false;
        }
        if (organizationAdminContactNumber.isEmpty()) {
            editTextAdminContactNumber.setError("দয়া করে ফোন নম্বর লিখুন!");
            editTextAdminContactNumber.requestFocus();
            return false;
        }
        if (organizationDistrict.equals("Select district") || organizationDistrict.equals("জেলা নির্বাচন")) {
            Toast.makeText(this, "জেলা নির্বাচন করুন", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void selectDistrictBySearch() {
        //Initialize Dialog
        dialog = new Dialog(AddOrganizationActivity.this);
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddOrganizationActivity.this, android.R.layout.simple_list_item_1, arrayListDistrict);
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