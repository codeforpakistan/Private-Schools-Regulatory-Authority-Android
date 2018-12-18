package com.psra.complaintsystem.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AfterSplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.psra.complaintsystem.R.layout.activity_after_splash_screen);
    }

    public void dosomthing(View view) {

        startActivity(new Intent(AfterSplashScreen.this, UserRegisterscreen.class));


    }

    public void godosomthing(View view) {


        Intent intent = new Intent(AfterSplashScreen.this, LoginScreen.class);
        startActivity(intent);


    }
}
