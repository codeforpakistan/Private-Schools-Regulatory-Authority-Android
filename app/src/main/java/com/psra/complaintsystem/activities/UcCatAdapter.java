package com.psra.complaintsystem.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.psra.complaintsystem.R;
import com.psra.complaintsystem.SqliteDB.UcList_;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 8/26/2018.
 */

public class UcCatAdapter extends ArrayAdapter<UcList_> implements Filterable {

    public Context context;
    private List<UcList_> mOriginalValues; // Original Values
    private List<UcList_> mDisplayedValues;    // Values to be displayed
    public UcCatAdapter(Context context, List<UcList_> mOriginalValues ) {
        super(context, R.layout.all_cat_row, mOriginalValues);
        this.context = context;
        this.mOriginalValues = mOriginalValues;
        this.mDisplayedValues = mOriginalValues;
    }

    public class MakeDataHolder
    {
        TextView name;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        UcCatAdapter.MakeDataHolder holder ;
        if(convertView==null)
        {
            holder = new UcCatAdapter.MakeDataHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.all_cat_row, parent, false);
            holder.name = convertView.findViewById(R.id.make_name);
            convertView.setTag(holder);
        }
        else
        {
            holder=(UcCatAdapter.MakeDataHolder) convertView.getTag();
        }

        holder.name.setText(mDisplayedValues.get(position).getUcTitle());
        return convertView;

    }

    @Override
    public int getCount() {
        return mDisplayedValues.size();
    }



    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                mDisplayedValues = (ArrayList<UcList_>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<UcList_> FilteredArrList = new ArrayList<UcList_>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<>(mDisplayedValues); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).getUcTitle();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new UcList_(mOriginalValues.get(i).getUcId() ,mOriginalValues.get(i).getUcTitle()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    public List<UcList_> getList(){
        return mDisplayedValues;
    }

}


