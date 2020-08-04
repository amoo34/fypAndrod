package com.example.kidsJoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class dayCareDetail extends AppCompatActivity {


    String userName,userEmail;
    int centerRqstCheck=0;
    String rootAdd="true";

    public static String latS,lonS,DCDName;

    DatePickerDialog.OnDateSetListener setListener;
    private int mhour,mMinute;
    String BookingDateA,StartTimeA,EndTimeA;
    String child_Name,child_Age;

    TextView nameTV,emailTV,phoneTV,distanceTV,ratesTV,resTV,openTV,closeTV,addressTV;
    ImageView pic;
    Button dateTime,childInfo,bookingRequest;


    RadioGroup radioGroup;
    RadioButton radioButton;
    String ChildGender;

    BookingRequestModel bookingRqstModel;
    BookingRequestModel modelCheck;
    imageModel imageModelX;
    DayCaremodel dayCaremodelX;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase fdatabase;
    DatabaseReference ref,reff,refff,refX,refXX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_care_detail);


        LinearLayout gallery = findViewById(R.id.galleryDaycare);
        LayoutInflater inflater = LayoutInflater.from(this);





        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);



        firebaseAuth = FirebaseAuth.getInstance();
        fdatabase = FirebaseDatabase.getInstance();
        ref = fdatabase.getReference();
        reff = fdatabase.getReference();





        String currentLatitude = MapsActivity.latpass;     //latpass and lonpass are static,global in MapsActivity which returns current Latitude and Longitude
        String currentLongitude = MapsActivity.lonpass;

        String name = getIntent().getStringExtra("name");
        String phoneNo = getIntent().getStringExtra("phoneNo");
        String email = getIntent().getStringExtra("email");
        String image = getIntent().getStringExtra("image");
        String rates = getIntent().getStringExtra("rates");
        String restrictions = getIntent().getStringExtra("restrictions");
        String openTime = getIntent().getStringExtra("openTime");
        String closeTime = getIntent().getStringExtra("closeTime");
        String CenterLatitude = getIntent().getStringExtra("latitude");
        String CenterLongitude = getIntent().getStringExtra("longitude");
        String CenterTimeStatus = getIntent().getStringExtra("centerClosed");



        ////image gallery

        refX = FirebaseDatabase.getInstance().getReference().child("DayCares");
        refX.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    dayCaremodelX = ds.getValue(DayCaremodel.class);
                    Log.d("stplE",dayCaremodelX.getDemail());
                    if(dayCaremodelX.getDemail().toString().equals(email)){

                        Log.d("stpl",ds.getKey());
                        refXX = FirebaseDatabase.getInstance().getReference().child("DayCares").child(ds.getKey()).child("images");
                        refXX.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot ds:dataSnapshot.getChildren()){
                                    imageModelX = ds.getValue(imageModel.class);

                                    View view = inflater.inflate(R.layout.item_gllery_dcd,gallery,false);
                                    ImageView imageView = view.findViewById(R.id.gallryItem);
                                   Picasso.get().load(imageModelX.getUrl().toString()).into(imageView);

                                    //imageView.setImageResource(Picasso.get().load(imageModelX.getUrl().toString()));


                                    gallery.addView(view);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });







                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







        for(int i=0;i<6;i++){
        }






        ////nav view

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.icEmailDCD:

                        String emailEmail = email;
                        Intent intentE = new Intent(Intent.ACTION_VIEW,Uri.parse("mailto:"+emailEmail));
                        startActivity(intentE);
                        //Toast.makeText(dayCareDetail.this, "Email", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.icLocDCD:


                        latS=CenterLatitude;
                        lonS=CenterLongitude;
                        DCDName=name;

                        Intent intentL = new Intent(dayCareDetail.this,CenterMarkerOnUserSide.class);

                        //intentL.putExtra("LongitudeMarker",CenterLongitude);
                        //intentL.putExtra("LatitudeMarker",CenterLatitude);
                        startActivity(intentL);
                    //    Toast.makeText(dayCareDetail.this, "Location", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.icRatingDCD:

                        String emailpass = email;
                        Intent intentX = new Intent(dayCareDetail.this,ReviewListUserSide.class);
                        intentX.putExtra("emailX",email);
                        startActivity(intentX);
                        //Toast.makeText(dayCareDetail.this, "Rating", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.icServicesDCD:

                           // String emailpassXX = email;
                            Intent intentXX = new Intent(dayCareDetail.this,Services_DCD.class);
                            //intentXX.putExtra("emailX",emailpassXX);
                            startActivity(intentXX);
                            //Toast.makeText(dayCareDetail.this, "Services", Toast.LENGTH_SHORT).show();
                            return true;

                    case R.id.icShareDCD:

                        Toast.makeText(dayCareDetail.this, "share", Toast.LENGTH_SHORT).show();
                        return true;


                }


                return false;
            }
        });





        nameTV = (TextView) findViewById(R.id.nameCheckDc);
        emailTV = (TextView) findViewById(R.id.emailCheckDc);
        phoneTV = (TextView) findViewById(R.id.phoneCheckDc);
        distanceTV = (TextView) findViewById(R.id.distanceCheckDc);
        addressTV = (TextView) findViewById(R.id.AddressCheckDc);
        ratesTV = (TextView) findViewById(R.id.priceCheckDc);
        resTV = (TextView) findViewById(R.id.resCheckDc);
        openTV = (TextView) findViewById(R.id.openCheckDc);
        closeTV = (TextView) findViewById(R.id.closeCheckDc);

        dateTime = (Button) findViewById(R.id.BookingDateTime);
        childInfo = (Button) findViewById(R.id.ChildInfo);
        bookingRequest = (Button) findViewById(R.id.bookNow);

        //pic = (ImageView) findViewById(R.id.picCheckDC);


        nameTV.setText(name);
        emailTV.setText(email);
        phoneTV.setText(phoneNo);
        ratesTV.setText(rates);
        resTV.setText(restrictions);
        openTV.setText(openTime);
        closeTV.setText(closeTime);
        //Picasso.get().load(image).into(pic);


        //convert current Lat Lon and dayCare Lat Lon to double to calculate distance
        double lat_a = Double.parseDouble(currentLatitude);
        double lng_a = Double.parseDouble(currentLongitude);
        double lat_b = Double.parseDouble(CenterLatitude);
        double lng_b = Double.parseDouble(CenterLongitude);

        String distance = getDistance(lat_a,lng_a,lat_b,lng_b);
        distanceTV.setText(distance);

        String address = getCompleteAddress(lat_b,lng_b);
        addressTV.setText(address);



        bookingRqstModel = new BookingRequestModel();
        reff = FirebaseDatabase.getInstance().getReference().child("BookingRequest");


        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ref.child("Users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName = dataSnapshot.child("name").getValue(String.class);
                userEmail = dataSnapshot.child("email").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        dateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(dayCareDetail.this);
                View mView = getLayoutInflater().inflate(R.layout.db_set_time, null);

                ImageButton btn_callender = (ImageButton) mView.findViewById(R.id.callenderButtonDB);
                ImageButton btn_timeS = (ImageButton) mView.findViewById(R.id.SclockButtonDB);
                ImageButton btn_timeE = (ImageButton) mView.findViewById(R.id.EclockButtonDB);
                TextView bookingDate = (TextView) mView.findViewById(R.id.bookingDateDB);
                TextView bookingStartTime = (TextView) mView.findViewById(R.id.bookingStartTimeDB);
                TextView bookingEndTime = (TextView) mView.findViewById(R.id.bookingEndTimeDB);
                Button btn_cancel = (Button) mView.findViewById(R.id.DBcancelbtn);
                Button btn_confirm = (Button) mView.findViewById(R.id.DBconfirmbtn);

                alert.setView(mView);

                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);

                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                bookingDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(dayCareDetail.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                setListener, year, month, day);
                        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        datePickerDialog.show();

                    }
                });
                btn_callender.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(dayCareDetail.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                setListener, year, month, day);
                        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        datePickerDialog.show();

                    }
                });

                setListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = year + "-" + month + "-" + day;
                        bookingDate.setText(date);
                        BookingDateA = date;
                    }
                };





                bookingStartTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar calendar = Calendar.getInstance();
                        mhour = calendar.get(Calendar.HOUR_OF_DAY);
                        mMinute = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(dayCareDetail.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                bookingStartTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mhour, mMinute, false);
                        timePickerDialog.show();
                    }
                });

                btn_timeS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar calendar = Calendar.getInstance();
                        mhour = calendar.get(Calendar.HOUR_OF_DAY);
                        mMinute = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(dayCareDetail.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                bookingStartTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mhour, mMinute, false);
                        timePickerDialog.show();
                    }
                });

                bookingEndTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar calendar = Calendar.getInstance();
                        mhour = calendar.get(Calendar.HOUR_OF_DAY);
                        mMinute = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(dayCareDetail.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                bookingEndTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mhour, mMinute, false);
                        timePickerDialog.show();
                    }
                });


                btn_timeE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar calendar = Calendar.getInstance();
                        mhour = calendar.get(Calendar.HOUR_OF_DAY);
                        mMinute = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(dayCareDetail.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                bookingEndTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mhour, mMinute, false);
                        timePickerDialog.show();
                    }
                });


                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });


                btn_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        BookingDateA = bookingDate.getText().toString();
                        StartTimeA = bookingStartTime.getText().toString();
                        EndTimeA = bookingEndTime.getText().toString();

                        Calendar calendar1 = Calendar.getInstance();


                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                      //  String dateFormated = format.format(Date.parse(BookingDateA));
                        String currentDate = format.format(calendar1.getTime());
                        BookingDateA = format.format(calendar.getTime());


                            if(checktimings(openTime,StartTimeA)==true &&checktimings(EndTimeA,closeTime)==true) {
                                bookingRqstModel.setBookingDate(BookingDateA);
                                bookingRqstModel.setStartTime(StartTimeA);
                                bookingRqstModel.setEndTime(EndTimeA);
                                Toast.makeText(dayCareDetail.this, "Added", Toast.LENGTH_SHORT).show();
                               }
                            if (checktimings(openTime,StartTimeA)==false || checktimings(EndTimeA,closeTime)==false) {
                                rootAdd="false";
                                Toast.makeText(dayCareDetail.this, "Center is not Accepting your timing", Toast.LENGTH_SHORT).show();
                                //dialog box X
                            }



                        //   Toast.makeText(dayCareDetail.this, "", Toast.LENGTH_LONG).show();


                        //ref.push().setValue(bookingRqstModel);

                        alertDialog.dismiss();
                    }
                });


                alertDialog.show();

            }
        });



        childInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(dayCareDetail.this);
                View mView = getLayoutInflater().inflate(R.layout.db_child_info, null);


                EditText childName = (EditText) mView.findViewById(R.id.childNameCardView);
                EditText childAge = (EditText) mView.findViewById(R.id.ChildAge);
                radioGroup = mView.findViewById(R.id.radioGroup);
                RadioButton male = (RadioButton) mView.findViewById(R.id.maleRB);
                RadioButton female = (RadioButton) mView.findViewById(R.id.femaleRB);
                Button btn_cancel = (Button) mView.findViewById(R.id.DBcancelbtn);
                Button btn_confirm = (Button) mView.findViewById(R.id.DBconfirmbtn);

                alert.setView(mView);

                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);


                btn_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //  Toast.makeText(dayCareDetail.this, ""+gender, Toast.LENGTH_SHORT).show();
                        if(male.isChecked()){
                            ChildGender=male.getText().toString();
                            //Toast.makeText(getApplicationContext(),""+male.getText().toString(),Toast.LENGTH_SHORT).show();
                        }
                        else if(female.isChecked()){
                            ChildGender=female.getText().toString();
                            //Toast.makeText(getApplicationContext(),""+female.getText().toString(),Toast.LENGTH_SHORT).show();
                        }

                         child_Name = childName.getText().toString();
                         child_Age = childAge.getText().toString();

                         double checkAge = Double.parseDouble(child_Age);
                         if(checkAge!=(double)checkAge){
                             rootAdd="false";
                             Toast.makeText(dayCareDetail.this, "Age not correct", Toast.LENGTH_SHORT).show();
                         }




                        Toast.makeText(dayCareDetail.this, "Child info Added", Toast.LENGTH_LONG).show();
                        alertDialog.dismiss();


                    }
                });


                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });



                alertDialog.show();
            }
        });



        bookingRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!email.equals("") && !BookingDateA.equals("") && !StartTimeA.equals("") && !EndTimeA.equals("") && !child_Name.equals("") &&
                        !child_Age.equals("") && !ChildGender.equals("") && !userEmail.equals("") && !userName.equals("") && !rates.equals("") ){


                    bookingRqstModel.setCenterEmail(email);
                    bookingRqstModel.setCenterName(name);
                    bookingRqstModel.setChildName(child_Name);
                    bookingRqstModel.setChildAge(child_Age);
                    bookingRqstModel.setChildGender(ChildGender);
                    bookingRqstModel.setUserEmail(userEmail);
                    bookingRqstModel.setUsername(userName);
                    bookingRqstModel.setPrice(rates);


                    int random = (int)(Math.random() * 10000000 + 1);

                    bookingRqstModel.setBookingID(Integer.toString(random));



                    refff = FirebaseDatabase.getInstance().getReference().child("BookingRequest");
                    refff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                modelCheck=ds.getValue(BookingRequestModel.class);
                                if(userEmail.equals(modelCheck.getUserEmail())==true){
                                    centerRqstCheck=1;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else{
                    Toast.makeText(dayCareDetail.this, "Correct your info", Toast.LENGTH_LONG).show();
                    }

                if(centerRqstCheck==0 && rootAdd=="true"){

                    reff.push().setValue(bookingRqstModel);


                    final AlertDialog.Builder alert = new AlertDialog.Builder(dayCareDetail.this);
                    View mView = getLayoutInflater().inflate(R.layout.db_rqstsent_successfully, null);

                    ImageView btn_closeDBpos = (ImageView) mView.findViewById(R.id.closeDBpos);
                    Button btnCheckBookingReqDB = (Button) mView.findViewById(R.id.db_rqstSentCheckB);

                    alert.setView(mView);

                    AlertDialog alertDialog = alert.create();
                    alertDialog.setCanceledOnTouchOutside(false);

                    btn_closeDBpos.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    btnCheckBookingReqDB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it = new Intent(dayCareDetail.this,pendingRqstUS.class);
                            startActivity(it);
                        }
                    });

                    alertDialog.show();
                    //Toast.makeText(dayCareDetail.this, "Request sent", Toast.LENGTH_SHORT).show();

                }
                else if(centerRqstCheck==1 || rootAdd=="false"){

                    AlertDialog.Builder alert = new AlertDialog.Builder(dayCareDetail.this);
                    View mView = getLayoutInflater().inflate(R.layout.db_errorbookingrqst, null);

                    ImageView btn_closeDBneg = (ImageView) mView.findViewById(R.id.closeDBNeg);
                    alert.setView(mView);

                    final AlertDialog alertDialog = alert.create();
                    alertDialog.setCanceledOnTouchOutside(false);

                    btn_closeDBneg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                    //Toast.makeText(dayCareDetail.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    public static String getDistance(double lat_a, double lng_a, double lat_b, double lng_b)
    {
        // earth radius is in mile
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b - lat_a);
        double lngDiff = Math.toRadians(lng_b - lng_a);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
                + Math.cos(Math.toRadians(lat_a))
                * Math.cos(Math.toRadians(lat_b)) * Math.sin(lngDiff / 2)
                * Math.sin(lngDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        int meterConversion = 1609;
        double kmConvertion = 1.6093;
        // return new Float(distance * meterConversion).floatValue();
        return String.format("%.2f", new Float(distance * kmConvertion).floatValue());
        // return String.format("%.2f", distance)+" m";
    }



    private String getCompleteAddress(double x, double y)       //method for conversion of latitude and longitude to address string
    {

        String address = "";
        Geocoder geocoder = new Geocoder(dayCareDetail.this, Locale.getDefault());


        try {
            List<Address> addresses = geocoder.getFromLocation(x,y,1);

            if(address != null){
                Address returnAddress = (Address) addresses.get(0);
                StringBuilder stringBuilderReturnAddress = new StringBuilder("");

                for(int i=0;i<=returnAddress.getMaxAddressLineIndex();i++)
                {
                    stringBuilderReturnAddress.append(returnAddress.getAddressLine(i)).append("\n");

                }

                address = stringBuilderReturnAddress.toString();

            }else{
                Toast.makeText(this, "Address not Found", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Toast.makeText(this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }

        return address ;

    }


    private boolean checktimings(String time1, String time2) {

        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);

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
