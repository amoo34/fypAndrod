package com.example.kidsJoy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RateRes_FA extends AppCompatActivity implements View.OnClickListener {

    EditText rates,restrictions;
    Button ratesB,resB;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase fdatabase;
    DatabaseReference ref;
    DayCaremodel center;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_res__f);

        fdatabase = FirebaseDatabase.getInstance();
        ref = fdatabase.getReference("DayCares");
        center = new DayCaremodel();

        rates = (EditText)findViewById(R.id.rate);
        restrictions= (EditText)findViewById(R.id.res);
        ratesB = (Button)findViewById(R.id.rateButton);
        resB= (Button)findViewById(R.id.resButton);

        ratesB.setOnClickListener(this);
        resB.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if (v == ratesB)
        {
            String dratesS = rates.getText().toString();
            if(dratesS!=null && isDouble(dratesS)==true)
            {
            double drates = Double.parseDouble(dratesS);
            String currentUserID = ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getKey();
            Map<String, Object> update = new HashMap<>();
            update.put("/" + currentUserID + "/dRates", dratesS);
            ref.updateChildren(update);
            }
            else
                {
                Toast.makeText(this, "rates not correct", Toast.LENGTH_SHORT).show();
            }
        }
        if (v == resB)
        {
            String dres = restrictions.getText().toString();
            if (dres != null) {
                String currentUserID = ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getKey();
                Map<String, Object> update = new HashMap<>();
                update.put("/" + currentUserID + "/dRestrections", dres);
                ref.updateChildren(update);
            }
        }

    }



    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }

    }
}
