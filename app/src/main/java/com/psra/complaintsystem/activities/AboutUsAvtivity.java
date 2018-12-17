package com.psra.complaintsystem.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AboutUsAvtivity extends AppCompatActivity {
    private Toolbar mTopToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.psra.complaintsystem.R.layout.activity_about_us_avtivity);
        mTopToolbar = findViewById(com.psra.complaintsystem.R.id.my_toolbar);
        mTopToolbar.setTitle("About Us");
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTopToolbar.setNavigationOnClickListener(arrow -> onBackPressed());
    }

    public void sendnextActivity(View view) {


        startActivity(new Intent(AboutUsAvtivity.this,ContactusScreen.class));}
}
