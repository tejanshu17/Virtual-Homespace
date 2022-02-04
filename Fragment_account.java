package com.example.login2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Fragment_account extends androidx.fragment.app.Fragment {

    TextView title_name;
    DatabaseReference databaseReference;
    FirebaseUser CurrentUser;
    String uid;
    Button order,wishlist,verifyemail,feedback,legal,contact,logout;
    FirebaseAuth mAuth;
    Activity activity;
    public Fragment_account(Activity activity){
        this.activity = activity;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.account,container,false);
        title_name = v.findViewById(R.id.title);
        //order = v.findViewById(R.id.order_account);
        wishlist = v.findViewById(R.id.wishlist_account);
        verifyemail = v.findViewById(R.id.emailVerify_account);
        feedback = v.findViewById(R.id.feedback_account);
        legal = v.findViewById(R.id.legal_account);
        contact = v.findViewById(R.id.contact_account);
        logout = v.findViewById(R.id.logout_account);

        CurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = CurrentUser.getUid();
        //Hello Name
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String title = dataSnapshot.child("Name").getValue().toString();
                title = "Hello ,  " + title;
                title_name.setText(title);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //final FirebaseUser user = mAuth.getCurrentUser();
        verifyemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),EmailVerify.class));
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),Feedback.class));
            }
        });

        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity1 = (AppCompatActivity) view.getContext();
                Fragment_wishlist fragment_wishlist = new Fragment_wishlist(activity1);
                activity1.getSupportFragmentManager().beginTransaction().replace(R.id.Fragmentcontainer,fragment_wishlist).addToBackStack(null).commit();

            }
        });


        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),ContactUS.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(),login.class));
            }
        });

        return v;
    }
}
