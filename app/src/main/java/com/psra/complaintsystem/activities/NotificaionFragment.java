package com.psra.complaintsystem.activities;


import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.psra.complaintsystem.R;
import com.psra.complaintsystem.SqliteDB.DatabaseRooom;
import com.psra.complaintsystem.SqliteDB.NotificationModule;
import com.psra.complaintsystem.Utils.RecyclerItemTouchHelper;
import com.psra.complaintsystem.Utils.SwipeHelper;
import com.psra.complaintsystem.dbclasses.DbConstants;
import com.psra.complaintsystem.modle.ComplainData;
import com.psra.complaintsystem.modle.ComplainsList;
import com.psra.complaintsystem.modle.CustomAdapter;
import com.psra.complaintsystem.modle.OfflineAdpter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificaionFragment extends Fragment implements
        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    HashMap<String, String> map = new HashMap<>();
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    List<ComplainData> data;
    DatabaseRooom databaseRooom;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView no_record_layout;
    String user_id;
    int notifi_user_id;
    SharedPreferences loginSharedPreferences;

    RecyclerItemTouchHelper recyclerItemTouchHelper;
    public NotificaionFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(com.psra.complaintsystem.R.layout.fragment_notificaion, container, false);
        loginSharedPreferences = getActivity().getSharedPreferences("loginData",Context.MODE_PRIVATE);
        user_id = loginSharedPreferences.getString("userId", "No Data");
        no_record_layout =view.findViewById(R.id.seize_history_img);
        databaseRooom = Room.databaseBuilder(getActivity(),
                DatabaseRooom.class, DbConstants.DB_NAME).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        swipeRefreshLayout = view.findViewById(com.psra.complaintsystem.R.id.swiprc);
        data = new ArrayList<ComplainData>();

        recyclerView = view.findViewById(com.psra.complaintsystem.R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //no_record_layout.setVisibility(View.GONE);

                data = databaseRooom.daoAccess().getAllNoficitions();
        Log.e("db data", data.toString() );
        if (data.size() > 0){
            String notifi_user_id = String.valueOf(data.get(0).getUser_id());
            Log.e("notifi_user_id", notifi_user_id );
            Log.e("user_id", user_id );
            if (user_id.equals(notifi_user_id)){
                no_record_layout.setVisibility(View.GONE);
                adapter = new OfflineAdpter(data);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        }






        //adapter.setRefreshing(false);
        // Setup onItemTouchHandler to enable drag and drop , swipe left or right
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0,  ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
// attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);





        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                          data.clear();
                           data=databaseRooom.daoAccess().getAllNoficitions();
                if(data.size() > 0) {
                    no_record_layout.setVisibility(View.GONE);
                    adapter = new OfflineAdpter(data);
                    recyclerView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                }

                        }

        });



/*
        SwipeHelper swipeHelper = new SwipeHelper(getActivity(), recyclerView) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton("Delete", 0, Color.parseColor("#FF3C30"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Log.e( "onClick: ",""+pos );
                                // TODO:


                            }
                        }
                ));

            }
        };*/

       return view;
    }

    public boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;

        }
        return isAvailable;

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        int id = data.get(viewHolder.getAdapterPosition()).getNotificationId();
        //Log.e("onSwiped: ",data.get(0).getNotificationId().toString());
        Log.e("onSwiped: ", String.valueOf(id));
        //problem is here check it
        //adapter.removeItem(viewHolder.getAdapterPosition());
        try {
            databaseRooom.daoAccess().deleteNotification(id);
            //data.clear();
            prepareData();
            //adapter.notifyDataSetChanged();

        }catch (Exception e){
            Log.e("Exception", e.toString());
        }

    }

    private void prepareData() {
        data.clear();
        data=databaseRooom.daoAccess().getAllNoficitions();
        if(data.size() >0) {
            no_record_layout.setVisibility(View.GONE);
            adapter = new OfflineAdpter(data);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }




}