package com.example.login2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStructure;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    TextView NotVerify;
    Button VerifyNow;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userid;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  NotVerify = findViewById(R.id.NotVerify);
        //VerifyNow = findViewById(R.id.VerifyNow);
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        loadFragment(new Fragment_home(this));


        userid = mAuth.getCurrentUser().getUid();
        final FirebaseUser user = mAuth.getCurrentUser();

       /* if(!user.isEmailVerified()){
            VerifyNow.setVisibility(View.VISIBLE);
            NotVerify.setVisibility(View.VISIBLE);

            VerifyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
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
            VerifyNow.setVisibility(View.GONE);
            NotVerify.setVisibility(View.GONE);
        }*/
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment!=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Fragmentcontainer,fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(getCurrentFocus()!=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch(item.getItemId()){

            case R.id.homeFragment:
                fragment = new Fragment_home(this);
                break;

            case R.id.searchFragment:
                fragment = new Fragment_search(this);
                break;

            case R.id.wishlistFragment:
                fragment = new Fragment_wishlist(this);
                break;

            case R.id.cartFragment:
                fragment = new Fragment_cart(this);
                break;

            case R.id.accountFragment:
                fragment = new Fragment_account(this);
                break;
        }

        return loadFragment(fragment);
    }
}
