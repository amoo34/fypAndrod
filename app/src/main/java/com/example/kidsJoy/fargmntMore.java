package com.example.kidsJoy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class fargmntMore extends Fragment {

    public EditText price,timing,description;
    Button confirm;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase fdatabase;
    DatabaseReference ref;
    DayCaremodel center;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_more,container,false);


        fdatabase = FirebaseDatabase.getInstance();
        ref = fdatabase.getReference("DayCares");
        center = new DayCaremodel();
        price = (EditText) v.findViewById(R.id.B_price);
        timing = (EditText) v.findViewById(R.id.B_timing);
        description = (EditText) v.findViewById(R.id.B_description);
        confirm = (Button) v.findViewById(R.id.B_confirm);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dPrice = price.getText().toString();
                String dTime = timing.getText().toString();
                String dDesc = description.getText().toString();



                String childpath = ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getKey();

                // Toast.makeText(getApplicationContext(), ""+childpath, Toast.LENGTH_SHORT).show();

                Map<String,Object> update = new HashMap<>();


                update.put("/"+childpath+"/price",dPrice);
                update.put("/"+childpath+"/timing",dTime);
                update.put("/"+childpath+"/description",dDesc);
                update.put("/"+childpath+"/description",dDesc);



                ref.updateChildren(update);

            }
        });







        return v;
    }
}
