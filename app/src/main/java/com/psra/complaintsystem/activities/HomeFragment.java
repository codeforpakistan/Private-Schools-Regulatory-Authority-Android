package com.psra.complaintsystem.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(com.psra.complaintsystem.R.layout.fragment_home, container, false);
        ViewPager vpPager = (ViewPager) view.findViewById(com.psra.complaintsystem.R.id.pager);
        vpPager.setClipToPadding(false);
        vpPager.setPageMargin(15);

        vpPager.setAdapter(new MyPageAdapter(getChildFragmentManager()));


        return view;
    }
    class MyPageAdapter extends FragmentPagerAdapter

    {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new ComplaintRegisterFragment();
            } else
                return new ListofComplaintFragment();

        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public float getPageWidth (int position) {
            return 0.85f;

        }
    }

}
