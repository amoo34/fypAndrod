package com.example.kidsJoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class complainUserSide extends AppCompatActivity {


    DatabaseReference reference;
    ConfirmBookingModel confirmBookingModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_user_side);

        EditText textComplain = (EditText) findViewById(R.id.complainEnter);
        Button sendComplain = (Button) findViewById(R.id.complainSend);



        String BookingID = getIntent().getStringExtra("BookingID");
        //textComplain.setText(BookingID);

        sendComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {







                if(textComplain.getText().toString().isEmpty()){
                    Toast.makeText(complainUserSide.this, "Kindly Enter your Complain", Toast.LENGTH_SHORT).show();

                }

                if(textComplain.getText().toString().isEmpty()==false) {

                    reference = FirebaseDatabase.getInstance().getReference().child("ConfirmBooking");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                confirmBookingModel = ds.getValue(ConfirmBookingModel.class);

                                if (confirmBookingModel.getBookingID().toString().equals(BookingID)) {

                                    if (confirmBookingModel.getReview().toString().isEmpty() && confirmBookingModel.getRating().toString().isEmpty()) {

                                        Toast.makeText(complainUserSide.this, "Kindly Rate And Review First", Toast.LENGTH_LONG).show();


                                    }
                                    if (confirmBookingModel.getReview().toString().isEmpty() == false && confirmBookingModel.getRating().toString().isEmpty() == false && confirmBookingModel.getComplain().toString().isEmpty() == false) {

                                        Toast.makeText(complainUserSide.this, "Your Complain is Already Submitted", Toast.LENGTH_SHORT).show();

                                    }
                                    if (confirmBookingModel.getReview().toString().isEmpty() == false && confirmBookingModel.getRating().toString().isEmpty() == false && confirmBookingModel.getComplain().toString().isEmpty() == true) {


                                        reference.child(ds.getKey().toString()).child("complain").setValue(textComplain.getText().toString());
                                        break;

                                    }


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





    }
}
