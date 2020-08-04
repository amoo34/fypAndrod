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

public class pendingRqstUS extends AppCompatActivity {


    String userEmailCheck,user;

    FirebaseDatabase fdatabase;
    DatabaseReference reference,ref;
    RecyclerView recyclerView;
    ArrayList<BookingRequestModel> listBookingsReq;
    myAdapterPRUS adapter2;
    BookingRequestModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_rqst_us);



        recyclerView = (RecyclerView)findViewById(R.id.RVpendingRqstUs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listBookingsReq = new ArrayList<BookingRequestModel>();

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





        reference = FirebaseDatabase.getInstance().getReference().child("BookingRequest");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    model = ds.getValue(BookingRequestModel.class);
                    user = model.getUserEmail();
                    if((user.equals(userEmailCheck))==true){
                        listBookingsReq.add(model);
                }


                }
                if(listBookingsReq.isEmpty()){
                    Toast.makeText(pendingRqstUS.this, "No Pending Request", Toast.LENGTH_SHORT).show();
                }

                adapter2 = new myAdapterPRUS(pendingRqstUS.this,listBookingsReq);
                recyclerView.setAdapter(adapter2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Toast.makeText(v.getContext(), "ooppss", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
