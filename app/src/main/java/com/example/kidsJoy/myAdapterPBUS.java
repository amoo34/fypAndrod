package com.example.kidsJoy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


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

public class myAdapterPBUS extends RecyclerView.Adapter<myAdapterPBUS.MyViewHolder> {

    Context context;
    ArrayList<ConfirmBookingModel> caremodels;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase fdatabase;
    DatabaseReference ref,reference2,reference3,reference4;


    ConfirmBookingModel confirmBookingModel;
    DayCaremodel dayCaremodel;
    String center;
    String updateRatingCE;
    double ratingCount=0,ratingC=0;


    String Bt1,UserEmail,ratingX,bookingDate;


    public myAdapterPBUS(Context c,ArrayList<ConfirmBookingModel> m){
        context = c;
        caremodels = m;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view_pbus,parent,false));
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
        UserEmail = caremodels.get(position).getUserEmail().toString();
        bookingDate = caremodels.get(position).getBookingDate().toString();
        Bt1=caremodels.get(position).getBookingID();

////////////for complain and review


        String rate = caremodels.get(position).getRating().toString();
        String review = caremodels.get(position).getReview().toString();
        //String complain = caremodels.get(position).getComplain().toString;







        holder.revAndRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rate.isEmpty()==false&&review.isEmpty()==false){
                    Toast.makeText(context, "your have already rated this Booking", Toast.LENGTH_SHORT).show();
                }
                if(rate.isEmpty()&&review.isEmpty()){



                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:00"));
                    Date currentLocalTime = cal.getTime();
                    DateFormat date = new SimpleDateFormat("HH:mm"); //  can get seconds by adding  "...:ss" to it
                    date.setTimeZone(TimeZone.getTimeZone("GMT+5:00"));
                    String localTime = date.format(currentLocalTime);





                    reference2 = FirebaseDatabase.getInstance().getReference().child("ConfirmBooking");
                    reference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                confirmBookingModel = ds.getValue(ConfirmBookingModel.class);


                                //check by booking ID
                                if(confirmBookingModel.getUserEmail().toString().equals(UserEmail) && confirmBookingModel.getBookingDate().toString().equals(bookingDate)){


                                            final androidx.appcompat.app.AlertDialog.Builder alert = new androidx.appcompat.app.AlertDialog.Builder(context);
                                            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                            View mView = inflater.inflate(R.layout.db_userside_rate_review, null);

                                            RatingBar mRatingBar = (RatingBar) mView.findViewById(R.id.ratingBar);
                                            TextView mRatingScale = (TextView) mView.findViewById(R.id.tvRatingScale);
                                            EditText mFeedback = (EditText) mView.findViewById(R.id.review);
                                            Button mSendFeedback = (Button) mView.findViewById(R.id.btnSubmit);

                                            alert.setView(mView);

                                            final androidx.appcompat.app.AlertDialog alertDialog = alert.create();
                                            alertDialog.setCanceledOnTouchOutside(false);

                                            mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                                @Override
                                                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                                                }
                                            });

                                            mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                                @Override
                                                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                                                    mRatingScale.setText(String.valueOf(v));
                                                    switch ((int) ratingBar.getRating()) {
                                                        case 1:
                                                            mRatingScale.setText("Very bad");
                                                            ratingX="1";
                                                            break;
                                                        case 2:
                                                            mRatingScale.setText("Need some improvement");
                                                            ratingX="2";
                                                            break;
                                                        case 3:
                                                            mRatingScale.setText("Good");
                                                            ratingX="3";
                                                            break;
                                                        case 4:
                                                            mRatingScale.setText("Great");
                                                            ratingX="4";
                                                            break;
                                                        case 5:
                                                            mRatingScale.setText("Awesome. I love it");
                                                            ratingX="5";
                                                            break;
                                                        default:
                                                            mRatingScale.setText("");
                                                    }
                                                }
                                            });

                                            mSendFeedback.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if (mFeedback.getText().toString().isEmpty()) {
                                                        Toast.makeText(context, "Please fill in feedback text box", Toast.LENGTH_LONG).show();
                                                    } else {
                                                        String review = mFeedback.getText().toString();

                                                        reference2.child(ds.getKey().toString()).child("rating").setValue(ratingX);
                                                        reference2.child(ds.getKey().toString()).child("review").setValue(review);




                                                        reference3 = FirebaseDatabase.getInstance().getReference().child("ConfirmBooking");
                                                        reference3.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                for(DataSnapshot ds:dataSnapshot.getChildren()){
                                                                    confirmBookingModel = ds.getValue(ConfirmBookingModel.class);
                                                                    center = confirmBookingModel.getCenterEmail();
                                                                    if((center.equals(updateRatingCE))==true){

                                                                        if(confirmBookingModel.getRating().isEmpty()==false && confirmBookingModel.getReview().isEmpty()==false) {
                                                                            ratingC = ratingC+Double.parseDouble(confirmBookingModel.getRating().toString());
                                                                            ratingCount=ratingCount+5;

                                                                            // Log.d("Step2:",rating);
                                                                        }
                                                                    }}


                                                                reference4 = FirebaseDatabase.getInstance().getReference().child("DayCares");
                                                                reference4.addValueEventListener(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                                                                            dayCaremodel = ds.getValue(DayCaremodel.class);
                                                                            String dd = dayCaremodel.getDemail().toString();
                                                                            // Log.d("step3",dd);
                                                                            if(dd.equals(updateRatingCE)){
                                                                                //Log.d("step4","reached");
                                                                                reference4.child(ds.getKey().toString()).child("overAllRating").setValue((Double.toString((ratingC/ratingCount)*5)));
                                                                                break;
                                                                            }



                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                    }
                                                                });



                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });




                                                        alertDialog.dismiss();

                                                        Toast.makeText(context, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                            alertDialog.show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

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


        holder.complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, complainUserSide.class);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("BookingID",Bt1);


                context.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return caremodels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView  childName,childAge,childGender,bookingDate,bookingStime,bookingEtime,centerEmail,centerName;
        Button revAndRate,complain;

        public MyViewHolder(View itemView) {
            super(itemView);

            childName = (TextView) itemView.findViewById(R.id.childNameCardView);
            childAge = (TextView) itemView.findViewById(R.id.childAge);
            childGender = (TextView) itemView.findViewById(R.id.childGender);
            bookingDate = (TextView) itemView.findViewById(R.id.bookingDate);
            bookingStime = (TextView) itemView.findViewById(R.id.startTimeBooking);
            bookingEtime = (TextView) itemView.findViewById(R.id.endTimeBooking);
            revAndRate = (Button) itemView.findViewById(R.id.rateNow);
            complain = (Button) itemView.findViewById(R.id.complain);
            centerEmail = (TextView) itemView.findViewById(R.id.mailcenterPRUS);
            centerName = (TextView) itemView.findViewById(R.id.centerName);



        }
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
