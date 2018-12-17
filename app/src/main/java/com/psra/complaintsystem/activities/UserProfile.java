package com.psra.complaintsystem.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.psra.complaintsystem.R;
import com.psra.complaintsystem.Utils.AppConfig;
import com.psra.complaintsystem.modle.UpdataDemo;
import com.psra.complaintsystem.restapi.RestApi;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.content.SharedPreferences.Editor;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class UserProfile extends AppCompatActivity {
    private static final String URL="http://pakwebdeveloper.net/psra/mobile_apis/";
    SharedPreferences sharedPreferences;
    private Toolbar mTopToolbar;
    String userTitle,userPassword,userEmail,cnic,contactNumber,district,address,gender;
    String userId;
    HashMap<String, String> map = new HashMap<>();
    EditText ed_name,ed_phone_number,ed_cnic,ed_emaill,ed_password,ed_distid,ed_address,ed_gender;
    String up_name,up_password,up_email,up_contactnumber,up_dist,up_address,up_gender;
    int success;
    Editor editor;
    Snackbar snackbar;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.psra.complaintsystem.R.layout.activity_user_profile);
        sharedPreferences=getApplicationContext().getSharedPreferences("loginData", Context.MODE_PRIVATE);
        ed_name=findViewById(com.psra.complaintsystem.R.id.et_name);
        ed_phone_number=findViewById(com.psra.complaintsystem.R.id.et_number);
        relativeLayout=findViewById(R.id.base_layout);
        mTopToolbar = findViewById(com.psra.complaintsystem.R.id.my_toolbar);
        mTopToolbar.setTitle("Update Profile");
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTopToolbar.setNavigationOnClickListener(arrow -> onBackPressed());
       // ed_emaill=findViewById(R.id.et_email);
        ed_address=findViewById(com.psra.complaintsystem.R.id.et_address);
       // ed_gender=findViewById(R.id.et_gender);
        ed_password=findViewById(com.psra.complaintsystem.R.id.et_password);
        //ed_distid=findViewById(R.id.et_dist);
        userTitle=sharedPreferences.getString("userTitle","No Data");
        userId=sharedPreferences.getString("userId","No Data");
        Log.e("usertitle",userTitle +"  "+userId);

        //dataShare();
        if(!userId.equals("No Data")) {
            dataShare();

        }





    }

    void dataShare()
    {

        //userTitle= sharedPreferences.getString("userTitle","No Data");
        userPassword=sharedPreferences.getString("userPassword","No Data");
       // userEmail=sharedPreferences.getString("userEmail","No Data");
        //cnic= sharedPreferences.getString("cnic","No Data");

        contactNumber=sharedPreferences.getString("contactNumber","No Data");
       // contactNumber.replaceAll("[\\s\\-()]","");
        //district=sharedPreferences.getString("district","No Data");
        String re=contactNumber.replaceAll("[\\s\\-()]","");
        Log.e("dataShare: ",re);

        address=sharedPreferences.getString("address","No Data");
        //gender= sharedPreferences.getString("gender","No Data");
       // userId=sharedPreferences.getString("userId","No Data");
        ed_name.setText(userTitle);
        ed_phone_number.setText(re);
       // ed_emaill.setText(userEmail);
        ed_address.setText(address);
        //ed_gender.setText(gender);
        ed_password.setText(userPassword);
       // ed_distid.setText(district);






    }


    public void upData(View view) {

        up_name=ed_name.getText().toString();
        up_contactnumber=ed_phone_number.getText().toString();
       // up_email=ed_emaill.getText().toString();
        up_address=ed_address.getText().toString();
       // up_gender=ed_gender.getText().toString();
        up_password=ed_password.getText().toString();
       // up_dist=ed_distid.getText().toString();
        Log.e("share_data", "getData: "+up_name+" "+up_contactnumber);

        map.put("userId", userId);
        map.put("userTitle", up_name);
        map.put("userPassword", up_password);
       // map.put("userEmail", up_email);
        map.put("contactNumber", up_contactnumber);
      //  map.put("district_id", up_dist);
        map.put("address", up_address);
        //map.put("gender", up_gender);


        Log.e("share_data", "getData: "+map.toString());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.baseUrl).client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        RestApi rest = retrofit.create(RestApi.class);
        Call<List<UpdataDemo>> call =  rest.doUpdata(map);
        call.enqueue(new Callback<List<UpdataDemo>>() {
            @Override
            public void onResponse(Call<List<UpdataDemo>> call, Response<List<UpdataDemo>> response) {
                Log.e("updata_Response", response.body().toString());
                 success = response.body().get(0).getSuccess();
                if(response.isSuccessful()) {


                  if(success == 1)
                    {

                        Toast.makeText(UserProfile.this, "Data updata", Toast.LENGTH_SHORT).show();

                         editor=sharedPreferences.edit();
                        editor.putString("userTitle",up_name);
                        editor.putString("userPassword",up_password);
                       // editor.putString("userEmail",up_email);
                        editor.putString("contactNumber",up_contactnumber);
                        //editor.putString("district",up_dist);
                        editor.putString("address",up_address);
                        //editor.putString("gender",up_gender);
                        editor.putString("userId",userId);
                        editor.commit();

                        snackbar = Snackbar
                                .make(relativeLayout, response.body().get(0).getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();


                    }

                }


            }

            @Override
            public void onFailure(Call<List<UpdataDemo>> call, Throwable t) {
                Log.e("Message", ""+t.getMessage());
                Toast.makeText(UserProfile.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    protected void onResume() {
        super.onResume();

    }

    }
