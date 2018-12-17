package com.psra.complaintsystem.modle;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.psra.complaintsystem.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by HP on 7/18/2018.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private List<ComplainsList> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        TextView textViewCat;
        TextView dateTime;
        private View rel;
        TextView textViewdetail;
       // ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            //this.textViewName = (TextView) itemView.findViewById(R.id.textcomplaintTitle);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.textStatus);
            this.textViewCat=(TextView)itemView.findViewById(R.id.CatStatus);
            this.rel = itemView.findViewById(R.id.rel);
            this.dateTime=itemView.findViewById(R.id.datetime);
            this.textViewdetail=itemView.findViewById(R.id.detailtv);
            //this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    public CustomAdapter(List<ComplainsList> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

       // view.setOnClickListener(ListofComplaints.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

       // TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        TextView textViewCat=holder.textViewCat;
        TextView textViewdatetime=holder.dateTime;
        TextView textViewdetail=holder.textViewdetail;
        //ImageView imageView = holder.imageViewIcon;

       // textViewName.setText(dataSet.get(listPosition).getDistrictTitle());
        textViewdetail.setText(dataSet.get(listPosition).getComplainDetail().toString());
       // Object complainsList=dataSet.get(listPosition).getStatusTitle();
        String date=dataSet.get(listPosition).getDated();
        String [] dateParts = date.split("-");
        String dayString = dateParts[0];
        String monthString = dateParts[1];
        String yearString = dateParts[2];
        Log.e("datetime", "onBindViewHolder: "+dataSet.get(listPosition).getDated());
       /* holder.approve_day.setText(dayString);
        holder.approve_month.setText(monthString);
        holder.approve_year.setText(yearString);*/
         //textViewdatetime.setText("date");
        textViewdatetime.setText(Html.fromHtml(dayString+"<br>"+monthString+ " <br>"+yearString));
        //holder.date.setText(movie.getCreatedAt());
        Random random = new Random();

        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
       // holder.rel.setBackgroundColor(color);
        textViewVersion.setText((CharSequence) dataSet.get(listPosition).getComplainTypeTitle());
        textViewCat.setText((CharSequence) dataSet.get(listPosition).getStatusTitle());

        Log.e("onBindViewHolder: ",dataSet.get(listPosition).getStatusTitle().toString());
        String statusvalue=dataSet.get(listPosition).getStatusTitle().toString();

        if(statusvalue.equalsIgnoreCase("Received")) {

            textViewCat.setBackgroundResource(R.color.recieved);
            holder.rel.setBackgroundResource(R.color.recieved);
        }
        else if(statusvalue.equalsIgnoreCase("Resolved")) {

            textViewCat.setBackgroundResource(R.color.resolved);
            holder.rel.setBackgroundResource(R.color.resolved);
        }

        else{

           textViewCat.setBackgroundResource(R.color.all);
            holder.rel.setBackgroundResource(R.color.all);
        }


        //imageView.setImageResource(dataSet.get(listPosition).getImage());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }



}
