package com.psra.complaintsystem.restapi;

import android.util.Log;

import com.psra.complaintsystem.SqliteDB.ComplainTypesList;
import com.psra.complaintsystem.SqliteDB.DistList;
import com.psra.complaintsystem.SqliteDB.Districtlist;
import com.psra.complaintsystem.SqliteDB.SchoolLIst;
import com.psra.complaintsystem.SqliteDB.Schoolslist;
import com.psra.complaintsystem.SqliteDB.TecList;
import com.psra.complaintsystem.SqliteDB.TehsilsList;
import com.psra.complaintsystem.SqliteDB.UcList;
import com.psra.complaintsystem.SqliteDB.UcList_;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by HP on 8/16/2018.
 */

public class CallsRest {
    private static final String URL = "http://pakwebdeveloper.net/psra/mobile_apis/";
     int success;
    HashMap<String, String> map = new HashMap<>();

    public static void loadAllComTypeListDistlist() {
                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(2, TimeUnit.MINUTES)
                            .readTimeout(2, TimeUnit.MINUTES)
                            .writeTimeout(2, TimeUnit.MINUTES)
                            .build();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(URL)
                            .client(okHttpClient)
                            .addConverterFactory(MoshiConverterFactory.create())
                            .build();

                    RestApi rest = retrofit.create(RestApi.class);
                    Call<DistList> call = rest.getAllDistrict();
                    call.enqueue(new Callback<DistList>() {
                        @Override
                        public void onResponse(Call<DistList> call, Response<DistList> response) {
                            Log.e("AllLIST", "onResponse: " + response.body());

                           // success = response.body().getSuccess();
                            //Log.e("ALLLIST", "onResponse: " + success);
                            Log.e("ALLLIST", "onResponse: " + response.body());
                            if (response.isSuccessful()) {
                                //if (success == 1) {
                                    DistList tecList = response.body();
                                    List<ComplainTypesList> compltLists = tecList.getComplainTypesList();
                                    List<Districtlist> distlists = tecList.getDistrictlist();
                                    Log.e("ALLLIST", "onResponse: " + compltLists + "          " + distlists);

                                   // Toast.makeText(HomeScreen.this, "data get", Toast.LENGTH_SHORT).show();

                                }


                            }


                        @Override
                        public void onFailure(Call<DistList> call, Throwable t) {
                            Log.e("Message", "" + t.getMessage());
                            //Toast.makeText(HomeScreen.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
    public void loadAllListTahsical(final String ids) {

        HashMap<String, String> map = new HashMap<>();
        map.put("district_id", ids);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        RestApi rest = retrofit.create(RestApi.class);
        Call<TecList> call = rest.getAllTihasle(map);
        call.enqueue(new Callback<TecList>() {
            @Override
            public void onResponse(Call<TecList> call, Response<TecList> response) {
                Log.e("AllLIST", "onResponse: " + response.body());

                success = response.body().getSuccess();
                Log.e("ALLLIST", "onResponse: " + success);
                Log.e("ALLLIST", "onResponse: " + response.body());
                if (response.isSuccessful()) {
                    if (success == 1) {
                        TecList tecList = response.body();
                        List<TehsilsList> tehsilsLists = tecList.getTehsilsList();
                        List<Schoolslist> schoolslists = tecList.getSchoolslist();
                        Log.e("ALLLIST", "onResponse: " + tehsilsLists + "          " + schoolslists);

                        // Toast.makeText(HomeScreen.this, "data get", Toast.LENGTH_SHORT).show();

                    }


                }
            }

            @Override
            public void onFailure(Call<TecList> call, Throwable t) {
                Log.e("Message", "" + t.getMessage());
                //Toast.makeText(HomeScreen.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public  void loadAllListUc(final String ids) {
        map.put("tehsil_id", ids);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        RestApi rest = retrofit.create(RestApi.class);
        Call<UcList> call = rest.getAllUc(map);
        call.enqueue(new Callback<UcList>() {
            @Override
            public void onResponse(Call<UcList> call, Response<UcList> response) {
                Log.e("AllLIST", "onResponse: " + response.body());

                success = response.body().getSuccess();
                Log.e("ALLLIST", "onResponse: " + success);
                Log.e("ALLLIST", "onResponse: " + response.body());
                if (response.isSuccessful()) {
                    if (success == 1) {
                        UcList ucList = response.body();
                        List<UcList_> ucList_Lists = ucList.getUcList();
                        List<Schoolslist> schoolslists = ucList.getSchoolslist();
                        Log.e("ALLLIST", "onResponse: " + ucList_Lists + "     value     " + schoolslists);

                        // Toast.makeText(HomeScreen.this, "data get", Toast.LENGTH_SHORT).show();

                    }


                }
            }

            @Override
            public void onFailure(Call<UcList> call, Throwable t) {
                Log.e("Message", "" + t.getMessage());
                //Toast.makeText(HomeScreen.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public  void loadAllSchools() {
        map.put("uc_id", "1");
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        RestApi rest = retrofit.create(RestApi.class);
        Call<SchoolLIst> call = rest.getAllSchool(map);
        call.enqueue(new Callback<SchoolLIst>() {
            @Override
            public void onResponse(Call<SchoolLIst> call, Response<SchoolLIst> response) {
                Log.e("AllLIST", "onResponse: " + response.body());

                //success = response.body().getSuccess();
                Log.e("ALLLIST", "onResponse: " + success);
                Log.e("ALLLIST", "onResponse: " + response.body());
                if (response.isSuccessful()) {
                    //if (success == 1) {
                    SchoolLIst schoolist = response.body();
                        List<UcList> ucList_Lists = schoolist.getUcList();
                        List<TehsilsList> tehsilList=schoolist.getTehsilsList();
                        List<Schoolslist> schoolslists = schoolist.getSchoolslist();
                        Log.e("ALLLIST", "onResponse: " + ucList_Lists + "     value  of schools    " + schoolslists);

                        // Toast.makeText(HomeScreen.this, "data get", Toast.LENGTH_SHORT).show();

                   // }


                }
            }

            @Override
            public void onFailure(Call<SchoolLIst> call, Throwable t) {
                Log.e("Message", "" + t.getMessage());
                //Toast.makeText(HomeScreen.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }
    }


