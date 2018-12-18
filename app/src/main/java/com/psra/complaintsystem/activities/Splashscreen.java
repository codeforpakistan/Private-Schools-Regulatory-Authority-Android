package com.psra.complaintsystem.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import retrofit2.Retrofit;

import static java.lang.Thread.sleep;

public class Splashscreen extends AppCompatActivity {
    //private FirebaseAnalytics mFirebaseAnalytics;
   /* private static final String URL = "http://pakwebdeveloper.net/psra/mobile_apis/";
    int success;
    HashMap<String, String> map = new HashMap<>();*/
    private static final String DATABASE_NAME ="psra_db";
    private static final String DATABASE_NAME_SEC ="psra2_db";
    private static final String LOGIN_ID = "userId";
    private SharedPreferences loginSharedPreferences;
    String id;
   //DatabaseRooom databaseRooom;

    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.psra.complaintsystem.R.layout.activity_splashscreen);
        Fabric.with(this, new Crashlytics());

        // mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        loginSharedPreferences = getApplicationContext().getSharedPreferences("loginData", Context.MODE_PRIVATE);


/*
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);*/

        id = loginSharedPreferences.getString("loginuserId", "No Data");

         // databaseRooom=Room.databaseBuilder(getApplicationContext(),DatabaseRooom.class,DATABASE_NAME).fallbackToDestructiveMigration().build();

       /*   if (isNetworkAvailable()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(URL)
                            .addConverterFactory(MoshiConverterFactory.create())
                            .build();
                    /////////////////////////////////
                    loadAllComTypeListDistlist();
                }
            });

        }else {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }*/

        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    if (id.equals("No Data")) {
                        Intent intent = new Intent(getApplicationContext(), AfterSplashScreen.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(Splashscreen.this, HomeScreen.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        myThread.start();


    }


  /*  private boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected())
        {
            isAvailable = true;

        }
        return isAvailable;
    }*/
   /* public void loadAllComTypeListDistlist() {
        RestApi rest = retrofit.create(RestApi.class);
        Call<DistList> call = rest.getAllDistrict();
        call.enqueue(new Callback<DistList>() {
            @Override
            public void onResponse(Call<DistList> call, Response<DistList> response) {
                Log.e("AllLIST", "onResponse: " + response.body());
                Log.e("ALLLIST", "onResponse: " + response.body());
                if (response.isSuccessful()) {
                    //if (success == 1) {
                    DistList tecList = response.body();
                    List<ComplainTypesList> compltLists = tecList.getComplainTypesList();
                    List<Districtlist> distlists = tecList.getDistrictlist();
                    Log.e("ALLLIST", "onResponse: " + compltLists + "          " + distlists);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            Districtlist districtlist = new Districtlist();
                            //ComplainTypesList complainTypesList=new ComplainTypesList();
                             databaseRooom.daoAccess().deleteDistrictlist();
                             databaseRooom.daoAccess().deleteComplaintTypelist();
                            for(int i=0; i<compltLists.size();i++){

                                String id_C = compltLists.get(i).getComplainTypeId();
                                String name_C = compltLists.get(i).getComplainTypeTitle();
                               *//* complainTypesList.setComplainTypeId(compltLists.get(i).getComplainTypeId());
                                complainTypesList.setComplainTypeTitle(compltLists.get(i).getComplainTypeTitle());*//*
                                Log.e("id complainTypesList ", id_C);
                                Log.e("name complainTypesList", name_C);
                                Log.e("db complainTypesList", "added " + id_C);
                                Log.e("db complainTypesList", "added " + name_C);
                                try{
                                   databaseRooom.daoAccess().insertMultipleComplainTypesList(compltLists);
                                }catch (Exception e){
                                    Log.e("db", e.toString());
                                }
                            }

                            for (int i = 0; i < distlists.size(); i++) {
                                String id = distlists.get(i).getDistrictId();
                                String name = distlists.get(i).getDistrictTitle();
                                districtlist.setDistrictId(distlists.get(i).getDistrictId());
                                districtlist.setDistrictTitle(distlists.get(i).getDistrictTitle());
                                Log.e("id", id);
                                Log.e("name", name);
                                Log.e("db", "added " + id);
                                Log.e("db", "added " + name);
                                try{
                                    databaseRooom.daoAccess().insertMultipleDist(distlists);
                                }catch (Exception e){
                                    Log.e("db", e.toString());
                                }
                            }
                           *//* try {
                                sleep(2000);

                                if (!id.equals("No Data")) {
                                    Intent intent = new Intent(Splashscreen.this, HomeScreen.class);
                                    startActivity(intent);

                                    finish();
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), AfterSplashScreen.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }*//*


                        }
                    }) .start();
                }
            }
                @Override
                public void onFailure(Call<DistList> call, Throwable t) {
                    Log.e("Message", "" + t.getMessage());
                    //Toast.makeText(HomeScreen.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
    }*/



}