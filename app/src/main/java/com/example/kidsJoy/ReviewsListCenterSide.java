package com.example.kidsJoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReviewsListCenterSide extends AppCompatActivity {


    String centerEmailCheck,center;

    FirebaseDatabase fdatabase;
    DatabaseReference reference,ref;
    RecyclerView recyclerView;
    ArrayList<ConfirmBookingModel> listReviews;
    myAdapterRLCS adapter2;
    ConfirmBookingModel model;

    double ratingCount=0,rating=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_list_center_side);

        TextView ratingOverAll = (TextView) findViewById(R.id.ratingA);





        recyclerView = (RecyclerView)findViewById(R.id.RV_reviewListCS);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listReviews = new ArrayList<ConfirmBookingModel>();

        fdatabase = FirebaseDatabase.getInstance();
        ref = fdatabase.getReference();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ref.child("DayCares").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                centerEmailCheck = dataSnapshot.child("demail").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });










        reference = FirebaseDatabase.getInstance().getReference().child("ConfirmBooking");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    model = ds.getValue(ConfirmBookingModel.class);
                    center = model.getCenterEmail();
                    if((center.equals(centerEmailCheck))==true){

                        if(model.getRating().isEmpty()==false && model.getReview().isEmpty()==false) {
                            rating = rating+Double.parseDouble(model.getRating().toString());
                            ratingCount=ratingCount+5;
                            listReviews.add(model);
                        }
                    }


                }
                if(listReviews.isEmpty()){
                    Toast.makeText(ReviewsListCenterSide.this, "No Reviews"+centerEmailCheck, Toast.LENGTH_SHORT).show();
                }

                adapter2 = new myAdapterRLCS(ReviewsListCenterSide.this,listReviews);
                recyclerView.setAdapter(adapter2);
                ratingOverAll.setText(Double.toString((rating/ratingCount)*5));



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Toast.makeText(v.getContext(), "ooppss", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
