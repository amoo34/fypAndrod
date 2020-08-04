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

public class myAdapterPRUS extends RecyclerView.Adapter<myAdapterPRUS.MyViewHolder> {

    Context context;
    ArrayList<BookingRequestModel> caremodels;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase fdatabase;
    DatabaseReference ref;
    ConfirmBookingModel confirmBookingModel;

    public myAdapterPRUS(Context c,ArrayList<BookingRequestModel> m){
        context = c;
        caremodels = m;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view_prus,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.childName.setText(caremodels.get(position).getChildName());
        holder.childAge.setText(caremodels.get(position).getChildAge());
        holder.childGender.setText(caremodels.get(position).getChildGender());
        holder.bookingDate.setText(caremodels.get(position).getBookingDate());
        holder.bookingStime.setText(caremodels.get(position).getStartTime());
        holder.bookingEtime.setText(caremodels.get(position).getEndTime());
        holder.centerEmail.setText(caremodels.get(position).getCenterEmail());
        holder.centerName.setText(caremodels.get(position).getCenterName());





        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference reff = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = reff.child("BookingRequest").orderByChild("userEmail").equalTo(caremodels.get(position).getUserEmail());
                Query beetaQuery = reff.child("BookingRequest").orderByChild("centerEmail").equalTo(caremodels.get(position).getCenterEmail());
                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            if((appleSnapshot.child("centerEmail").getValue().toString()).equals(caremodels.get(position).getCenterEmail())){
                                appleSnapshot.getRef().removeValue();
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            }
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


        holder.centerEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String xo = caremodels.get(position).getCenterEmail();
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

        TextView  childName,childAge,childGender,bookingDate,bookingStime,bookingEtime,centerEmail,centerName;
        Button delete;

        public MyViewHolder(View itemView) {
            super(itemView);

            childName = (TextView) itemView.findViewById(R.id.childNameCardView);
            childAge = (TextView) itemView.findViewById(R.id.childAge);
            childGender = (TextView) itemView.findViewById(R.id.childGender);
            bookingDate = (TextView) itemView.findViewById(R.id.bookingDate);
            bookingStime = (TextView) itemView.findViewById(R.id.startTimeBooking);
            bookingEtime = (TextView) itemView.findViewById(R.id.endTimeBooking);
            delete = (Button) itemView.findViewById(R.id.dltRqst);
            centerEmail = (TextView) itemView.findViewById(R.id.mailcenterPRUS);
            centerName = (TextView) itemView.findViewById(R.id.centerName);



        }
    }
}
