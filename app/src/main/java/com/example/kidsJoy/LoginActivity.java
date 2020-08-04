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

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button login;
    private TextView signup;
    FirebaseAuth mAuth;
    private TextView forgottpassword;
    private ProgressDialog mdialog;

    String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editPas);
        forgottpassword=(TextView) findViewById(R.id.fforget);
        login = (Button) findViewById(R.id.button);
        signup = (TextView) findViewById(R.id.signup2);
        mAuth = FirebaseAuth.getInstance();
        mdialog = new ProgressDialog(this);

        //  forgottpassword = (TextView) findViewById(R.id.forget);
        // FirebaseUser user = mAuth.getCurrentUser();
        //if (user != null) {
        //   finish();
        //  startActivity(new Intent(LoginActivity.this, MapsActivity.class));
        forgottpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, password_activity.class));
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();
                if (TextUtils.isEmpty(Email)) {
                    email.setError("Required Email");
                    return;
                }
                if (TextUtils.isEmpty(Password)) {
                    password.setError("Required Password");
                    return;
                }
                mdialog.setMessage("Processing");
                mdialog.show();


                mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            checkEmailVerification();
                           // mAuth.signOut();
                            mdialog.dismiss();
                            //    info();
                            //startActivity(new Intent(getApplicationContext(), MapsActivity.class));

                        } else {
                            Toast.makeText(getApplicationContext(), "Wrong Details", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }


        });
    }





    private void checkEmailVerification() {
        FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if (emailflag) {
            finish();
            startActivity(new Intent(LoginActivity.this, MapsActivity.class));
        } else {
            Toast.makeText(this, "Email not verified", Toast.LENGTH_SHORT).show();

        }


    }





}
