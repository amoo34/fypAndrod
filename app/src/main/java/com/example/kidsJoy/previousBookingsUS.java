package com.example.kidsJoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class previousBookingsUS extends AppCompatActivity {


    String userEmailCheck,user;

    FirebaseDatabase fdatabase;
    DatabaseReference reference,ref;
    RecyclerView recyclerView;
    ArrayList<ConfirmBookingModel> listBookings;
    myAdapterPBUS adapter2;
    ConfirmBookingModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_bookings_us);



        recyclerView = (RecyclerView)findViewById(R.id.RVconfirmBookingsUs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listBookings = new ArrayList<ConfirmBookingModel>();

        fdatabase = FirebaseDatabase.getInstance();
        ref = fdatabase.getReference();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ref.child("Users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userEmailCheck = dataSnapshot.child("email").getValue(String.class);
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
                    user = model.getUserEmail();
                    if((user.equals(userEmailCheck))==true){
                        listBookings.add(model);
                    }


                }
                if(listBookings.isEmpty()){
                    Toast.makeText(previousBookingsUS.this, "No Pending Request", Toast.LENGTH_SHORT).show();
                }

                adapter2 = new myAdapterPBUS(previousBookingsUS.this,listBookings);
                recyclerView.setAdapter(adapter2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Toast.makeText(v.getContext(), "ooppss", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
