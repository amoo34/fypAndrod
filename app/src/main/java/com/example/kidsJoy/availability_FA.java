package com.example.kidsJoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.service.autofill.FieldClassification;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class availability_FA extends AppCompatActivity implements View.OnClickListener {


    TextView status;
    Button enable,disable;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase fdatabase;
    DatabaseReference ref,ref2;
    DayCaremodel center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability__f);


        fdatabase = FirebaseDatabase.getInstance();
        ref = fdatabase.getReference("DayCares");
        center = new DayCaremodel();


        status = (TextView) findViewById(R.id.statusTxtAAFA);
        enable = (Button) findViewById(R.id.enableAvailability);
        disable = (Button) findViewById(R.id.disableAvailability);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ref2 = ref.child(userId);
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable <DataSnapshot> allData = dataSnapshot.getChildren();
               status.setText(dataSnapshot.child("availability").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        enable.setOnClickListener(this);
        disable.setOnClickListener(this);


    }




    @Override
    public void onClick(View v){
        if(v==enable){

            String status = "Enabled";
            String currentUserID = ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getKey();
            Map<String, Object> update = new HashMap<>();
            update.put("/" + currentUserID + "/availability", status);
            ref.updateChildren(update);


            Toast.makeText(this, "Availability Enabled", Toast.LENGTH_SHORT).show();
            enable.setVisibility(View.GONE);
            disable.setVisibility(View.VISIBLE);
        }
        if(v==disable){

            String status = "Disabled";
            String currentUserID = ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getKey();
            Map<String, Object> update = new HashMap<>();
            update.put("/"+currentUserID+"/availability",status);
            ref.updateChildren(update);

            Toast.makeText(this, "Availability Disabled", Toast.LENGTH_SHORT).show();
            enable.setVisibility(View.VISIBLE);
            disable.setVisibility(View.GONE);
        }

    }
}
