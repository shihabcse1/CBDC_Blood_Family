package com.shihabcse.cbdcbloodfamily.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Splash Screen
        startActivity(new Intent(this, LoginActivity.class));
        finish();

    }
}