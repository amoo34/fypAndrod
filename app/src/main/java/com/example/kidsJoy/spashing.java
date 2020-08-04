package com.example.kidsJoy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;


public class spashing extends AppCompatActivity {
    Handler handler;
    Runnable runnable;
    ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_spashing);
        img=findViewById(R.id.beautiful);

        img.animate().alpha(4000).setDuration(0);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent dsp = new Intent(spashing.this,UserType.class);
                startActivity(dsp);
                finish();
            }
        },4000);

    }
}
