package com.psra.complaintsystem.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.psra.complaintsystem.R;
import com.psra.complaintsystem.Utils.AppConfig;
import com.psra.complaintsystem.modle.FeedbackModule;
import com.psra.complaintsystem.restapi.RestApi;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class DetailListActivity extends AppCompatActivity {
    private static final String URL = "http://pakwebdeveloper.net/psra/mobile_apis/";
  String statustitle,complainTypeTitle,distric,complaintagainst,complaintId;
  TextView tv_statustitle,tv_complainTypeTitle,tv_distric,tv_complaintagainst,tv_complaint_detail;
    private Toolbar mTopToolbar;
    private SharedPreferences loginSharedPreferences;
    SharedPreferences.Editor loginEditor;
    int success;
    HashMap<String, String> map = new HashMap<>();
    Button bt,yes_bt,no_bt,sub_bt;
    ImageView cancle_im;
    AlertDialog alertDialog;
    EditText feed_tv;
    String value,valuefeed,notification,st_data,complaintDetail;
    int show;
    Button bt_feed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.psra.complaintsystem.R.layout.activity_detail_list);
         bt_feed=findViewById(R.id.bt_submitbt);
        tv_statustitle=findViewById(com.psra.complaintsystem.R.id.tv_statustitle);
        tv_complainTypeTitle=findViewById(com.psra.complaintsystem.R.id.tv_complainTypeTitle);
        bt=findViewById(com.psra.complaintsystem.R.id.bt_submitbt);
        tv_complaint_detail=findViewById(R.id.tv_complaintdetail);
        tv_distric=findViewById(com.psra.complaintsystem.R.id.tv_distric);
        tv_complaintagainst=findViewById(com.psra.complaintsystem.R.id.tv_complainagainst);
        mTopToolbar =findViewById(com.psra.complaintsystem.R.id.my_toolbar);
        mTopToolbar.setTitle("Complaints Detail");
        setSupportActionBar(mTopToolbar);
      /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTopToolbar.setNavigationOnClickListener(arrow -> onBackPressed());

        Intent intent=getIntent();
               Bundle bundle =intent.getExtras();
        //notification = bundle.getString("notification");
       // Log.e("detailactivity", "onCreate:"+notification );
               //String d=intent.getStringExtra("statustitle");
               if(bundle != null)
               {

                  statustitle= bundle.getString("statustitle");
                  complainTypeTitle= bundle.getString("complainTypeTitle");
                 distric = bundle.getString("distric");
                 complaintagainst=bundle.getString("complaintagainst");
                   complaintId = bundle.getString("complaintId");
                   show=bundle.getInt("show_status");
                   complaintDetail=bundle.getString("complaintDetail");

                   Log.e("show", String.valueOf(show));
               }

        tv_statustitle.setText(statustitle);
        String[] parts = complaintDetail.split(":");
        String first = parts[0];
        if (first == null){
            tv_complainTypeTitle.setText(complainTypeTitle);
        }else {

            tv_complainTypeTitle.setText(complainTypeTitle + ": "+first);
        }

        tv_distric.setText(distric);
        tv_complaintagainst.setText(complaintagainst);
        tv_complaint_detail.setText(complaintDetail);

            if(show == 1)
            {

                bt_feed.setVisibility(View.VISIBLE);
            }


        Log.e("showdata", "onCreate: "+complaintId);
        Log.e("show", "onCreate: "+show);
    }



    void postData()
    {
        map.put("complain_id",complaintId);
        map.put("agree_or_not_agree",value);
        map.put("details",valuefeed);

        Log.e("mapdata", "postData: "+complaintId +"  "+value+"  "+valuefeed);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        //dialog.show();
        RestApi rest = retrofit.create(RestApi.class);
        Call<List<FeedbackModule>> call = rest.submitFeedback(map);
        call.enqueue(new Callback<List<FeedbackModule>>() {
            @Override
            public void onResponse(Call<List<FeedbackModule>> call, Response<List<FeedbackModule>> response) {
                success = response.body().get(0).getSuccess();
                  Integer message =response.body().get(0).getSuccess();
                Log.e("successOne", "onResponse: "+success +"   "+message);
                if (response.isSuccessful()) {
                  /*  if (success == 1) {*/
                        Log.e("success", "onResponse: one 1 " );
                        Toast.makeText(DetailListActivity.this, "Feedback submited", Toast.LENGTH_SHORT).show();


                       finish();
                    }
                   /* else if(success==0)
                    {
                        Toast.makeText(DetailListActivity.this, "services issue", Toast.LENGTH_SHORT).show();

                    }*/




                }


            @Override
            public void onFailure(Call<List<FeedbackModule>> call, Throwable t) {
                Log.e("Message", "" + t.getMessage());

            }
        });



    }

    public void submiteFeedback(View view) {
       showPictureDialog();
    }
    private void showPictureDialog() {

        Log.e("click", "showPictureDialog: ");
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(com.psra.complaintsystem.R.layout.feedrow, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        // create alert dialog
        alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

       cancle_im= promptsView.findViewById(com.psra.complaintsystem.R.id.cancle_bt);
       yes_bt=promptsView.findViewById(com.psra.complaintsystem.R.id.yes_bt);
       no_bt=promptsView.findViewById(com.psra.complaintsystem.R.id.no_bt);
        feed_tv=promptsView.findViewById(com.psra.complaintsystem.R.id.feed_tv);
        sub_bt=promptsView.findViewById(com.psra.complaintsystem.R.id.submit_bt);
       cancle_im.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               alertDialog.dismiss();
           }
       });


       yes_bt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              boolean ce= v.isFocused();
               value="Yes";

                   yes_bt.setBackgroundColor(Color.parseColor("#049053"));
                  no_bt.setBackgroundColor(Color.parseColor("#ffffff"));

           }
       });




       no_bt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                value="No";
               no_bt.setBackgroundColor(Color.parseColor("#FFEF1B25"));
               yes_bt.setBackgroundColor(Color.parseColor("#ffffff"));
           }
       });

       sub_bt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               runOnUiThread(new Runnable() {

                   @Override
                   public void run() {
                       valuefeed=feed_tv.getText().toString();
                       if(isNetworkAvailable()) {
                           postData();
                       }
                       else
                       {
                           Toast.makeText(DetailListActivity.this, "check internet", Toast.LENGTH_SHORT).show();

                       }
                   }
               });
           }
       });

    }


    public boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;

        }
        return isAvailable;

    }
}
