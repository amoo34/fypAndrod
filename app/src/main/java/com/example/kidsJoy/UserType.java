package com.example.kidsJoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserType extends AppCompatActivity {

    private Button user,business;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);
        user = (Button) findViewById(R.id.userLogin);
        business = (Button) findViewById(R.id.busineesLogin);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent user = new Intent(UserType.this,LoginActivity.class);
                startActivity(user);
            }
        });
        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bus = new Intent(UserType.this,DayCareLogin.class);
                startActivity(bus);
            }
        });
    }
}
