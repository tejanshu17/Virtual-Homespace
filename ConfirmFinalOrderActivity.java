package com.example.login2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, addressEditText, cityEditText;
    private Button confirmorderbtn;
    FirebaseUser CurrentUser;
    String uid;
    private String totalAmount = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        totalAmount = getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Rs. "+totalAmount, Toast.LENGTH_SHORT).show();

        nameEditText = (EditText) findViewById(R.id.Shipment_Name);
        phoneEditText = (EditText) findViewById(R.id.Shipment_phone_number);
        addressEditText = (EditText) findViewById(R.id.Shipment_address);
        cityEditText = (EditText) findViewById(R.id.Shipment_city);
        confirmorderbtn = (Button) findViewById(R.id.confirm_final_order_btn);
        //uid = CurrentUser.getUid();
        //Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();
        confirmorderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         //       Check();
                if(TextUtils.isEmpty(nameEditText.getText().toString())){
                    Toast.makeText(ConfirmFinalOrderActivity.this, "Please provide your Full Name", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(phoneEditText.getText().toString())){
                    Toast.makeText(ConfirmFinalOrderActivity.this, "Please provide your Phone Number", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(addressEditText.getText().toString())){
                    Toast.makeText(ConfirmFinalOrderActivity.this, "Please provide your Full Name", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(cityEditText.getText().toString())){
                    Toast.makeText(ConfirmFinalOrderActivity.this, "Please provide your Full Name", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ConfirmFinalOrderActivity.this, "Confirm Order", Toast.LENGTH_SHORT).show();
                    ConfirmOrder();
                }
            }
        });
    }

    /*private void Check() {
        if(TextUtils.isEmpty(nameEditText.getText().toString())){
            Toast.makeText(this, "Please provide your Full Name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phoneEditText.getText().toString())){
            Toast.makeText(this, "Please provide your Phone Number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addressEditText.getText().toString())){
            Toast.makeText(this, "Please provide your Address", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(cityEditText.getText().toString())){
            Toast.makeText(this, "Please provide your City Name", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Confirmed Order", Toast.LENGTH_SHORT).show();
            ConfirmOrder();
        }

    }*/

    private void ConfirmOrder(){
        //Toast.makeText(this, "Confirm Order Called", Toast.LENGTH_SHORT).show();
        final String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calForDate.getTime());

        final DatabaseReference ordersRef = FirebaseDatabase .getInstance().getReference()
                .child("Orders");
        //ordersRef.setValue("Hi");
        //Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        HashMap<String,Object> ordersMap = new HashMap<>();
        ordersMap.put("TotalAmount",totalAmount);
        ordersMap.put("name",nameEditText.getText().toString());
        ordersMap.put("phone",phoneEditText.getText().toString());
        ordersMap.put("address",addressEditText.getText().toString());
        ordersMap.put("city",cityEditText.getText().toString());
        ordersMap.put("date",saveCurrentDate);
        ordersMap.put("time",saveCurrentTime);
        ordersMap.put("state","not shipped");
        //Toast.makeText(this, "Done22", Toast.LENGTH_SHORT).show();
        //String a = CurrentUser.getUid();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();

        ordersRef.child(uid)
                .updateChildren(ordersMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ConfirmFinalOrderActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    FirebaseDatabase.getInstance().getReference().child("Cart List")
                            .child("User View")
                            .child(uid)
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ConfirmFinalOrderActivity.this, "Your Order Has Been Placed Successfully", Toast.LENGTH_SHORT).show();
                                        Intent mainActivity = new Intent(ConfirmFinalOrderActivity.this,MainActivity.class);
                                        mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainActivity);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
    /*private void ConfirmOrder() {
        final String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentDate.format(calForDate.getTime());

        final DatabaseReference ordersRef = FirebaseDatabase .getInstance().getReference()
                .child("Orders")
                .child(CurrentUser.getUid());

        HashMap<String,Object> ordersMap = new HashMap<>();
        ordersMap.put("Total Amount",totalAmount);
        ordersMap.put("name",nameEditText.getText().toString());
        ordersMap.put("phone",phoneEditText.getText().toString());
        ordersMap.put("address",addressEditText.getText().toString());
        ordersMap.put("city",cityEditText.getText().toString());
        ordersMap.put("date",saveCurrentDate);
        ordersMap.put("time",saveCurrentTime);
        ordersMap.put("state","not shipped");

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference().child("Cart List")
                            .child("User View")
                            .child(CurrentUser.getUid())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ConfirmFinalOrderActivity.this, "Your Order Has Been Placed Successfully", Toast.LENGTH_SHORT).show();
                                        Intent mainActivity = new Intent(ConfirmFinalOrderActivity.this,MainActivity.class);
                                     //   mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainActivity);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }*/

    @Override
    public void onBackPressed() {
        Intent mainActivity = new Intent(ConfirmFinalOrderActivity.this,MainActivity.class);
        startActivity(mainActivity);
        finish();
    }
}
