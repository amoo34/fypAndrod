package com.example.kidsJoy;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DayCareLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button confirmButton;
    Marker mCenterMarker;
    Location location;
    private LocationManager locationManager;
    private Button searchButton,searchDaycare;
    private EditText enterLocation;
    public int x=0;
    ArrayList<String> addressFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_care_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        searchButton = (Button) findViewById(R.id.NearHome);
        enterLocation = (EditText)findViewById(R.id.searchHomelocation);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        confirmButton = (Button) findViewById(R.id.confirm);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  onSearch();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mMap.getCameraPosition();

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                List<Address> addresses = null;

                String errorMessage;
                try {
                    addresses = geocoder.getFromLocation(
                            mMap.getCameraPosition().target.latitude,
                            mMap.getCameraPosition().target.longitude,
                            // In this sample, get just a single address.
                            1);
                } catch (IOException ioException) {
                    // Catch network or other I/O problems.
                   // errorMessage = getString(R.string.service_not_available);

                } catch (IllegalArgumentException illegalArgumentException) {
                    // Catch invalid latitude or longitude values.
                  //  errorMessage = getString(R.string.invalid_lat_long_used);

                }

                    Address address = addresses.get(0);
                     addressFragments = new ArrayList<String>();

                    // Fetch the address lines using getAddressLine,
                    // join them, and send them to the thread.
                    for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                        addressFragments.add(address.getAddressLine(i));

        }

                    Intent ic = getIntent();
                Intent it = new Intent(getApplicationContext(),DayCareSignup.class);
                    it.putStringArrayListExtra("address",addressFragments);
                  //  String name = ic.getStringExtra("name");
                    it.putExtra("dname",ic.getStringExtra("name"));
                    it.putExtra("dgmail",ic.getStringExtra("email"));
                    it.putExtra("dnumber",ic.getStringExtra("number"));

                    it.putExtra("activity","locat");
                    it.putExtra("latitude",mMap.getCameraPosition().target.latitude);
                    it.putExtra("longitude",mMap.getCameraPosition().target.longitude);
                    startActivity(it);
            }});
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(31.5204, 74.3587);
         mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Lahore").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15f));


        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

         //       Toast.makeText(getApplicationContext(),"sadas" +mMap.getCameraPosition(),Toast.LENGTH_SHORT).show();
            }
        });

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                if (mCenterMarker != null) {
                //    mCenterMarker.remove();
                    mMap.clear();
                }

                CameraPosition test = mMap.getCameraPosition();
                //Assign mCenterMarker reference:
                mCenterMarker = mMap.addMarker(new MarkerOptions().position(mMap.getCameraPosition().target).anchor(0.5f, .05f).title("Test"));

            }
        });


    }
    public void closeKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }
    public void onSearch()
    {
        closeKeyBoard();



        String address = enterLocation.getText().toString();
        List<Address> addresslist = null;
        if(!TextUtils.isEmpty(address))
        {
            Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
            Geocoder geocoder = new Geocoder(this);
            try {
                addresslist = geocoder.getFromLocationName(address,6);
                if(addresslist!=null) {
                    Toast.makeText(this, "Hello jan", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < addresslist.size(); i++) {
                        Address addresses = addresslist.get(i);
                        LatLng ltln = new LatLng(addresses.getLatitude(), addresses.getLongitude());
                        if (mCenterMarker != null) {
                            mCenterMarker.remove();
                        }
                        mMap.getUiSettings().setCompassEnabled(false);
                        mCenterMarker = mMap.addMarker(new MarkerOptions().position(ltln).title("Marker in Sydney"));
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
}
