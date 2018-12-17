package com.psra.complaintsystem.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences.Editor;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.psra.complaintsystem.R;
import com.psra.complaintsystem.SqliteDB.DistList;
import com.psra.complaintsystem.SqliteDB.Districtlist;
import com.psra.complaintsystem.Utils.AppConfig;
import com.psra.complaintsystem.Utils.ProgressGenerator;
import com.psra.complaintsystem.modle.Registered;
import com.psra.complaintsystem.restapi.RestApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class UserRegisterscreen extends AppCompatActivity implements ProgressGenerator.OnCompleteListener{
    String userId;
    Spinner sp_gernder;
    Snackbar snackbar;
    RelativeLayout relativeLayout;
    List<Districtlist> distlists;
    String distcatId, distcatName;
    EditText ed_name, ed_phone_number, ed_cnic, ed_emaill, ed_password, ed_distid, ed_address, ed_gender, complaint_spinner;
    HashMap<String, String> map = new HashMap<>();
    private static final String[] gender = {"Select Gender", "Male", "Female","Other"};
    private static final String URL = "http://pakwebdeveloper.net/psra/mobile_apis/";
    Button button;
    AllDistCatAdapter allCatAdapter;
    String data_name, data_phonenumber, data_cnic, data_email, data_password, data_distid, data_address, data_gender;
    SharedPreferences sharedPreferences;
    Editor editor;
    String api_token;
    int success;
    List<String> plantsList;
    ProgressGenerator progressGenerator;
    ActionProcessButton bt_submitbt;
    int  intgender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.psra.complaintsystem.R.layout.activity_user_registerscreen);
   /*     api_token=FirebaseInstanceId.getInstance().getToken();
         if(api_token !=null) {
             Log.e("api_token", api_token.toString());
         }*/
        plantsList = new ArrayList<>(Arrays.asList(gender));
        progressGenerator = new ProgressGenerator(this);

        sharedPreferences = getApplicationContext().getSharedPreferences("loginData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        api_token = sharedPreferences.getString("token_key", "No Data");
        if(api_token.equals("No Data")) {
            api_token  = FirebaseInstanceId.getInstance().getToken();
            Log.e("api_token", api_token);
        }
        relativeLayout = findViewById(R.id.base_layout);
        complaint_spinner = findViewById(R.id.complaint_spinner);
        sp_gernder = findViewById(com.psra.complaintsystem.R.id.sp);
        bt_submitbt = (ActionProcessButton) findViewById(R.id.bt_submitbt);
        ed_name = findViewById(com.psra.complaintsystem.R.id.et_name);
        ed_phone_number = findViewById(com.psra.complaintsystem.R.id.et_number);
        ed_cnic = findViewById(com.psra.complaintsystem.R.id.et_cnic_login);
        ed_emaill = findViewById(com.psra.complaintsystem.R.id.et_email);
        ed_address = findViewById(com.psra.complaintsystem.R.id.et_address);
        // ed_gender=findViewById(R.id.et_gender);
        ed_password = findViewById(com.psra.complaintsystem.R.id.et_password);
        button = findViewById(com.psra.complaintsystem.R.id.btn_submit);
        String usesrnaemtest = sharedPreferences.getString("userTitle", "No Data");
        Log.e("nametest", usesrnaemtest);

        loadAllComTypeListDistlist();

        complaint_spinner.setEnabled(false);

        complaint_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAllCategoriesDialog();
            }
        });

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spitem, plantsList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spitem);
        sp_gernder.setAdapter(spinnerArrayAdapter);

        sp_gernder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint

                TextView tv = (TextView) view;
                if (position > 0) {
                    // Notify the selected item text
                   /* Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();*/

                    data_gender = (String) parent.getItemAtPosition(position);
                   intgender = position;
                    tv.setTextColor(Color.BLACK);
                    Log.e("onItemSelected: ",data_gender+""+intgender);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

      /*

        ArrayAdapter<String> adaptedist = new ArrayAdapter<String>(this, R.layout.spitem, gender);

        sp_gernder.setAdapter(adaptedist);

        sp_gernder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if(position == 0)
                {
                    ((TextView)sp_gernder.getSelectedView()).setError("Error message");
                   *//* TextView errorText = (TextView)sp_gernder.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("my actual error text");//changes the selected item text to this*//*

                }
                else
                {
                    data_gender=(String) parent.getItemAtPosition(position);
                }
                Log.v("item", (String) parent.getItemAtPosition(position));
                Log.v("item", String.valueOf(position));


                // Toast.makeText(NewComplaintscreen.this, ""+dist_id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
*/


        ed_cnic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //data_cnic = ed_cnic.getText().toString();

                if (s.length() < 13) {
                    ed_cnic.setTextColor(Color.RED);
                    ed_cnic.setError("Enter 13 digits");

                } else {
                    ed_cnic.setTextColor(Color.BLACK);
                }
            }
        });




        ed_phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                data_phonenumber = ed_phone_number.getText().toString();

                if (data_phonenumber.length() < 11) {
                    ed_phone_number.setTextColor(Color.RED);
                    ed_phone_number.setError("Enter 11 digits");

                } else {
                    ed_phone_number.setTextColor(Color.BLACK);
                }
            }
        });


    }

    public void adduser(View view) {


       // progressGenerator.start(bt_submitbt);
       // bt_submitbt.setEnabled(false);
        data_name = ed_name.getText().toString();
        data_phonenumber = ed_phone_number.getText().toString();
        data_cnic = ed_cnic.getText().toString();
        data_email = ed_emaill.getText().toString();
        //data_gender=ed_gender.getText().toString();
        data_address = ed_address.getText().toString();
        data_password = ed_password.getText().toString();


        if (TextUtils.isEmpty(data_name)) {
            ed_name.setError("Enter Name");
            ed_name.requestFocus();
        } else if (TextUtils.isEmpty(data_phonenumber)) {

            ed_phone_number.setError("Enter Cell");
            ed_phone_number.requestFocus();
        }
        else if (TextUtils.isEmpty(data_cnic) && data_cnic.length() < 13) {
            ed_cnic.setError("Enter CNIC");
            ed_cnic.requestFocus();
        }else if (TextUtils.isEmpty(data_email)) {

            ed_emaill.setError("Enter Email");
            ed_emaill.requestFocus();
        } else if (TextUtils.isEmpty(data_address)) {

            ed_address.setError("Enter Address");
            ed_address.requestFocus();
        }
        else if (TextUtils.isEmpty(data_password)) {

            ed_password.setError("Enter Password");
            ed_password.requestFocus();
        }
        else if(TextUtils.isEmpty(distcatId))
        {
            complaint_spinner.setError("Select District");
            complaint_spinner.requestFocus();
        }
        else
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    progressGenerator.start(bt_submitbt);

                    postData();


                }
            });

        }



    }
    void postData()
    {
        map.put("userTitle", data_name);
        map.put("userPassword", data_password);
        map.put("userEmail", data_email);
        map.put("cnic", data_cnic);
        map.put("contactNumber", data_phonenumber);

        map.put("address", data_address);
        map.put("gender", String.valueOf(intgender));
        map.put("token_key", api_token);
        Log.e("post data", map.toString());




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
        Call<List<Registered>> call = rest.doRegistration(map);
        call.enqueue(new Callback<List<Registered>>() {
            @Override
            public void onResponse(Call<List<Registered>> call, Response<List<Registered>> response) {

                Log.e("dataresponse", response.body().toString());
                  success=response.body().get(0).getSuccess();
                Log.e("onResponse: ",""+success );
                Log.e("onResponse: ",""+response.body().get(0).getMessage() );

                if (response.isSuccessful()) {
                     if(success == 0)
                     {

                         snackbar = Snackbar
                                 .make(relativeLayout, "Kindly fill the registration form completely", Snackbar.LENGTH_LONG);
                         snackbar.show();


                     }
                     else if (success == 1){
                    List<Registered> r = response.body();
                    for (Registered s : r) {

                        Log.e("data", String.valueOf(s.getUserId()));
                        userId = String.valueOf(s.getUserId());


                        Toast.makeText(UserRegisterscreen.this, "User Created", Toast.LENGTH_SHORT).show();
                        Log.e("data", "onResponse: ");


                      /*  editor.putString("userTitle", data_name);
                        editor.putString("userPassword", data_password);
                        editor.putString("userEmail", data_email);
                        editor.putString("cnic", data_cnic);
                        editor.putString("contactNumber", data_phonenumber);
                        editor.putString("district", distcatName);
                        editor.putString("address", data_address);
                        editor.putString("gender", data_gender);
                        editor.putString("userId", userId);
                        //editor.putString("token_key", api_token);
                        editor.commit();*/
                        Log.e("uregisterscreen_userid", "onResponse: " + userId + "    " + data_name + "" + data_password + "" + data_email);
                        bt_submitbt.setEnabled(false);
                        Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
                        intent.putExtra("UserId", userId);
                        intent.putExtra("userPassword", data_password);
                        intent.putExtra("userEmail", data_email);
                        intent.putExtra("cnic", data_cnic);
                        intent.putExtra("contactNumber", data_phonenumber);
                        intent.putExtra("district", distcatName);
                        intent.putExtra("address", distcatName);
                        intent.putExtra("gender", data_gender);
                        intent.putExtra("userTitle", data_name);
                        startActivity(intent);
                        finish();
                    }

                    }
                     else if(success == 2)
                     {

                         snackbar = Snackbar
                                 .make(relativeLayout, "Registration error occured, try again", Snackbar.LENGTH_LONG);
                         snackbar.show();


                     }

                    else if(success == 3)
                     {

                         snackbar = Snackbar
                                 .make(relativeLayout,"cnic is already registered", Snackbar.LENGTH_LONG);
                         snackbar.show();


                     }
                }
            }

            @Override
            public void onFailure(Call<List<Registered>> call, Throwable t) {
                Log.e("Message", "" + t.getMessage());
                Toast.makeText(UserRegisterscreen.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadAllComTypeListDistlist() {
        Retrofit retrofit;
        retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
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
                    distlists = tecList.getDistrictlist();
                    Log.e("ALLLIST", "onResponse: " + distlists);
                    complaint_spinner.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<DistList> call, Throwable t) {
                Log.e("Message", "" + t.getMessage());
                //Toast.makeText(HomeScreen.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showAllCategoriesDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Dialog allCategoriesDialog = new Dialog(UserRegisterscreen.this, com.psra.complaintsystem.R.style.dialog_theme);
                allCategoriesDialog.setCancelable(true);
                allCategoriesDialog.setContentView(com.psra.complaintsystem.R.layout.spinner_dialog);
                TextView dialog_title = allCategoriesDialog.findViewById(com.psra.complaintsystem.R.id.dialog_title);
                //add dynamic title to dialog
                dialog_title.setText("Select District");
                SearchView mSearchView = allCategoriesDialog.findViewById(com.psra.complaintsystem.R.id.search_et);
                mSearchView.setVisibility(View.VISIBLE);
                final ListView list = allCategoriesDialog.findViewById(com.psra.complaintsystem.R.id.seize_cat_list);
                allCatAdapter = new AllDistCatAdapter(UserRegisterscreen.this, distlists);
                list.setAdapter(allCatAdapter);
                allCatAdapter.notifyDataSetChanged();
                list.setTextFilterEnabled(false);
                mSearchView.setIconifiedByDefault(false);
                mSearchView.setSubmitButtonEnabled(false);
                mSearchView.setQueryHint("Search...");
                allCategoriesDialog.show();
                //add text watcher on search edit text
                mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (TextUtils.isEmpty(newText)) {

                            allCatAdapter = new AllDistCatAdapter(UserRegisterscreen.this, distlists);
                            list.setAdapter(allCatAdapter);
                            allCatAdapter.notifyDataSetChanged();

                        } else {
                            Filter filter = allCatAdapter.getFilter();
                            filter.filter(newText);
                        }
                        return true;
                    }
                });

                //list view item click listener
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        allCategoriesDialog.dismiss();
                        List<Districtlist> arrayList = new ArrayList<>();
                        arrayList = allCatAdapter.getList();
                        complaint_spinner.setText("" + arrayList.get(i).getDistrictTitle());
                        distcatId = arrayList.get(i).getDistrictId();
                        distcatName = arrayList.get(i).getDistrictTitle();

                        if (!distcatId.equals("")) {
                            //create part and put in hasmap
                            map.put("district_id", distcatId);

                            //mMap.put("vechicle_make", createPartFromString(catId));
                            Log.e("catId", distcatId);

                        } else {

                        }


                    }
                });


            }
        });
    }

    @Override
    public void onComplete() {

    }
}
