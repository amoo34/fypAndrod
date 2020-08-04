package com.example.kidsJoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Daycarepassword extends AppCompatActivity {
    private EditText passwordEmail;
    private Button resetPassword;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daycarepassword);

        passwordEmail= (EditText)findViewById(R.id.EmailD);
        resetPassword= (Button)findViewById(R.id.button3);
        firebaseAuth=FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = passwordEmail.getText().toString().trim();
                if (useremail.equals("")) {
                    Toast.makeText(Daycarepassword.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Daycarepassword.this, "Password Reset Email Sent", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Daycarepassword.this, DayCareLogin.class));
                            } else {
                                Toast.makeText(Daycarepassword.this, "Error in Sending Massage", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}