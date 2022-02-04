package com.example.login2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmailVerify extends AppCompatActivity {
    private EditText email;
    DatabaseReference databaseReference;
    FirebaseUser CurrentUser;
    String uid;
    FirebaseAuth mAuth;
    TextView txtemail,verified;
    Button VerifyNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_verify);

        email = findViewById(R.id.EnterEmail);
        txtemail = findViewById(R.id.email);
        VerifyNow = findViewById(R.id.verify);
        verified = findViewById(R.id.verified);
        CurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = CurrentUser.getUid();
        //get email-id
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String email_id = dataSnapshot.child("Email").getValue().toString();
                email.setText(email_id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(!CurrentUser.isEmailVerified()){

            VerifyNow.setVisibility(View.VISIBLE);
            verified.setVisibility(View.GONE);

            VerifyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    CurrentUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(v.getContext(), "Verification Email Has Been Sent", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag","onFailure: Email not sent"+e.getMessage());
                        }
                    });

                }
            });
        }
        else{
            verified.setVisibility(View.VISIBLE);
            VerifyNow.setVisibility(View.GONE);
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(getCurrentFocus()!=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
