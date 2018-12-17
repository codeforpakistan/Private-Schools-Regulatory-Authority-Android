package com.psra.complaintsystem.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListofComplaintFragment extends Fragment {

    ImageView imageView;
    public ListofComplaintFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(com.psra.complaintsystem.R.layout.fragment_listof_complaint, container, false);
        imageView=view.findViewById(com.psra.complaintsystem.R.id.tvimage);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),ListofComplaints.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
