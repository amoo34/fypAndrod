package com.example.kidsJoy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class timings_FA extends AppCompatActivity implements View.OnClickListener {

    EditText Otime,Ctime;
    TextView OtimeT,CtimeT;
    Button update;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase fdatabase;
    DatabaseReference ref;
    DayCaremodel center;


    private int mhour,mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timings__f);

        fdatabase = FirebaseDatabase.getInstance();
        ref = fdatabase.getReference("DayCares");
        center = new DayCaremodel();

        Otime = (EditText) findViewById(R.id.openingClock);
        Ctime = (EditText) findViewById(R.id.closingClock);
        update = (Button) findViewById(R.id.updateTime);
        OtimeT = (TextView) findViewById(R.id.openingText);
        CtimeT = (TextView) findViewById(R.id.closingText);

        OtimeT.setOnClickListener(this);
        CtimeT.setOnClickListener(this);
        Otime.setOnClickListener(this);
        Ctime.setOnClickListener(this);
        update.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v==OtimeT || v==Otime){
            final Calendar calendar = Calendar.getInstance();
            mhour= calendar.get(Calendar.HOUR_OF_DAY);
            mMinute= calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Otime.setText(hourOfDay+":"+minute);
                }
            },mhour,mMinute,false);
            timePickerDialog.show();
        }
        if(v==CtimeT || v==Ctime){
            final Calendar calendar = Calendar.getInstance();
            mhour= calendar.get(Calendar.HOUR_OF_DAY);
            mMinute= calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Ctime.setText(hourOfDay+":"+minute);
                }
            },mhour,mMinute,false);
            timePickerDialog.show();
        }


        if(v==update){

            String dOtime = Otime.getText().toString();
            String dCtime = Ctime.getText().toString();


            String currentUserID = ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getKey();

            // Toast.makeText(getApplicationContext(), ""+childpath, Toast.LENGTH_SHORT).show();

            Map<String,Object> update = new HashMap<>();


            update.put("/"+currentUserID+"/dOtime",dOtime);
            update.put("/"+currentUserID+"/dCtime",dCtime);

            ref.updateChildren(update);
        }

    }














}
