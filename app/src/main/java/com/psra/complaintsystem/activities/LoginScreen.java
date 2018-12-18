package com.psra.complaintsystem.activities;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.iid.FirebaseInstanceId;

import com.psra.complaintsystem.Utils.AppConfig;
import com.psra.complaintsystem.modle.DemoLogin;
import com.psra.complaintsystem.modle.ForgetPassword;
import com.psra.complaintsystem.modle.UserInfoList;
import com.psra.complaintsystem.restapi.RestApi;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


import io.fabric.sdk.android.Fabric;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class LoginScreen extends AppCompatActivity {
    private static final String URL = "http://pakwebdeveloper.net/psra/mobile_apis/";
    private static final String URLD="http://pakwebdeveloper.net/psra/mobile_apis/";
    String eddata;
   /* SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;*/
    HashMap<String, String> map = new HashMap<>();
    EditText forget_password;
    HashMap<String, String> mapdata = new HashMap<>();
    TextView textView,forgetpass_tv;
    EditText ed_cnic, ed_pass;
    String data_pass, data_cnic,data_api;
    Button button,sub_bt;
    String u_id;
    android.app.AlertDialog alertDialog;
   boolean chekc;
    private static final String LOGIN_ID = "userId";
    private CheckBox mCheckBox;
    int success;
    private SharedPreferences loginSharedPreferences;
    private SharedPreferences.Editor loginEditor;
    AwesomeProgressDialog mAwesomeProgressDialog;
    String api_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.psra.complaintsystem.R.layout.activity_login_screen);
        Fabric.with(this, new Crashlytics());
        ///////////////////////////////////////save to sharepriflance////////////////////////////////////////////////////////
        //api_token= FirebaseInstanceId.getInstance().getToken();
        loginSharedPreferences=getApplicationContext().getSharedPreferences("loginData",Context.MODE_PRIVATE);

        api_token=loginSharedPreferences.getString("token_key","No Data");
        //Log.e("loginapitoken", api_token);
        if(api_token.equals("No Data")) {
            api_token  = FirebaseInstanceId.getInstance().getToken();
            Log.e("api_token", api_token);
        }

        mCheckBox = findViewById(com.psra.complaintsystem.R.id.remember_ch);
        mAwesomeProgressDialog = new AwesomeProgressDialog(this);
        button = findViewById(com.psra.complaintsystem.R.id.btn_submit);
        textView = findViewById(com.psra.complaintsystem.R.id.tv_new_user_registere_here);
    /*    sharedPreferences=getApplicationContext().getSharedPreferences("loginData", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        api_token=sharedPreferences.getString("token_key","No Data");
        Log.e("api_token", api_token);*/



        forgetpass_tv=findViewById(com.psra.complaintsystem.R.id.forgetpass);
        ed_cnic = findViewById(com.psra.complaintsystem.R.id.et_cnic_login);
        ed_pass = findViewById(com.psra.complaintsystem.R.id.et_pass_login);

        chekc=hasNetworkAccess();
        if(chekc)
        {
            //Toast.makeText(this, "conntected to network", Toast.LENGTH_SHORT).show();

        }
        else {
            displayAlert();
           // Toast.makeText(this, "check your network", Toast.LENGTH_SHORT).show();

        }


        //loginSharedPreferences = getApplicationContext().getSharedPreferences("loginData", Context.MODE_PRIVATE);



        ed_cnic.addTextChangedListener(new TextWatcher()  {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                data_cnic = ed_cnic.getText().toString();

                if (data_cnic.length() < 13) {
                    ed_cnic.setTextColor(Color.RED);
                    ed_cnic.setError("Enter 13 digits");

                } else {
                    ed_cnic.setTextColor(Color.BLACK);
                }
            }
        });

    /*    String id = loginSharedPreferences.getString(LOGIN_ID, "No Data");


        if (!id.equals("No Data")) {
            Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
            startActivity(intent);
            finish();
        }*/

        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startActivity(new Intent(LoginScreen.this, UserRegisterscreen.class));

                return false;
            }
        });

        forgetpass_tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //startActivity(new Intent(LoginScreen.this, ForgetPassworld.class));

         dailogBox();
                return false;
            }
        });

    }

    public void LoginSubmit(View view) {

        data_cnic = ed_cnic.getText().toString();
        data_pass = ed_pass.getText().toString();
        if (TextUtils.isEmpty(data_cnic)) {
            ed_cnic.setError("Enter CNIC");
            ed_cnic.requestFocus();
        } else if (TextUtils.isEmpty(data_pass)) {
            ed_pass.setError("Enter Password");
            ed_pass.requestFocus();

        } else {
            if (hasNetworkAccess()) {

                map.put("cnic", data_cnic);
                map.put("userPassword", data_pass);
                map.put("token_key",api_token);
                Log.e("map", map.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        postdata();
                        mAwesomeProgressDialog.setTitle("Authenticating")
                                .setMessage("Please Wait")
                                .setColoredCircle(com.psra.complaintsystem.R.color.colorPrimaryDark)
                                .setDialogIconAndColor(com.psra.complaintsystem.R.drawable.ic_dialog_info, com.psra.complaintsystem.R.color.white)
                                .setCancelable(false)
                                .show();

                    }
                });


            } else {

                displayAlert();

            }
        }

    }


    public boolean hasNetworkAccess() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected())
        {
            isAvailable = true;

        }
        return isAvailable;

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
    public void postdata(){

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
        Call<DemoLogin> call = rest.testLogin(map);
        call.enqueue(new Callback<DemoLogin>() {
            @Override
            public void onResponse(Call<DemoLogin> call, Response<DemoLogin> response) {
                success = response.body().getSuccess();
                if (success == 1) {
                    Log.e("data", response.body().toString());
                    mAwesomeProgressDialog.hide();
                    List<UserInfoList> l = response.body().getUserInfoList();
                    for (UserInfoList s : l) {
                        loginEditor=loginSharedPreferences.edit();
                        loginEditor.putString("userTitle", s.getUserTitle());
                        loginEditor.putString("userPassword", s.getUserPassword());
                        loginEditor.putString("userEmail", s.getUserEmail());
                        loginEditor.putString("cnic", s.getCnic());
                        loginEditor.putString("contactNumber", s.getContactNumber());
                        loginEditor.putString("district", s.getDistrictTitle());
                        loginEditor.putString("address", s.getAddress());
                        loginEditor.putString("gender", s.getGender());
                        loginEditor.putString("userId", s.getUserId());
                        //editor.putString("token_key", api_token);
                        loginEditor.commit();


                        Log.e("inputdata", s.getUserTitle());
                        u_id = s.getUserId();
                        Log.e("inputdata", s.getUserTitle());

                            Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                            startActivity(intent);
                            finish();


                        if (mCheckBox.isChecked()) {
                            loginEditor = loginSharedPreferences.edit();
                            loginEditor.putString("loginuserId", u_id);
                            Log.e("loginscreenuser_id", "dosomething: " + u_id);
                            loginEditor.commit();
                        }
                    }
                } else if (success == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AwesomeErrorDialog(LoginScreen.this)
                                    .setTitle("Whoops")
                                    .setMessage("Cnic or Password is incorrect")
                                    .setColoredCircle(com.psra.complaintsystem.R.color.dialogErrorBackgroundColor)
                                    .setDialogIconAndColor(com.psra.complaintsystem.R.drawable.ic_dialog_error, com.psra.complaintsystem.R.color.white)
                                    .setCancelable(false)

                                    .setButtonBackgroundColor(com.psra.complaintsystem.R.color.dialogErrorBackgroundColor)
                                    .setButtonText("Try Again")
                                    .setErrorButtonClick(new Closure() {
                                        @Override
                                        public void exec() {
                                            // click
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {


                                                }
                                            });
                                        }
                                    })
                                    .show();
                        }
                    });
                    mAwesomeProgressDialog.hide();
                }
                else if (success == 2) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AwesomeErrorDialog(LoginScreen.this)
                                    .setTitle("Whoops")
                                    .setMessage("Cnic or Password is incorrect")
                                    .setColoredCircle(com.psra.complaintsystem.R.color.dialogErrorBackgroundColor)
                                    .setDialogIconAndColor(com.psra.complaintsystem.R.drawable.ic_dialog_error, com.psra.complaintsystem.R.color.white)
                                    .setCancelable(false)

                                    .setButtonBackgroundColor(com.psra.complaintsystem.R.color.dialogErrorBackgroundColor)
                                    .setButtonText("Try Again")
                                    .setErrorButtonClick(new Closure() {
                                        @Override
                                        public void exec() {
                                            // click
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {


                                                }
                                            });
                                        }
                                    })
                                    .show();
                        }
                    });
                    mAwesomeProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(Call<DemoLogin> call, Throwable t) {
                Log.e("error", "onFailure: " + t.getMessage());
                // Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_SHORT).show();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AwesomeErrorDialog(LoginScreen.this)
                                .setTitle("WhOops")
                                .setMessage("Some Thing Went Wrong")
                                .setColoredCircle(com.psra.complaintsystem.R.color.dialogErrorBackgroundColor)
                                .setDialogIconAndColor(com.psra.complaintsystem.R.drawable.ic_dialog_error, com.psra.complaintsystem.R.color.white)
                                .setCancelable(false)

                                .setButtonBackgroundColor(com.psra.complaintsystem.R.color.dialogErrorBackgroundColor)
                                .setButtonText("Try Again")
                                .setErrorButtonClick(new Closure() {
                                    @Override
                                    public void exec() {
                                        // click
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {


                                            }
                                        });
                                    }
                                })
                                .show();
                    }
                });
                mAwesomeProgressDialog.hide();


            }
        });

    }

    void dailogBox()
    {



        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(com.psra.complaintsystem.R.layout.forgetdailog, null);

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        // create alert dialog
        alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

        sub_bt= promptsView.findViewById(com.psra.complaintsystem.R.id.button_fgps);
        forget_password=promptsView.findViewById(com.psra.complaintsystem.R.id.forget);


        sub_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        eddata=forget_password.getText().toString();
                        mapdata.put("cnic",eddata);
                        post();
                        finish();

                    }
                });
            }
        });
    }



    void post(){

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
        Call<List<ForgetPassword>> call =  rest.getPassword(mapdata);
        call.enqueue(new Callback<List<ForgetPassword>>() {
            @Override
            public void onResponse(Call<List<ForgetPassword>> call, Response<List<ForgetPassword>> response) {
                success = response.body().get(0).getSuccess();
                String messageprint=response.body().toString();
                Log.e("Message", "onResponse: "+success );
                Log.e("Message", "onResponse: "+messageprint);

                if (response.isSuccessful()) {
                    if (success == 0) {
                        Log.e("Message", "onResponse: "+response.body().get(0).getMessage());
                    }
                    else if(success == 1)
                    {
                      alertDialog.dismiss();
                    Log.e("Message", "onResponse: "+response.body().get(0).getMessage());
                    }

                    else if (success == 2)
                    {
                        Log.e("Message", "onResponse: "+response.body().get(0).getMessage());
                    }
                }


            }

            @Override
            public void onFailure(Call<List<ForgetPassword>> call, Throwable t) {
                Log.e("Message", ""+t.getMessage());
                Toast.makeText(LoginScreen.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });




    }

}


