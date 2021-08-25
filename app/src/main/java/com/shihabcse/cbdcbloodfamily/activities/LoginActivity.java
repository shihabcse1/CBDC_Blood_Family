package com.shihabcse.cbdcbloodfamily.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shihabcse.cbdcbloodfamily.R;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private Button buttonCreateAnAccount, buttonSignIn;
    private EditText editTextLoginEmail, editTextLoginPassword;
    private SwitchCompat switchCompat;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // user is already connected so we need to redirect him to homepage
            updateUI();
        }
    }

    private void updateUI() {
        Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        // load language selection
        loadLocale();
        // for changing the language of ActionBar
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle(getResources().getString(R.string.app_name));
        
        mAuth = FirebaseAuth.getInstance();
        switchCompat = findViewById(R.id.switch_language);
        buttonCreateAnAccount = findViewById(R.id.button_create_new_account);
        buttonSignIn = findViewById(R.id.button_sign_in);
        editTextLoginEmail = findViewById(R.id.editTextText_user_email);
        editTextLoginPassword = findViewById(R.id.editTextText_register_user_password);

        progressDialog = new ProgressDialog(this);

        // Select language by switchCompat
        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchCompat.isChecked()) {

                    setLocale("bn");

                } else {

                    setLocale("en");

                }
                recreate();
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String mail = editTextLoginEmail.getText().toString().trim();
                final String password = editTextLoginPassword.getText().toString().trim();

                if (!isUserInputValidation(mail, password)) {
                    //buttonLogIn.setVisibility(View.VISIBLE);
                } else {
                    signIn(mail, password);
                }
                
            }
        });

        buttonCreateAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivity = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerActivity);
            }
        });
    }

    private void loadLocale() {
        SharedPreferences preferences = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        String language = preferences.getString("My_Lang", "");
        setLocale(language);
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        // save data to shared preference
        SharedPreferences.Editor editor = getSharedPreferences("settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    private boolean isUserInputValidation(String mail, String password) {
        if (mail.isEmpty()) {
            editTextLoginEmail.setError("Please Enter Your Mail!");
            editTextLoginEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            editTextLoginEmail.setError("Enter a Valid Email Address!");
            editTextLoginEmail.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            editTextLoginPassword.setError("Minimum 6 Characters Required!");
            editTextLoginPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void signIn(String mail, String password) {

        // progress Dialog
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Please wait ...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            updateUI();
                        } else {
                            progressDialog.dismiss();
                            showMessage(task.getException().getMessage());
                        }
                    }
                });
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}