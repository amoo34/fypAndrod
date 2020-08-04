package com.example.kidsJoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

public class DayCareProfile extends AppCompatActivity {

    TextView dPname,dPemail,dPnumber,dPaddress,dPrating;
    Button editB;
    private FirebaseDatabase fdatabase;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_care_profile);

        dPname = (TextView)findViewById(R.id.dPname);
        dPemail = (TextView)findViewById(R.id.dPemail);
        dPnumber = (TextView)findViewById(R.id.dPnumber);
        dPaddress = (TextView)findViewById(R.id.dPaddress);
        dPrating = (TextView) findViewById(R.id.ratingProfileDC);

        fdatabase = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = fdatabase.getReference("DayCares").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable <DataSnapshot> allData = dataSnapshot.getChildren();


                String lat = dataSnapshot.child("latitude").getValue().toString();
                String lon = dataSnapshot.child("longitude").getValue().toString();
                double lata = Double.parseDouble(lat);
                double lona = Double.parseDouble(lon);
                String address = getCompleteAddress(lata,lona);


                dPname.setText(dataSnapshot.child("dname").getValue().toString());
                dPemail.setText(dataSnapshot.child("demail").getValue().toString());
                dPnumber.setText(dataSnapshot.child("dphoneNumber").getValue().toString());
                dPrating.setText(dataSnapshot.child("overAllRating").getValue().toString());
                dPaddress.setText(address);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private String getCompleteAddress(double x, double y)       //method for conversion of latitude and longitude to address string
    {

        String address = "";
        Geocoder geocoder = new Geocoder(DayCareProfile.this, Locale.getDefault());


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
