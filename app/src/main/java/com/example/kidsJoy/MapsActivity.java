package com.example.kidsJoy;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import androidx.core.content.ContextCompat;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MapsActivity extends AppCompatActivity  implements OnMapReadyCallback {

    public static String latpass;
    public static String lonpass;
    public static String AllDayCares="-1";

    String rating;
    String homeLatA,homeLonA,workLatA,workLonA;


    private FirebaseAuth mAuth;
    FirebaseUser currentuser;




    String UserEmail;
    String bookingEndTime;
    String checkDlt="no";

    ConfirmBookingModel model;
    DayCaremodel dayCaremodel;
    double ratingCount=0,ratingC=0;
    String centerEmailCheck,center;
    String updateRatingCE;



    TextView userNameA,homeLocation,workLocation;
   // String UserName,homeLoc,workLoc;


    private GoogleMap mMap;
    private ImageButton getCurrent,earth,editHome,editWork;
    private Button turnOn,gps;
    private Button NearWork,NearHome,NearCurrent,AllCenters;
    private TextView HomeLocText,WorkLocText,username;
    private LocationManager locationManager;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private FirebaseDatabase fdatabase;
    ConfirmBookingModel confirmBookingModel;
    private DatabaseReference reference,reference2,reference3,reference4;
    Location locationC;
   // Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    private DrawerLayout mdrawer;
    Marker marker;
    public FusedLocationProviderClient fusedLocationProviderClient;
    public void info() {
        fdatabase = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = fdatabase.getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> allData = dataSnapshot.getChildren();
                for (DataSnapshot currentUser : allData) {


                    if (userId.equals(currentUser.getKey())) {
                        String UserName = currentUser.child("name").getValue().toString();
                        String Email = currentUser.child("email").getValue().toString();
                        String PhoneNumber = currentUser.child("phoneNumber").getValue().toString();
                      //  Toast.makeText(getApplicationContext(), "ere" + UserName, Toast.LENGTH_SHORT).show();
                        //  String UserName = currentUser.child("email").getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawerlayout);

        mAuth = FirebaseAuth.getInstance();

        currentuser=mAuth.getCurrentUser();



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbart);
        userNameA = (TextView) findViewById(R.id.usernameMACV);
        homeLocation = (TextView) findViewById(R.id.searchHomelocation);
        workLocation = (TextView) findViewById(R.id.searchWorklocation);
        //fdatabase = FirebaseDatabase.getInstance();
        //reference = fdatabase.getReference("Users");
        //String nameU = reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).;
        fdatabase = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        reference = fdatabase.getReference("Users").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable <DataSnapshot> allData = dataSnapshot.getChildren();
                userNameA.setText(dataSnapshot.child("name").getValue().toString());
                homeLocation.setText(dataSnapshot.child("homeLocation").getValue().toString());
                workLocation.setText(dataSnapshot.child("workLocation").getValue().toString());
                homeLatA=dataSnapshot.child("homeLocLat").getValue().toString();
                homeLonA=dataSnapshot.child("homeLocLon").getValue().toString();
                workLatA=dataSnapshot.child("workLocLat").getValue().toString();
                workLonA=dataSnapshot.child("workLocLon").getValue().toString();

                UserEmail = dataSnapshot.child("email").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        setSupportActionBar(toolbar);
        NavigationView navView = findViewById(R.id.navview);


        mdrawer = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this,mdrawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mdrawer.addDrawerListener(toggle);
        updateNavHeader();
        toggle.syncState();
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId())
                {
                    case R.id.Profile:
                      //  info();
                        Intent it = new Intent(MapsActivity.this,profile.class);
                        startActivity(it);
                       // finish();
                        break;
                    case R.id.homeLocation:
                        Intent itt = new Intent(MapsActivity.this,EditLocActivity.class);
                        startActivity(itt);
                    //  Toast.makeText(MapsActivity.this, "Home Clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.workLocation:
                        Intent ittt = new Intent(MapsActivity.this,EditLocWorkActivity.class);
                        startActivity(ittt);
                    //  Toast.makeText(MapsActivity.this, "Work Clicked", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.yourRqsts:
                        Intent ittttt = new Intent(MapsActivity.this,pendingRqstUS.class);
                        startActivity(ittttt);
                        break;


                    case R.id.bookings:
                        Intent itttttt = new Intent(MapsActivity.this,previousBookingsUS.class);
                        startActivity(itttttt);
                        break;
                    case R.id.about:
                       // Toast.makeText(getApplicationContext(),"Hello dd Profile",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.contact:
                        break;
                    case R.id.payment:
                        break;
                    case R.id.logout:

                        Intent itttt = new Intent(MapsActivity.this,UserType.class);
                        itttt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(itttt);
                }
                mdrawer.closeDrawer(GravityCompat.START);
                return true;
            }
                    });

        NearWork = (Button) findViewById(R.id.NearWork);
        NearHome = (Button) findViewById(R.id.NearHome);
        NearCurrent = (Button) findViewById(R.id.NearCurrent);
        AllCenters = (Button) findViewById(R.id.AllCenters);
        WorkLocText = (TextView) findViewById(R.id.searchWorklocation);

        earth =(ImageButton) findViewById(R.id.earthmode);
        getCurrent = (ImageButton) findViewById(R.id.getcurrentloc);
        editHome = (ImageButton) findViewById(R.id.imageButtonHomeLoc);
        editWork = (ImageButton) findViewById(R.id.imageButtonWorkLoc);
        HomeLocText = (TextView) findViewById(R.id.searchHomelocation);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        editHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(MapsActivity.this, "edit home", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(MapsActivity.this,EditLocActivity.class);
                startActivity(it);
            }
        });

        editWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MapsActivity.this,EditLocWorkActivity.class);
                startActivity(it);
                //Toast.makeText(MapsActivity.this, "edit work", Toast.LENGTH_SHORT).show();
            }
        });



        //HomeLocText.setText(EditLocActivity.addressHome);
        //WorkLocText.setText(EditLocWorkActivity.addressWork);




        NearCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                double getClat = marker.getPosition().latitude;
                double getClon = marker.getPosition().longitude;
                latpass = Double.toString(getClat);
                lonpass = Double.toString(getClon);
                AllDayCares = "-1";


                Intent intent = new Intent(MapsActivity.this,showDaycareFilters.class);
                // intent.putExtra("latCC",t1);
               // intent.putExtra("lonCC",t2);
                startActivity(intent);

            }
        });


        NearHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latpass = homeLatA;
                lonpass = homeLonA;
                AllDayCares = "-1";
                Intent intent = new Intent(MapsActivity.this,showDaycareFilters.class);
                startActivity(intent);




            }
        });

        NearWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latpass = workLatA;
                lonpass = workLonA;
                AllDayCares = "-1";
                Intent intent = new Intent(MapsActivity.this,showDaycareFilters.class);
                startActivity(intent);
            }
        });


        AllCenters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latpass = homeLatA;
                lonpass = homeLonA;
                AllDayCares = "1";
                Intent intent = new Intent(MapsActivity.this,showDaycareFilters.class);
                startActivity(intent);

            }
        });


