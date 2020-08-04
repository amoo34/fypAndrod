package com.example.kidsJoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DayCareSignup extends AppCompatActivity {


    private EditText name,email,number,location,password;
    private TextView Login;

    private Button signup;
    FirebaseAuth mAuth;
    FirebaseDatabase fdatabase;
    ArrayList<String> addressname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_care_signup);
        mAuth = FirebaseAuth.getInstance();
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        number = (EditText) findViewById(R.id.number);
        location = (EditText) findViewById(R.id.location);
        password = (EditText) findViewById(R.id.mpassword);
        signup = (Button) findViewById(R.id.msignup);
        Login=(TextView)findViewById(R.id.haveaccount);
        addressname = new ArrayList<String>();
        Intent loc = getIntent();
        String act = loc.getStringExtra("activity");
        if(act != null) {
            addressname = loc.getStringArrayListExtra("address");
            location.setText(addressname.get(0));
            name.setText(loc.getStringExtra("dname"));
            Toast.makeText(getApplicationContext(),loc.getStringExtra("dname"),Toast.LENGTH_SHORT).show();
            email.setText(loc.getStringExtra("dgmail"));
            number.setText(loc.getStringExtra("dnumber"));
        }
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DayCareSignup.this,DayCareLocation.class);


                it.putExtra("name",  name.getText().toString());
                it.putExtra("email", email.getText().toString());
                it.putExtra("number",  number.getText().toString());
                it.putExtra("password",  password.getText().toString());
                //        it.putExtra("longitude",mMap.getCameraPosition().target.longitude);
                startActivity(it);
            }
        });
        location.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Intent it = new Intent(DayCareSignup.this,DayCareLocation.class);
                it.putExtra("name",  name.getText().toString());
                it.putExtra("email", email.getText().toString());
                it.putExtra("number",  number.getText().toString());
                it.putExtra("password",  password.getText().toString());
                //        it.putExtra("longitude",mMap.getCameraPosition().target.longitude);
                startActivity(it);
            }
        });
Login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(DayCareSignup.this,DayCareLogin.class);
    }
});

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dayCarename = name.getText().toString();
                String dayCareemail = email.getText().toString();
                String dayCarenumber = number.getText().toString();
                double dayCarelatitudeX = (loc.getDoubleExtra("latitude",0));
                double dayCarelongitudeX = (loc.getDoubleExtra("longitude",0));
                String dayCarepassword = password.getText().toString();
                String dOpeningtime = "";
                String dClosingtime = "";
                String dRates= "";
                String dRes= "";
                String dAvail="Enabled";
                String dOverAllRating="";
                String dayCarelatitude = String.valueOf(dayCarelatitudeX);
                String dayCarelongitude = String.valueOf(dayCarelongitudeX);



                if(TextUtils.isEmpty(name.getText().toString()))

                {
                    name.setError("Empty Text Field ");
                    return;

                }



                if(TextUtils.isEmpty(email.getText().toString()))

                {
                    email.setError("Empty Text Field ");
                    return;

                }


                if(TextUtils.isEmpty(number.getText().toString()))

                {
                    number.setError("Empty Text Field ");
                    return;

                }


                if(TextUtils.isEmpty(password.getText().toString()))

                {
                    password.setError("Empty Text Field ");
                    return;

                }





















                mAuth.createUserWithEmailAndPassword(dayCareemail,dayCarepassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            DayCaremodel dcmodel = new DayCaremodel(dayCarename,dayCareemail,dayCarenumber,dayCarelatitude,dayCarelongitude,dayCarepassword,dOpeningtime,dClosingtime,dRates,dRes,dAvail,dOverAllRating);
                            FirebaseDatabase.getInstance().getReference("DayCares")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(dcmodel)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                sendEmailVerification();
                                                mAuth.signOut();
                                                finish();

                                                Intent it = new Intent(getApplicationContext(),DayCareLogin.class);
                                                startActivity(it);
                                            }
                                            else
                                            {
                                                Toast.makeText(getApplicationContext(), " Not Done", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });


                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), " Wrong Information or Account Exist", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

    private  void sendEmailVerification(){
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(DayCareSignup.this,"Email verification sent",Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        finish();
                        startActivity(new Intent(DayCareSignup.this,DayCareLogin.class));
                    }else{
                        Toast.makeText(DayCareSignup.this,"Email verification not sent",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}
