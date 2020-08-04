package com.example.kidsJoy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class myAdapterRLCS extends RecyclerView.Adapter<myAdapterRLCS.MyViewHolder> {

    Context context;
    ArrayList<ConfirmBookingModel> caremodels;

    FirebaseDatabase fdatabase;
    DatabaseReference ref,reference2;

    ConfirmBookingModel confirmBookingModel;

    public myAdapterRLCS(Context c,ArrayList<ConfirmBookingModel> m){
        context = c;
        caremodels = m;

    }


    @NonNull
    @Override
    public myAdapterRLCS.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myAdapterRLCS.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view_rlcs,parent,false));
    }



    @Override
    public void onBindViewHolder(@NonNull myAdapterRLCS.MyViewHolder holder, int position) {


        holder.childName.setText(caremodels.get(position).getChildName());
        holder.bookingID.setText(caremodels.get(position).getBookingDate());
        holder.review.setText(caremodels.get(position).getReview());
        holder.rating.setText(caremodels.get(position).getRating());



    }


    @Override
    public int getItemCount() {
        return caremodels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView childName,bookingID,review,rating;

        public MyViewHolder(View itemView) {
            super(itemView);

            childName = (TextView) itemView.findViewById(R.id.childNameCardView);
            bookingID = (TextView) itemView.findViewById(R.id.bookingIDcardView);
            review = (TextView) itemView.findViewById(R.id.reviewcardView);
            rating = (TextView) itemView.findViewById(R.id.ratingCardView);

        }
    }


    }
