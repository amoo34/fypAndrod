package com.example.kidsJoy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {

    Context context;
    ArrayList<ListDccModel> caremodels;
    String status;
    Animation fadeImage;


    public myAdapter(Context c,ArrayList<ListDccModel> m){
        context = c;
        caremodels = m;
        //fadeImage = AnimationUtils.loadAnimation(this.context,R.anim.fade);

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {




        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm");
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:00"));
        String localTime = date.format(currentLocalTime);
        String Ctime = caremodels.get(position).getdCtime();
        String Otime = caremodels.get(position).getdOtime();


        if(checktimings(localTime,Ctime)==true){
            holder.statusTFalse.setVisibility(View.GONE);
            holder.statusTTrue.setVisibility(View.VISIBLE);
        }else if(checktimings(localTime,Ctime)==false){
            holder.statusTFalse.setVisibility(View.VISIBLE);
            holder.statusTTrue.setVisibility(View.GONE);
        }



        holder.namedcc.setText(caremodels.get(position).getDname());
        holder.pricedcc.setText(caremodels.get(position).getdRates());
        Picasso.get().load(caremodels.get(position).getImageUrl()).into(holder.picCenterdcc);
        status = caremodels.get(position).getAvailability();
        holder.statusOfline.setVisibility(View.GONE);
        if(status!=null && status.equals("Disabled")){
             holder.statusOfline.setVisibility(View.VISIBLE);
            fadeImage = AnimationUtils.loadAnimation(this.context,R.anim.fade);
            holder.picCenterdcc.startAnimation(fadeImage);
            holder.statusTFalse.setVisibility(View.VISIBLE);
            holder.statusTTrue.setVisibility(View.GONE);
            holder.checkDetail.setEnabled(false);
        }


        holder.checkDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String CheckedLatitude = caremodels.get(position).getLatitude();
                String CheckedLongitude = caremodels.get(position).getLongitude();
                String CheckedName = caremodels.get(position).getDname();
                String CheckedPhone = caremodels.get(position).getDphoneNumber();
                String CheckedEmail = caremodels.get(position).getDemail();
                String CheckedOpenT = caremodels.get(position).getdOtime();
                String CheckedCloseT = caremodels.get(position).getdCtime();
                String CheckedRates = caremodels.get(position).getdRates();
                String CheckedRestrections = caremodels.get(position).getdRestrections();
                String CheckedImage = caremodels.get(position).getImageUrl();





                Intent intent = new Intent(context,dayCareDetail.class);
                intent.putExtra("name",CheckedName);
                intent.putExtra("phoneNo",CheckedPhone);
                intent.putExtra("email",CheckedEmail);
                intent.putExtra("image",CheckedImage);
                intent.putExtra("rates",CheckedRates);
                intent.putExtra("restrictions",CheckedRestrections);
                intent.putExtra("openTime",CheckedOpenT);
                intent.putExtra("closeTime",CheckedCloseT);
                intent.putExtra("latitude",CheckedLatitude);
                intent.putExtra("longitude",CheckedLongitude);
                context.startActivity(intent);



            }
        });






    }

    @Override
    public int getItemCount() {
        return caremodels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView namedcc,emaildcc,pricedcc,statusOfline,statusTTrue,statusTFalse;
        ImageView picCenterdcc;
        Button checkDetail;

        public MyViewHolder(View itemView){
            super(itemView);
            namedcc = (TextView) itemView.findViewById(R.id.RVname);
            pricedcc = (TextView) itemView.findViewById(R.id.RVprice);
            picCenterdcc = (ImageView) itemView.findViewById(R.id.RVpic);
            checkDetail = (Button) itemView.findViewById(R.id.checkDetails);
            //bookingButton = (Button) itemView.findViewById(R.id.bookingButton);
            statusOfline = (TextView) itemView.findViewById(R.id.statusOfline);
            statusTTrue = (TextView) itemView.findViewById(R.id.statusTimeTrue);
            statusTFalse = (TextView) itemView.findViewById(R.id.statusTimeFalse);


        }
    }

    private boolean checktimings(String time, String endtime) {

        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

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
