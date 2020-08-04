package com.example.kidsJoy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class fargmntHome extends Fragment {

   // ImageView img;
    private FirebaseDatabase fdatabase;
    private DatabaseReference reference;
    Button profileBHFA,reviewsBHFS,servicesBHFA,promotionBHFA,cCareBHFA,logoutBHFA;


    String u1="";
    String Ct1="ko",Bt1;
    ConfirmBookingModel confirmBookingModel;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);

      //  img = (ImageView) v.findViewById(R.id.piccenter);
        profileBHFA = (Button) v.findViewById(R.id.BprofileHomeFA);
        reviewsBHFS = (Button) v.findViewById(R.id.BreviewsHomeFA);
        servicesBHFA = (Button) v.findViewById(R.id.BservicesHomeFA);
        promotionBHFA = (Button) v.findViewById(R.id.BpromoHomeFA);
        cCareBHFA = (Button) v.findViewById(R.id.BCcareHomeFA);
        logoutBHFA = (Button) v.findViewById(R.id.BlogoutHomeFA);


        final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

/*
        fdatabase = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = fdatabase.getReference("DayCares").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable <DataSnapshot> allData = dataSnapshot.getChildren();

                String url = dataSnapshot.child("imageUrl").getValue().toString();
                Picasso.get().load(url).into(img);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


*/




        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm"); //  can get seconds by adding  "...:ss" to it
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:00"));
        String localTime = date.format(currentLocalTime);



        reference = FirebaseDatabase.getInstance().getReference().child("ConfirmBooking");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    confirmBookingModel = ds.getValue(ConfirmBookingModel.class);
                    if(confirmBookingModel.getStatus().toString().equals("Confirmed")){
                        Ct1=confirmBookingModel.getEndTime().toString();
                        Bt1= ds.getKey().toString();
                        if(checktimings(Ct1,localTime)==true){
                            reference.child(Bt1).child("status").setValue("Finished");
                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







        profileBHFA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), DayCareProfile.class));
                //Toast.makeText(v.getContext(), "profile clicked", Toast.LENGTH_SHORT).show();
            }
        });

        reviewsBHFS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(v.getContext(),ReviewsListCenterSide.class));
               // Toast.makeText(v.getContext(), ""+Bt1, Toast.LENGTH_LONG).show();
            }
        });

        servicesBHFA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),Services_DCD.class));
               // Toast.makeText(v.getContext(), "services clicked", Toast.LENGTH_SHORT).show();
            }
        });

        promotionBHFA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "promotions clicked", Toast.LENGTH_SHORT).show();
            }
        });

        cCareBHFA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "care clicked", Toast.LENGTH_SHORT).show();
            }
        });

        logoutBHFA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent itttt = new Intent(v.getContext(),UserType.class);
                itttt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(itttt);
                //Toast.makeText(v.getContext(), "logout clicked", Toast.LENGTH_SHORT).show();
            }
        });


                return v;

    }


    private boolean checktimings(String t1, String t2) {

        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(t1);
            Date date2 = sdf.parse(t2);

            if(date1.before(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }

}
