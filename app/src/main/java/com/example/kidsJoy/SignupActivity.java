package com.example.kidsJoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

public class SignupActivity extends AppCompatActivity {
    private EditText email,password,name,phonenumber;
    private TextView login;
    private Button signup;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        name = (EditText) findViewById(R.id.editText3);
        email = (EditText) findViewById(R.id.editText4);
        phonenumber = (EditText) findViewById(R.id.editText5);
        password = (EditText) findViewById(R.id.enterpass);
        signup = (Button) findViewById(R.id.button4);
        login=(TextView) findViewById(R.id.already);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
        mDialog = new ProgressDialog(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Email = email.getText().toString();
                String PhoneNumber = phonenumber.getText().toString();
                String Password = password.getText().toString();
                if(TextUtils.isEmpty(Name))
                {
                    name.setError("Required Name");
                    return;
                }

                if(TextUtils.isEmpty(Email))
                {
                    email.setError("Required Email");
                    return;
                }
                if(TextUtils.isEmpty(PhoneNumber))
                {
                    phonenumber.setError("Required Phone Number");
                    return;
                }
                if(TextUtils.isEmpty(Password))
                {
                    password.setError("Required passeord");
                    return;
                }
                mDialog.setMessage("Processing....");
                mDialog.show();
                mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            String homeLocation="";
                            String workLocation="";
                            String homeLocLat="";
                            String homeLocLon="";
                            String workLocLat="";
                            String workLocLon="";
                            usermodal myuser = new usermodal(Name,Email,PhoneNumber,Password,homeLocation,workLocation,homeLocLat,homeLocLon,workLocLat,workLocLon);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(myuser)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                sendEmailVerification();

                                                mDialog.dismiss();
                                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });


                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Some thing went Wrong",Toast.LENGTH_SHORT).show();
                        }
                    }

                });

            }
        });
    }




    private  void sendEmailVerification(){
        FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(SignupActivity.this,"Email verification sent",Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        finish();
                        startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                    }else{
                        Toast.makeText(SignupActivity.this,"Email verification not sent",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }


}
