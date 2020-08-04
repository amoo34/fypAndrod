package com.example.kidsJoy;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class showDayCare extends AppCompatActivity {


    String latpassX,lonpassX;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<ListDccModel> listDCC;
    myAdapter adapter;
    ListDccModel model;
   // Button showAll;
    boolean countCenter = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_day_care);

     //   showAll = (Button) findViewById(R.id.allCenters);
        recyclerView = (RecyclerView)findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listDCC = new ArrayList<ListDccModel>();




        reference = FirebaseDatabase.getInstance().getReference().child("DayCares");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {

                    String latpassA = MapsActivity.latpass;
                    String lonpassA = MapsActivity.lonpass;
                    double lat_a = Double.parseDouble(latpassA);
                    double lon_a = Double.parseDouble(lonpassA);
                    model = ds.getValue(ListDccModel.class);
                    String modelLat =  model.getLatitude();
                    String modelLon = model.getLongitude();
                    double lat_b = Double.parseDouble(modelLat);
                    double lon_b = Double.parseDouble(modelLon);

                    String dis = getDistance(lat_a,lon_a,lat_b,lon_b);
                    double distance = Double.parseDouble(dis);


                    if(lat_a!=-1&&lon_a!=-1){

                        if(distance<=3)
                        {
                            listDCC.add(model);
                            countCenter=true;
                        }
                        if(countCenter==false && distance<=6){

                            listDCC.add(model);
                            countCenter=true;
                        }
                        if(countCenter==false && distance<=9){
                            listDCC.add(model);
                            countCenter=true;
                        }
                        if(countCenter==false && distance<=15){
                            listDCC.add(model);
                            countCenter=true;
                        }

                    }
                    if(lat_a==-1&&lon_a==-1){
                        listDCC.add(model);
                    }


                }
                if(listDCC.isEmpty()){
                    Toast.makeText(showDayCare.this, "Sorry there is no DAYCARE CENTER near your Selected Location", Toast.LENGTH_LONG).show();
                }
                adapter = new myAdapter(showDayCare.this,listDCC);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(showDayCare.this, "Opps", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static String getDistance(double lat_a, double lng_a, double lat_b, double lng_b) {
        // earth radius is in mile
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b - lat_a);
        double lngDiff = Math.toRadians(lng_b - lng_a);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
                + Math.cos(Math.toRadians(lat_a))
                * Math.cos(Math.toRadians(lat_b)) * Math.sin(lngDiff / 2)
                * Math.sin(lngDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        int meterConversion = 1609;
        double kmConvertion = 1.6093;
        // return new Float(distance * meterConversion).floatValue();
        return String.format("%.2f", new Float(distance * kmConvertion).floatValue());
        // return String.format("%.2f", distance)+" m";
    }


}