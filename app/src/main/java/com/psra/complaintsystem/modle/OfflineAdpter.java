package com.psra.complaintsystem.modle;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.psra.complaintsystem.R;
import com.psra.complaintsystem.SqliteDB.NotificationModule;

import java.util.List;
import java.util.Random;

/**
 * Created by HP on 10/16/2018.
 */

public class OfflineAdpter  extends RecyclerView.Adapter<OfflineAdpter.MyViewHolder> {


    private List<ComplainData> dataSet;
    public OfflineAdpter(List<ComplainData> data) {
        this.dataSet = data;
    }
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

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        // view.setOnClickListener(ListofComplaints.myOnClickListener);

        OfflineAdpter.MyViewHolder myViewHolder = new OfflineAdpter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int listPosition) {
        //TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        TextView textViewCat=holder.textViewCat;
        TextView textViewdatetime=holder.dateTime;
        TextView textViewdetail=holder.textViewdetail;
        //ImageView imageView = holder.imageViewIcon;
       String datecheck= dataSet.get(listPosition).getDate();
        String [] dateParts = datecheck.split("-");
        String dayString = dateParts[0];
        String monthString = dateParts[1];
        String yearString = dateParts[2];

        // textViewdatetime.setText(Html.fromHtml(dayString+"<br>"+monthString+ " <br>"+yearString));
        String date=dataSet.get(listPosition).getComplainDetail();
        Log.e("date",dayString );
        //textViewName.setText(dataSet.get(listPosition).getComplaintTitle());
        textViewdetail.setText(date);

       /* String monthString = dateParts[1];
        String yearString = dateParts[2];*/

       // textViewdatetime.setText(Html.fromHtml(dayString+"<br>"+monthString+ " <br>"+yearString));
        textViewdatetime.setText(Html.fromHtml(dayString+"<br>"+monthString+ " <br>"+yearString));
        //holder.date.setText(movie.getCreatedAt());
        Random random = new Random();

        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
       // holder.rel.setBackgroundColor(color);
        textViewVersion.setText((CharSequence) dataSet.get(listPosition).getComplainTypeTitle());
        textViewCat.setText((CharSequence) dataSet.get(listPosition).getStatusTitle());
       // textViewCat.setBackgroundColor(color);
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

    }

    @Override
    public  int getItemCount() {
        return dataSet.size();
    }

    public  void removeItem(int position) {
        dataSet.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }
}
