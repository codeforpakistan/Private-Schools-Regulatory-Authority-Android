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
import com.psra.complaintsystem.SqliteDB.ComplainTypesList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 8/27/2018.
 */

public class TypeCatAdpater extends ArrayAdapter<ComplainTypesList> implements Filterable {

    public Context context;
    private List<ComplainTypesList> mOriginalValues; // Original Values
    private List<ComplainTypesList> mDisplayedValues;    // Values to be displayed
    public TypeCatAdpater(Context context, List<ComplainTypesList> mOriginalValues ) {
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
        TypeCatAdpater.MakeDataHolder holder ;
        if(convertView==null)
        {
            holder = new TypeCatAdpater.MakeDataHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.all_cat_row, parent, false);
            holder.name = convertView.findViewById(R.id.make_name);
            convertView.setTag(holder);
        }
        else
        {
            holder=(TypeCatAdpater.MakeDataHolder) convertView.getTag();
        }

        holder.name.setText(mDisplayedValues.get(position).getComplainTypeTitle());
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

                mDisplayedValues = (ArrayList<ComplainTypesList>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<ComplainTypesList> FilteredArrList = new ArrayList<ComplainTypesList>();

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
                        String data = mOriginalValues.get(i).getComplainTypeTitle();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new ComplainTypesList(mOriginalValues.get(i).getComplainTypeId() ,mOriginalValues.get(i).getComplainTypeTitle()));
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

    public List<ComplainTypesList> getList(){
        return mDisplayedValues;
    }

}

