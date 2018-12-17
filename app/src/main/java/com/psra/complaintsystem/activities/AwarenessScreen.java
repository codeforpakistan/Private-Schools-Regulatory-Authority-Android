package com.psra.complaintsystem.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.psra.complaintsystem.restapi.CallsRest;

public class AwarenessScreen extends AppCompatActivity {
       CallsRest callsRest=new CallsRest();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.psra.complaintsystem.R.layout.activity_awareness_screen);

    }
}
