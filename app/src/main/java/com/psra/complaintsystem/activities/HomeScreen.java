package com.psra.complaintsystem.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;

import com.crashlytics.android.Crashlytics;
import com.psra.complaintsystem.R;

import java.lang.reflect.Field;

import io.fabric.sdk.android.Fabric;


public class HomeScreen extends AppCompatActivity{
ImageView imageView_one,imageView_two,imageView_three,imageView_four,imageView_five,imageView_six;
    boolean chekc;
    private Toolbar mTopToolbar;
    private SharedPreferences loginSharedPreferences;
    Editor loginEditor;
    Button btno,btyes;
    String value;
   android.app.AlertDialog alertDialog;
    private static final String URL = "http://pakwebdeveloper.net/psra/mobile_apis/";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case com.psra.complaintsystem.R.id.navigation_home:
                    fragment = new HomeFragment();
                    mTopToolbar.setTitle("Home");
                    break;
                case com.psra.complaintsystem.R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_dashboard);
                     fragment = new NotificaionFragment();
                    mTopToolbar.setTitle("Notification");
                    break;
                case com.psra.complaintsystem.R.id.navigation_webview:
                    // mTextMessage.setText(R.string.title_notifications);
                    fragment = new WebviewFragment();
                    mTopToolbar.setTitle("Visit Website");
                    //Toast.makeText(getBaseContext(),"click home",Toast.LENGTH_LONG).show();
                    break;
                case com.psra.complaintsystem.R.id.navigation_contactus:
                    // mTextMessage.setText(R.string.title_notifications);
                    fragment = new ContactusFragment();
                    mTopToolbar.setTitle("Contact us");
                    break;
            }
            return loadFragment(fragment);
        }
    };

int success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Fabric.with(this, new Crashlytics());

  /*  imageView_one=findViewById(R.id.imageView);
    imageView_two=findViewById(R.id.imageView2);
    imageView_three=findViewById(R.id.imageView3);
    imageView_four=findViewById(R.id.imageView4);
    imageView_five=findViewById(R.id.imageView6);
    imageView_six=findViewById(R.id.imageView7);
    imageView_one.setOnClickListener(this);
    imageView_two.setOnClickListener(this);
    imageView_three.setOnClickListener(this);
    imageView_four.setOnClickListener(this);
    imageView_five.setOnClickListener(this);
    imageView_six.setOnClickListener(this);*/
        mTopToolbar = findViewById(com.psra.complaintsystem.R.id.my_toolbar);
        mTopToolbar.setTitle("Home");
        setSupportActionBar(mTopToolbar);

        loginSharedPreferences =getApplicationContext().getSharedPreferences("loginData",Context.MODE_PRIVATE);
        chekc=hasNetworkAccess(this);
        if(chekc)
        {
            Toast.makeText(this, "conntected to network", Toast.LENGTH_SHORT).show();
            //chekc=false;
        }
        else {
            displayAlert();
            // Toast.makeText(this, "check your network", Toast.LENGTH_SHORT).show();

        }





        loadFragment(new HomeFragment());
        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(com.psra.complaintsystem.R.id.navigation);
        navigation.setAnimation(null);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        Intent intent=getIntent();
        Bundle bundle =intent.getExtras();
        if(bundle != null)
        {
           value= bundle.getString("key");

           if(value.equalsIgnoreCase("status"))
           {
               loadFragment(new NotificaionFragment());

           }
        }



    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(com.psra.complaintsystem.R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public static class BottomNavigationViewHelper {
        @SuppressLint("RestrictedApi")
        public static void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    //noinspection RestrictedApi
                    item.setShiftingMode(false);
                    item.setAnimation(null);

                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
    }

   /* @Override
    public void onClick(View v) {
        Intent intent;
        int id=v.getId();
        switch (id)
        {
            case R.id.imageView:
            intent =new Intent(HomeScreen.this,NewComplaintscreen.class);
            startActivity(intent);
            break;

            case R.id.imageView2:
                intent =new Intent(HomeScreen.this,ListofComplaints.class);
                startActivity(intent);
                break;
            case R.id.imageView3:
                intent = new Intent(HomeScreen.this,WebviewActivity.class);
                startActivity(intent);
                break;

            case R.id.imageView4:
                intent =new Intent(HomeScreen.this,MapsActivity.class);
                startActivity(intent);
                break;
            case R.id.imageView6:
                intent =new Intent(HomeScreen.this,UserProfile.class);
                startActivity(intent);
                break;
            case R.id.imageView7:
                intent =new Intent(HomeScreen.this,AwarenessScreen.class);
                startActivity(intent);

                break;

        }


    }*/
    public static boolean hasNetworkAccess(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    public void displayAlert()
    {
        new AlertDialog.Builder(this).setMessage("Please Check Your Internet Connection and Try Again")
                .setTitle("Network Error")
                .setCancelable(true)
                .setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton){

                            }
                        })
                .show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.psra.complaintsystem.R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.psra.complaintsystem.R.id.logout) {
            //memory leak point
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LayoutInflater li = LayoutInflater.from(HomeScreen.this);
                    View promptsView = li.inflate(com.psra.complaintsystem.R.layout.logoutdailog, null);

                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(HomeScreen.this);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);
                    // create alert dialog
                    alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                    btno= promptsView.findViewById(com.psra.complaintsystem.R.id.bt_no);
                    btyes=promptsView.findViewById(com.psra.complaintsystem.R.id.bt_yes);


                    btyes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            goToLogin();
                        }
                    });

                    btno.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            alertDialog.dismiss();
                        }
                    });
                }
            });

        }

        else if(id == com.psra.complaintsystem.R.id.update)
        {

            startActivity(new Intent(HomeScreen.this,UserProfile.class));
        }
        else if(id == com.psra.complaintsystem.R.id.about_us)
        {
            startActivity(new Intent(HomeScreen.this,AboutUsAvtivity.class));
        }


        return super.onOptionsItemSelected(item);
    }


    private void goToLogin() {


        loginEditor = loginSharedPreferences.edit();
        loginEditor.clear();
        loginEditor.commit();

        Intent intnt = new Intent(HomeScreen.this, LoginScreen.class);
        // Closing all the Activities
        intnt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intnt);
        finish();

    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to Exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      finish();

                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


}
