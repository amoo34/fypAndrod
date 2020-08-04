package com.example.kidsJoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    private FirebaseDatabase fdatabase;
    private DatabaseReference reference;
    usermodal user;
    TextView t1,t2,t3,t4,t5,t6;
    Thread thread;
    private ProgressDialog mdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        t1 = (TextView) findViewById(R.id.name);
        t2 = (TextView) findViewById(R.id.mobile);
        t3 = (TextView) findViewById(R.id.email);
      //  t4 = (TextView) findViewById(R.id.extView5);

        Toolbar toolbar = (Toolbar) findViewById(R.id.okok);
        setSupportActionBar(toolbar);
       // mdialog = new ProgressDialog(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MapsActivity.class));
            }
        });
        fdatabase = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = fdatabase.getReference("Users").child(userId);
        String name = fdatabase.getReference("Users").child(userId).child("name").getKey();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable <DataSnapshot> allData = dataSnapshot.getChildren();


                       // Toast.makeText(getApplicationContext(),"ere" + UserName,Toast.LENGTH_SHORT).show();
                      //  String UserName = currentUser.child("email").getValue().toString();
                        t1.setText(dataSnapshot.child("name").getValue().toString());
                        t2.setText(dataSnapshot.child("phoneNumber").getValue().toString());
                        t3.setText(dataSnapshot.child("email").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
