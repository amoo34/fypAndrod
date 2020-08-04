package com.example.kidsJoy;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CenterMarkerOnUserSide extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseReference referenceX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_marker_on_user_side);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);






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




        double latDCD = Double.parseDouble(dayCareDetail.latS);
        Log.d("latDCD",""+latDCD);
        double lonDCD = Double.parseDouble(dayCareDetail.lonS);


        double latMA = Double.parseDouble(MapsActivity.latpass);
        Log.d("latMA",""+latMA);
        double lonMA = Double.parseDouble(MapsActivity.lonpass);


        LatLng from_Latlng=new LatLng(latDCD,lonDCD);
        LatLng to_Latlong=new LatLng(latMA,lonMA);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(from_Latlng);
        builder.include(to_Latlong);
        LatLngBounds bounds = builder.build();
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100), 2000, null);
        LatLng markerDCD = new LatLng(latDCD, lonDCD);
        mMap.addMarker(new MarkerOptions().position(markerDCD).title(dayCareDetail.DCDName).snippet("and snippet")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        LatLng markerMA = new LatLng(latMA, lonMA);
        mMap.addMarker(new MarkerOptions().position(markerMA).title("Your Selected Location").snippet("and snippet")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));





//        double latD = Double.parseDouble(dayCareDetail.latS);
  //      double longD = Double.parseDouble(dayCareDetail.lonS);
    //    LatLng markerDCD = new LatLng(latD, longD);
      //  mMap.addMarker(new MarkerOptions().position(markerDCD).title(dayCareDetail.DCDName));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(markerDCD));
    }
}
