package com.example.kidsJoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DayCarehome extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_carehome);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentDC, new fargmntHome()).commit();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;

                switch (menuItem.getItemId())
                {
                    case R.id.action_home:
                        //Toast.makeText(DayCarehome.this, "Home Clicked", Toast.LENGTH_SHORT).show();
                        selectedFragment = new fargmntHome();
                        break;

                    case R.id.action_booking:
                        selectedFragment = new fargmntBookings();
                        //Toast.makeText(DayCarehome.this, "booking Clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_availability:
                        selectedFragment = new fargmntAvailability();
                        //Toast.makeText(DayCarehome.this, "Availability Clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_notification:
                        selectedFragment = new fargmntNotification();
                        //Toast.makeText(DayCarehome.this, "Notifications Clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_more:
                        Intent it = new Intent(getApplicationContext(), DayCareMoreItems.class);
                        startActivity(it);
                        //selectedFragment = new fargmntMore();
                        // Toast.makeText(DayCarehome.this, "More Clicked", Toast.LENGTH_SHORT).show();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentDC, selectedFragment).commit();

                return true;
            }
        });
    }
}