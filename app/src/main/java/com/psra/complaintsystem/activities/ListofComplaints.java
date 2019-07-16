package com.psra.complaintsystem.activities;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.psra.complaintsystem.R;
import com.psra.complaintsystem.SqliteDB.DatabaseRooom;
import com.psra.complaintsystem.Utils.AppConfig;
import com.psra.complaintsystem.dbclasses.DbConstants;
import com.psra.complaintsystem.modle.ComplainsList;
import com.psra.complaintsystem.modle.CustomAdapter;
import com.psra.complaintsystem.modle.GetComplaintDatum;
import com.psra.complaintsystem.restapi.RestApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ListofComplaints extends AppCompatActivity {
    private static final String URL = "http://pakwebdeveloper.net/psra/mobile_apis/get_complain_list/";

    SharedPreferences sharedPreferences;
    String userId;
    SwipeRefreshLayout swipeRefreshLayout;
    HashMap<String, String> map = new HashMap<>();
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    List<ComplainsList> data;
    View rel;
    ImageView no_record_layout;
    String distric, complainTypeTitle, statustitle, complaintagainst,compliantId,complaintDetail;
    private Toolbar mTopToolbar;
    // public static View.OnClickListener myOnClickListener;
    int success,show_feedback;
    ProgressDialog dialog;
    DatabaseRooom databaseRooom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.psra.complaintsystem.R.layout.activity_listof_complaints);
        no_record_layout =findViewById(R.id.seize_history_img);
        databaseRooom = Room.databaseBuilder(getApplicationContext(),
                DatabaseRooom.class, DbConstants.DB_NAME)
                .fallbackToDestructiveMigration()
                .build();

        swipeRefreshLayout=findViewById(com.psra.complaintsystem.R.id.swiprc);
        rel = findViewById(com.psra.complaintsystem.R.id.rel);
        mTopToolbar = findViewById(com.psra.complaintsystem.R.id.my_toolbar);
        mTopToolbar.setTitle("List of Complaints");
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTopToolbar.setNavigationOnClickListener(arrow -> onBackPressed());
        data = new ArrayList<ComplainsList>();
        sharedPreferences =getApplicationContext().getSharedPreferences("loginData",Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "No data");/// to be fixed
        map.put("userId", userId);
        //myOnClickListener = new MyOnClickListener(this);
        Log.e("listofcomplaint_userId", "onCreate: " + userId);
        recyclerView = (RecyclerView) findViewById(com.psra.complaintsystem.R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                swipeRefreshLayout.setEnabled(layoutManager.getChildCount() == 0); // 0 is for first item position
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(isNetworkAvailable()) {
                    
                    getAlldata();
                }
                else
                {
                           new Thread(new Runnable() {
                               @Override
                               public void run() {
                                   data.clear();
                                   data=databaseRooom.daoAccess().getAllComplaints();
                                   Log.e("run: ",databaseRooom.daoAccess().getAllComplaints().toString());
                                   if(data.size() > 0)
                                   {
                                       runOnUiThread(new Runnable() {
                                           @Override
                                           public void run() {
                                               no_record_layout.setVisibility(View.GONE);
                                               adapter = new CustomAdapter(data);
                                               recyclerView.setAdapter(adapter);
                                               swipeRefreshLayout.setRefreshing(false);

                                           }
                                       });

                                   }
                                   else
                                   {


                                       runOnUiThread(new Runnable() {
                                           @Override
                                           public void run() {
                                               no_record_layout.setVisibility(View.GONE);
                                               Toast.makeText(ListofComplaints.this, "no internet", Toast.LENGTH_SHORT).show();
                                               //no internet
                                           }
                                       });

                                       swipeRefreshLayout.setRefreshing(false);
                                   }
                               }
                           }).start();

                                    //////////no internat

                }
            }
        });

                if(isNetworkAvailable())
                {

                    getAlldata();

                }
                else
                {
                    new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    data.clear();
                                    data=databaseRooom.daoAccess().getAllComplaints();
                                    if(data.size() > 0)
                                    {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                adapter = new CustomAdapter(data);
                                                recyclerView.setAdapter(adapter);
                                                swipeRefreshLayout.setRefreshing(false);
                                                no_record_layout.setVisibility(View.GONE);
                                            }
                                        });

                                    }
                                    else
                                    {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                no_record_layout.setVisibility(View.GONE);
                                                Toast.makeText(ListofComplaints.this, "no internet", Toast.LENGTH_SHORT).show();
                                                //no internet

                                            }
                                        });
                                        swipeRefreshLayout.setRefreshing(false);

                                    }
                                }
                            }).start();
                            //////////no internat



                }

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                swipeRefreshLayout.setRefreshing(false);
                ComplainsList complainsList = data.get(position);

                statustitle = (String) complainsList.getStatusTitle();
                complainTypeTitle = (String) complainsList.getComplainTypeTitle();
                distric = complainsList.getDistrictTitle();
                complaintagainst = complainsList.getSchoolName();
                compliantId=complainsList.getComplainId();
                show_feedback=complainsList.getShow();
                complaintDetail=complainsList.getComplainDetail();
                Intent intent = new Intent(ListofComplaints.this, DetailListActivity.class);
                intent.putExtra("statustitle", statustitle);
                intent.putExtra("complainTypeTitle", complainTypeTitle);
                intent.putExtra("distric", distric);
                if (complaintagainst== null){
                    intent.putExtra("complaintagainst", complainsList.getSchoolOtherName());
                }else {
                    intent.putExtra("complaintagainst", complaintagainst);

                }

                intent.putExtra("complaintId",compliantId);
                intent.putExtra("show_status",show_feedback);
                intent.putExtra("complaintDetail",complaintDetail);

                startActivity(intent);
                
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));




      /*  for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.versionArray[i],
                    MyData.id_[i],
                    MyData.drawableArray[i]
            ));
        }
*/
    }
  void getAlldata()
  {

      new Thread(new Runnable() {
          @Override
          public void run() {

              swipeRefreshLayout.setRefreshing(true);
              data.clear();
             // databaseRooom.daoAccess().deleteComplaintList(data);
              OkHttpClient okHttpClient = new OkHttpClient.Builder()
                      .connectTimeout(2, TimeUnit.MINUTES)
                      .readTimeout(2, TimeUnit.MINUTES)
                      .writeTimeout(2, TimeUnit.MINUTES)
                      .build();
              Retrofit retrofit = new Retrofit.Builder()
                      .baseUrl(AppConfig.baseUrl).client(okHttpClient)
                      .addConverterFactory(MoshiConverterFactory.create())
                      .build();
              //dialog.show();
              RestApi rest = retrofit.create(RestApi.class);
              Call<List<GetComplaintDatum>> call = rest.getAllComplaints(map);
              call.enqueue(new Callback<List<GetComplaintDatum>>() {
                  @Override
                  public void onResponse(Call<List<GetComplaintDatum>> call, Response<List<GetComplaintDatum>> response) {
                      success = response.body().get(0).getSuccess();
                      Log.e("successcm", "onResponse: "+success);
                      if (response.isSuccessful()) {
                          if (success == 1) {
                              no_record_layout.setVisibility(View.GONE);
                              for (GetComplaintDatum d : response.body()) {
                                  Log.e("AllLIST", "onResponse: " + d.getComplainsList());

                                  for (ComplainsList a : d.getComplainsList()) {

                                      data.add(a);
                                      adapter = new CustomAdapter(data);
                                      recyclerView.setAdapter(adapter);
                                      swipeRefreshLayout.setRefreshing(false);
                               /* distric =a.getDistrictTitle();
                                complainTypeTitle= (String) a.getComplainTypeTitle();
                                statustitle=(String)a.getStatusTitle();*/


                                      Log.e("AllLIST", "onResponse: " + a.getComplainDetail() + "     " + a.getComplainId() + "  " + a.getDated());


                                  }

                                  new Thread(new Runnable() {
                                      @Override
                                      public void run() {
                                          databaseRooom.daoAccess().deleteComplaintList(data);
                                          databaseRooom.daoAccess().insertComplaintsList(data);
                                          // Log.e("run: ",databaseRooom.daoAccess().getAllComplaints().toString());


                                      }
                                  }).start();

                              }

                              Log.e("All", "onResponse: " + response.body());
                              //Toast.makeText(ListofComplaints.this, "data get", Toast.LENGTH_SHORT).show();

                   /* }*/

                          }
                          else if(success==2)
                          {

                              swipeRefreshLayout.setRefreshing(false);
                              Toast.makeText(ListofComplaints.this, "You didn't filed complain yet", Toast.LENGTH_SHORT).show();

                          }

                          else if(success==0)
                          {
                              Toast.makeText(ListofComplaints.this, "Internal server error", Toast.LENGTH_SHORT).show();
                              swipeRefreshLayout.setRefreshing(false);
                          }



                      }
                  }

                  @Override
                  public void onFailure(Call<List<GetComplaintDatum>> call, Throwable t) {
                      Log.e("Message", "" + t.getMessage());
                      swipeRefreshLayout.setRefreshing(false);
                      Toast.makeText(ListofComplaints.this, "Network error", Toast.LENGTH_SHORT).show();
                  }
              });



          }
      }).start();

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

    /*  private static class MyOnClickListener implements View.OnClickListener {

          private final Context context;


          private MyOnClickListener(Context context) {
              this.context = context;
          }

          @Override
          public void onClick(View v) {
             int id= v.getId();

              Toast.makeText(context, "selected   " +id, Toast.LENGTH_SHORT).show();
          }


      }*/



}