////////////////////// rate review

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
                    bookingEndTime = confirmBookingModel.getEndTime().toString();
                    updateRatingCE=confirmBookingModel.getCenterEmail();
                    if(confirmBookingModel.getUserEmail().toString().equals(UserEmail)){
                        if(checktimings(bookingEndTime,localTime)==true){

                            if(confirmBookingModel.getReview().toString().equals("") && confirmBookingModel.getRating().toString().equals("")){

                                final androidx.appcompat.app.AlertDialog.Builder alert = new androidx.appcompat.app.AlertDialog.Builder(MapsActivity.this);
                                View mView = getLayoutInflater().inflate(R.layout.db_userside_rate_review, null);

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
                                                rating="1";
                                                break;
                                            case 2:
                                                mRatingScale.setText("Need some improvement");
                                                rating="2";
                                                break;
                                            case 3:
                                                mRatingScale.setText("Good");
                                                rating="3";
                                                break;
                                            case 4:
                                                mRatingScale.setText("Great");
                                                rating="4";
                                                break;
                                            case 5:
                                                mRatingScale.setText("Awesome. I love it");
                                                rating="5";
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
                                            Toast.makeText(MapsActivity.this, "Please fill in feedback text box", Toast.LENGTH_LONG).show();
                                        } else {
                                           String review = mFeedback.getText().toString();

                                            reference2.child(ds.getKey().toString()).child("rating").setValue(rating);
                                            reference2.child(ds.getKey().toString()).child("review").setValue(review);


                                           // Log.d("Step1:",updateRatingCE);




                                            reference3 = FirebaseDatabase.getInstance().getReference().child("ConfirmBooking");
                                            reference3.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                                                    model = ds.getValue(ConfirmBookingModel.class);
                                                    center = model.getCenterEmail();
                                                    if((center.equals(updateRatingCE))==true){

                                                        if(model.getRating().isEmpty()==false && model.getReview().isEmpty()==false) {
                                                            ratingC = ratingC+Double.parseDouble(model.getRating().toString());
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

                                            Toast.makeText(MapsActivity.this, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                alertDialog.show();


                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



























        earth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentType = (mMap.getMapType());
             if(currentType == mMap.MAP_TYPE_SATELLITE)
             {
                    mMap.setMapType(mMap.MAP_TYPE_NORMAL);
             }
             else
             {
                 mMap.setMapType(mMap.MAP_TYPE_SATELLITE);
             }
            }
        });
        getCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        getCurrentLocation();

                    }}
            }
        });

        fusedLocationProviderClient = new FusedLocationProviderClient(MapsActivity.this);
        turnOn = (Button) findViewById(R.id.turnon);
        gps = (Button) findViewById(R.id.gps);
        gps.setVisibility(View.GONE);
        turnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOn.setVisibility(View.GONE);
            askLocationPermission();

          //  getCurrentLocation();
            }
        });
        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isGPSenabled();
                getCurrentLocation();
            }
        });
        // Ask Location Permission
        if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                turnOn.setVisibility(View.GONE);
                getCurrentLocation();
            } else {
                turnOn.setVisibility(View.GONE);
                gps.setVisibility(View.VISIBLE);
            }
        }
        else
        {
        //    gps.setVisibility(View.GONE);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        checkGoogleServiceavailable();
        mapFragment.getMapAsync(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(4* 1000);
        locationRequest.setFastestInterval(2000);
        locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {

                        if (marker != null) {
                            marker.remove();
                        }
                    //    Toast.makeText(getApplicationContext(),"Thread"+Thread.currentThread(),Toast.LENGTH_SHORT).show();
                        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.getUiSettings().setCompassEnabled(false);
                       marker =  mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                  //      mMap.getUiSettings().setZoomControlsEnabled(true);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16f));

                        if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                mMap.setMyLocationEnabled(true);
                                mMap.getUiSettings().setMyLocationButtonEnabled(false);

                            }}
                        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
                    }
                }
            }

        };
    }

    private void updateNavHeader() {



        NavigationView navigationView = findViewById(R.id.navview);
        View headerview = navigationView.getHeaderView(0);
        // TextView navname = headerview.findViewById(R.id.navname);
        TextView navmail = headerview.findViewById(R.id.navmail);


        navmail.setText(currentuser.getEmail());
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(31.5204, 74.3587);
        marker =   mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.getUiSettings().setCompassEnabled(false);
        // mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16f));
      //  mMap.getUiSettings().setZoomControlsEnabled(true);
      //  mMap.setMyLocationEnabled(true);
    }
    public void getCurrentLocation() {

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            gps.setVisibility(View.GONE);
        }
        Log.d("Right now", "getCurrentLocation: ");
      //  Toast.makeText(getApplicationContext(),"BAllay",Toast.LENGTH_SHORT).show();
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                if (marker != null) {
                    marker.remove();
                }
           //     Toast.makeText(getApplicationContext(),"DONOE DONE DONE",Toast.LENGTH_SHORT).show();
                LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                marker =   mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.getUiSettings().setCompassEnabled(false);
               // mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16f));

                if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        mMap.setMyLocationEnabled(true);
                        mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }}
              //  fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
              //  fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            }
            else
            {
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
               // fusedLocationProviderClient.removeLocationUpdates(locationCallback);
          //      Toast.makeText(getApplicationContext(),"NAHE NAHE AHE",Toast.LENGTH_SHORT).show();
            }

        });
     //   Toast.makeText(getApplicationContext(),"TERE lvel",Toast.LENGTH_SHORT).show();
    }
    public void checkGoogleServiceavailable()
    {
        GoogleApiAvailability googleapi = GoogleApiAvailability.getInstance();
        int result = googleapi.isGooglePlayServicesAvailable(this);
        if(result ==ConnectionResult.SUCCESS)
        {
        //    Toast.makeText(getApplicationContext(),"GANDO is AVailable",Toast.LENGTH_SHORT).show();
        }
        else if(googleapi.isUserResolvableError(result))
    {
      //  Toast.makeText(getApplicationContext(),"KARLEGA to",Toast.LENGTH_SHORT).show();
    }
        else
        {
       //     Toast.makeText(getApplicationContext(),"HAHA",Toast.LENGTH_SHORT).show();
        }
    }

    public void askLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                getCurrentLocation();
            }
            else
            {
                gps.setVisibility(View.VISIBLE);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                getCurrentLocation();
            }
            else
            {
                gps.setVisibility(View.VISIBLE);
            }
        }
        else
        {

            turnOn.setVisibility(View.VISIBLE);
         //   Toast.makeText(getApplicationContext(),"Need permission to show you Map",Toast.LENGTH_SHORT).show();
        }
    }
    public void isGPSenabled()
    {


            AlertDialog ai = new AlertDialog.Builder(this)
                    .setTitle("GPS Permissions")
                    .setMessage("GPS permission")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivityForResult( new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS ), 1) ;

                        }
                    })
                 //   .setCancelable(false)
                    .show();

   //     Toast.makeText(getApplicationContext(),"ASAS gps enable",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1)
        {
             locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Boolean ans = locationManager.isProviderEnabled(LocationManager. GPS_PROVIDER );
            if(ans)
            {
                finish();
                startActivity(getIntent());
            }
            else
            {

            }
        }
        }

    public void closeKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }

    @Override
    public void onBackPressed() {
        if(mdrawer.isDrawerOpen(GravityCompat.START))
        {
            mdrawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    public void onSearch()
    {
        closeKeyBoard();
        //Toast.makeText(getApplicationContext(),"Working",Toast.LENGTH_SHORT).show();


        if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                turnOn.setVisibility(View.GONE);
                gps.setVisibility(View.GONE);
               // getCurrentLocation();
            } else {
                turnOn.setVisibility(View.GONE);
                gps.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            //    gps.setVisibility(View.GONE);
        }


        String address = HomeLocText.getText().toString();
        List<Address> addresslist = null;
        if(!TextUtils.isEmpty(address))
        {
            Geocoder geocoder = new Geocoder(this);
            try {
                addresslist = geocoder.getFromLocationName(address,6);
                if(addresslist!=null) {
                    for (int i = 0; i < addresslist.size(); i++) {
                        Address addresses = addresslist.get(i);
                        LatLng ltln = new LatLng(addresses.getLatitude(), addresses.getLongitude());
                        if (marker != null) {
                            marker.remove();
                        }
                        mMap.getUiSettings().setCompassEnabled(false);
                       marker = mMap.addMarker(new MarkerOptions().position(ltln).title("Marker in Sydney"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ltln, 12f));

                        if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                   mMap.setMyLocationEnabled(true);
                                mMap.getUiSettings().setMyLocationButtonEnabled(false);

                            }}
                    }
                }
                else
                {
                    Toast.makeText(this,"Locatio not found",Toast.LENGTH_SHORT);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

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
