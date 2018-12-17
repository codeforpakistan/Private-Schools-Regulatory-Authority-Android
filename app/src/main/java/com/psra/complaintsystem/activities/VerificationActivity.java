package com.psra.complaintsystem.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.psra.complaintsystem.R;
import com.psra.complaintsystem.Utils.AppConfig;
import com.psra.complaintsystem.modle.VerificationClas;
import com.psra.complaintsystem.restapi.RestApi;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class VerificationActivity extends AppCompatActivity {
EditText et_verification,ed_two,ed_three,ed_four;
String finaldata;
    private static final String URL="http://pakwebdeveloper.net/psra/mobile_apis/";
    String userTitle,userPassword,userEmail,cnic,contactNumber,district,address,gender;
    HashMap<String, String> map = new HashMap<>();
    String userId;
    Snackbar snackbar;
    RelativeLayout relativeLayout;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.psra.complaintsystem.R.layout.activity_verification);
        relativeLayout=findViewById(R.id.layout_base);
        et_verification=findViewById(com.psra.complaintsystem.R.id.et_verif);
        ed_two=findViewById(com.psra.complaintsystem.R.id.edt_txt2);
        ed_three=findViewById(com.psra.complaintsystem.R.id.edt_txt3);
        ed_four=findViewById(com.psra.complaintsystem.R.id.edt_txt4);
        sharedPreferences = getApplicationContext().getSharedPreferences("loginData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

                 //Intent intent=getIntent();

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }



            userId  = extras.getString("UserId");
            userTitle  = extras.getString("userTitle");
            userPassword  = extras.getString("userPassword");
            userEmail  = extras.getString("userEmail");
            cnic  = extras.getString("cnic");
            contactNumber  = extras.getString("contactNumber");
            district  = extras.getString("district");
            address  = extras.getString("address");
            gender  = extras.getString("gender");
            Log.e("id_uder","onCreate: "+userId);
            Log.e("userTitle","onCreate: "+userTitle);






        et_verification.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                ed_two.requestFocus();
            }
        });

        ed_two.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                ed_three.requestFocus();
            }
        });

        ed_three.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                ed_four.requestFocus();
            }
        });








    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void doWork(View view) {
       postData();
    }

    public void againEnteremail(View view) {
        Intent intent=new Intent(VerificationActivity.this,UserRegisterscreen.class);

        startActivity(intent);
        finish();
    }
/////////////////////////////////////////////////////////////////////////////////////////
    public void verificationAgainsend(View view) {


    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            postData();
        }
    });



    }



    void  postData()
    {
        String data= et_verification.getText().toString();
        String datatwo= ed_two.getText().toString();
        String datathree= ed_three.getText().toString();
        String datafour= ed_four.getText().toString();

        finaldata=data+datatwo+datathree+datafour;
        map.put("user_id", String.valueOf(userId));
        map.put("varification_code",finaldata);
        Log.e("post data",map.toString());


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
        Call<List<VerificationClas>> call =  rest.verifieCode(map);
        call.enqueue(new Callback<List<VerificationClas>>() {
            @Override
            public void onResponse(Call<List<VerificationClas>> call, Response<List<VerificationClas>> response) {
                int success = response.body().get(0).getSuccess();
                Log.e("Response", response.body().toString());

                if(response.isSuccessful())
                {

                    if(success == 0)
                    {
                        snackbar = Snackbar
                                .make(relativeLayout, response.body().get(0).getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    }
                   else if(success == 1) {
                        Toast.makeText(VerificationActivity.this, "User Verified", Toast.LENGTH_SHORT).show();
                        editor.putString("userTitle", userTitle);
                        editor.putString("userPassword", userPassword);
                        editor.putString("userEmail", userEmail);
                        editor.putString("cnic", cnic);
                        editor.putString("contactNumber", contactNumber);
                        editor.putString("district", district);
                        editor.putString("address", address);
                        editor.putString("gender", gender);
                        editor.putString("userId", userId);
                        //editor.putString("token_key", api_token);
                        editor.commit();

                        Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }


                }
            }

            @Override
            public void onFailure(Call<List<VerificationClas>> call, Throwable t) {
                Log.e("Message", ""+t.getMessage());
                Toast.makeText(VerificationActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
