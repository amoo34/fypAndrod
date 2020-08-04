package com.example.kidsJoy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class showDaycareFilters extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tabAllAvail,tabRating,tabDistance;
    public PageAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_daycare_filters);


        tabLayout = (TabLayout) findViewById(R.id.tabLayoutSDfilter);
        tabAllAvail = (TabItem) findViewById(R.id.tabAllAvailableSD);
        tabRating = (TabItem) findViewById(R.id.tabRatingSD);
        tabDistance = (TabItem) findViewById(R.id.tabDistanceSD);

        viewPager = (ViewPager) findViewById(R.id.viewPagerSDF);


        pagerAdapter = new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0){
                    pagerAdapter.notifyDataSetChanged();
                }
                else if(tab.getPosition() == 1) {
                    pagerAdapter.notifyDataSetChanged();
                }
                else if(tab.getPosition() == 2) {
                    pagerAdapter.notifyDataSetChanged();
                }



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));











    }

}
