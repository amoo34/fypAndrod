package com.example.kidsJoy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class fargmntAvailability extends Fragment {

    Button timings, adjustAvailability,rates_restrictions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_availability,container,false);

        timings = (Button) v.findViewById(R.id.OCtimings);
        adjustAvailability = (Button) v.findViewById(R.id.ajustAvailibility);
        rates_restrictions = (Button) v.findViewById(R.id.ratestAndRes);

        timings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(),timings_FA.class);
                startActivity(it);
            }
        });

        rates_restrictions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(),RateRes_FA.class);
                startActivity(it);
            }
        });

        adjustAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(),availability_FA.class);
                startActivity(it);
            }
        });




        return v;

    }
}
