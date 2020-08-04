package com.example.kidsJoy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class myAdapterNFA extends RecyclerView.Adapter<myAdapterNFA.MyViewHolder> {

    Context context;
    ArrayList<BookingRequestModel> caremodels;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase fdatabase;
    DatabaseReference ref;
    ConfirmBookingModel confirmBookingModel;

    public myAdapterNFA(Context c,ArrayList<BookingRequestModel> m){
        context = c;
        caremodels = m;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view_nfa,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.customerName.setText(caremodels.get(position).getUsername());
        holder.customerEmail.setText(caremodels.get(position).getUserEmail());
        holder.childName.setText(caremodels.get(position).getChildName());
        holder.childAge.setText(caremodels.get(position).getChildAge());
        holder.childGender.setText(caremodels.get(position).getChildGender());
        holder.bookingDate.setText(caremodels.get(position).getBookingDate());
        holder.bookingStime.setText(caremodels.get(position).getStartTime());
        holder.bookingEtime.setText(caremodels.get(position).getEndTime());


        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                firebaseAuth = FirebaseAuth.getInstance();
                fdatabase = FirebaseDatabase.getInstance();
                ref = fdatabase.getReference();


                confirmBookingModel = new ConfirmBookingModel();
                ref = FirebaseDatabase.getInstance().getReference().child("ConfirmBooking");

                confirmBookingModel.setCenterEmail(caremodels.get(position).getCenterEmail());
                confirmBookingModel.setCenterName(caremodels.get(position).getCenterName());
                confirmBookingModel.setBookingDate(caremodels.get(position).getBookingDate());
                confirmBookingModel.setBookingID(caremodels.get(position).getBookingID());
                confirmBookingModel.setStartTime(caremodels.get(position).getStartTime());
                confirmBookingModel.setEndTime(caremodels.get(position).getEndTime());
                confirmBookingModel.setChildName(caremodels.get(position).getChildName());
                confirmBookingModel.setChildAge(caremodels.get(position).getChildAge());
                confirmBookingModel.setChildGender(caremodels.get(position).getChildGender());
                confirmBookingModel.setUsername(caremodels.get(position).getUsername());
                confirmBookingModel.setUserEmail(caremodels.get(position).getUserEmail());
                confirmBookingModel.setStatus("Confirmed");
                confirmBookingModel.setRating("");
                confirmBookingModel.setReview("");
                confirmBookingModel.setComplain("");
                confirmBookingModel.setComplain("");
                confirmBookingModel.setPrice(caremodels.get(position).getPrice());



                ref.push().setValue(confirmBookingModel);
                Toast.makeText(context, "Booking Accepted", Toast.LENGTH_SHORT).show();


                DatabaseReference reff = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = reff.child("BookingRequest").orderByChild("userEmail").equalTo(caremodels.get(position).getUserEmail());

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                       // Log.e(TAG, "onCancelled", databaseError.toException());
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                });








            }
        });

        holder.customerEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xo = caremodels.get(position).getUserEmail();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"+xo));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return caremodels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView customerName, customerEmail, childName,childAge,childGender,bookingDate,bookingStime,bookingEtime;
         Button accept,reject,block;

        public MyViewHolder(View itemView) {
            super(itemView);
            customerName = (TextView) itemView.findViewById(R.id.cusName);
            customerEmail = (TextView) itemView.findViewById(R.id.cusEmail);
            childName = (TextView) itemView.findViewById(R.id.childNameCardView);
            childAge = (TextView) itemView.findViewById(R.id.childAge);
            childGender = (TextView) itemView.findViewById(R.id.childGender);
            bookingDate = (TextView) itemView.findViewById(R.id.bookingDate);
            bookingStime = (TextView) itemView.findViewById(R.id.startTimeBooking);
            bookingEtime = (TextView) itemView.findViewById(R.id.endTimeBooking);
            accept = (Button) itemView.findViewById(R.id.bookingAccept);
            reject = (Button) itemView.findViewById(R.id.bookingReject);
            block = (Button) itemView.findViewById(R.id.blockCustomer);


        }
    }
}
