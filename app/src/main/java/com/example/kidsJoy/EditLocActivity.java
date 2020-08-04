package com.example.kidsJoy;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EditLocActivity extends FragmentActivity implements OnMapReadyCallback {


    String latpassEditHome;
    String lonpassEditHome;
    double latA,lngA;
    String addressHome;

    FirebaseDatabase fdatabase;
    DatabaseReference ref;
    usermodal usermodalA;


    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    SearchView searchView;
    private ImageButton getCurrent;
    Button confirmEditLoc;
    private LocationManager locationManager;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    Marker marker;
    public FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_loc);


        fdatabase = FirebaseDatabase.getInstance();
        ref = fdatabase.getReference("Users");
        usermodalA = new usermodal();



        searchView = findViewById(R.id.sv_editLoc);
        confirmEditLoc = findViewById(R.id.confirmEditLoc);
        getCurrent = (ImageButton) findViewById(R.id.IBgetCrnt);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapEditLoc);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        fusedLocationProviderClient = new FusedLocationProviderClient(EditLocActivity.this);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                if(location != null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(EditLocActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                    latA = address.getLatitude();
                    lngA = address.getLongitude();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);




        getCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        getCurrentLocation();

                    }}
            }
        });


        confirmEditLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    latpassEditHome = String.valueOf(latA);
                    lonpassEditHome = String.valueOf(lngA);

                    addressHome = getCompleteAddress(latA,lngA);



                String currentUserID = ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getKey();
                Map<String, Object> update = new HashMap<>();
                update.put("/" + currentUserID + "/homeLocation", addressHome);
                update.put("/" + currentUserID + "/homeLocLat", latpassEditHome);
                update.put("/" + currentUserID + "/homeLocLon", lonpassEditHome);;
                ref.updateChildren(update);
                Toast.makeText(EditLocActivity.this, "Added in DataBase", Toast.LENGTH_SHORT).show();



                Intent it = new Intent(EditLocActivity.this,MapsActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(it);

            }
        });

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
                        marker =  mMap.addMarker(new MarkerOptions().position(sydney).title(""));
                        //      mMap.getUiSettings().setZoomControlsEnabled(true);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16f));

                        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
}


    public void getCurrentLocation() {


        Log.d("Right now", "getCurrentLocation: ");
        //  Toast.makeText(getApplicationContext(),"BAllay",Toast.LENGTH_SHORT).show();
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                if (marker != null) {
                    marker.remove();
                }
                //     Toast.makeText(getApplicationContext(),"DONOE DONE DONE",Toast.LENGTH_SHORT).show();
                LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                marker =   mMap.addMarker(new MarkerOptions().position(sydney).title(""));
                latA = marker.getPosition().latitude;
                lngA = marker.getPosition().longitude;
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

    private String getCompleteAddress(double x, double y)       //method for conversion of latitude and longitude to address string
    {

        String address = "";
        Geocoder geocoder = new Geocoder(EditLocActivity.this, Locale.getDefault());


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



}
