package com.example.kidsJoy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class myAdapterBFA extends RecyclerView.Adapter<myAdapterBFA.MyViewHolder> {

    Context context;
    ArrayList<ConfirmBookingModel> caremodels;

    public myAdapterBFA(Context c,ArrayList<ConfirmBookingModel> m){
        context = c;
        caremodels = m;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view_bfa,parent,false));
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
        holder.status.setText(caremodels.get(position).getStatus());



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

        TextView customerName, customerEmail, childName,childAge,childGender,bookingDate,bookingStime,bookingEtime,status;


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
            status = (TextView) itemView.findViewById(R.id.statusBookingFA);


        }
    }
}
