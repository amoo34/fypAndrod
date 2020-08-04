package com.example.kidsJoy;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    private int noOfTabs;

    public PageAdapter(FragmentManager fm,int noOfTabs){
        super(fm);
        this.noOfTabs=noOfTabs;
    }





    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new tabAllAvail();

            case 1:
                return new tabRating();

            case 2:
                return new tabDistance();

            default:
                return null;


        }


    }

    @Override
    public int getCount() {
        return noOfTabs;
    }


    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
