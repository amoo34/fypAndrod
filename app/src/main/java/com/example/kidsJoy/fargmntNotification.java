package com.example.kidsJoy;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fargmntNotification extends Fragment {



    String centerEmailCheck,center;

    FirebaseDatabase fdatabase;
    DatabaseReference reference,ref;
    RecyclerView recyclerView;
    ArrayList<BookingRequestModel> listBookingsReq;
    myAdapterNFA adapter2;
    BookingRequestModel model;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification,container,false);




        recyclerView = (RecyclerView)v.findViewById(R.id.RVnotificationFA);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listBookingsReq = new ArrayList<BookingRequestModel>();

        fdatabase = FirebaseDatabase.getInstance();
        ref = fdatabase.getReference();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ref.child("DayCares").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                centerEmailCheck = dataSnapshot.child("demail").getValue(String.class);
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
                    center = model.getCenterEmail();
                    if((center.equals(centerEmailCheck))==true){
                    listBookingsReq.add(model);}


                }
                adapter2 = new myAdapterNFA(getActivity(),listBookingsReq);
                recyclerView.setAdapter(adapter2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
               // Toast.makeText(v.getContext(), "ooppss", Toast.LENGTH_SHORT).show();
            }
        });




        return v;
    }
}
