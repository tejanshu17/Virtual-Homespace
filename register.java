package com.example.login2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    EditText Username,Mail,Password,phone;
    Button register;
    TextView loginAcc;
    TextInputLayout phone_layout;
    ImageButton google_btn;
    FirebaseAuth mAuth;
    public static final String TAG = "TAG";
    FirebaseFirestore fStore;
    FirebaseUser CurrentUser;
    DatabaseReference ref;
    String userID;
    private GoogleSignInClient google;
    private int RC_SIGN_IN=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Username = findViewById(R.id.Username);
        Mail = findViewById(R.id.Mail);
        Password = findViewById(R.id.Password);
        phone = findViewById(R.id.Phone);
        register = findViewById(R.id.register);
        google_btn = findViewById(R.id.sign_in_google);
        loginAcc= findViewById(R.id.login);
        phone_layout = findViewById(R.id.Phone_layout);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Username.onEditorAction(EditorInfo.IME_ACTION_DONE);
                phone.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Mail.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Password.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //google sign-in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        google = GoogleSignIn.getClient(this,gso);

        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        //check whether already logged in
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


        //phone edittext layout
        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    phone_layout.setHint("Enter Phone no");
                } else if (hasFocus = false) {
                    phone_layout.setHint("+91 9821060xxx");
                }
                else{
                    phone_layout.setHint("+91 9821060xxx");
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Mail.getText().toString().trim();
                final String password = Password.getText().toString().trim();
                final String fullname = Username.getText().toString();
                final String phoneno = phone.getText().toString();


                if (TextUtils.isEmpty(email)) {
                    Mail.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Password.setError("Password is Required");
                    return;
                }

                if (Password.length() < 6) {
                    Password.setError("Password must be more than 6 Characters");
                }

           //     ValidatephoneNumber(phoneno);


                //register the user in firebase; verify user email id
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //send verification code
                            FirebaseUser fuser = mAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(register.this, "Verification Email Has Been Sent", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"onFailure: Email not sent"+e.getMessage());
                                }
                            });

                            Toast.makeText(register.this, "User Created", Toast.LENGTH_SHORT).show();
                            //FirebaseUser user = mAuth.getCurrentUser();
                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("Username").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("Username", fullname);
                            user.put("Mail", email);
                            user.put("phone", phoneno);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });

                            //Real-time database
                            CurrentUser = task.getResult().getUser();
                            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                            ref = databaseReference.child(CurrentUser.getUid());
                            ref.child("Email").setValue(email);
                            ref.child("Name").setValue(fullname);
                            ref.child("Phone").setValue(phoneno);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(register.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        loginAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), login.class));
            }
        });
    }

    /*Validate phoneno
    private void ValidatephoneNumber(final String phone){
        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if((dataSnapshot.child("Users").child(phone).exists())){
                    Toast.makeText(register.this, "This "+ phone + "already exist", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(register.this,login.class);
                    startActivity(intent);
                }
                else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

    private void signIn(){
        Intent signInIntent = google.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         if(requestCode == RC_SIGN_IN){
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount>completedTask){
        try{
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        }catch (ApiException e){
            Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acc){
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acc.getIdToken(), null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(register.this, "Successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    //updateUI(user);
                }
                else {
                    Toast.makeText(register.this, "Failed", Toast.LENGTH_SHORT).show();
                    //updateUI(null);
                }
            }
        });
    }

   /* private void updateUI(FirebaseUser fUser){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account != null){
            String personname = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamiltyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();

            Toast.makeText(this, "DONE", Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(getCurrentFocus()!=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}