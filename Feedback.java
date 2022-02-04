package com.example.login2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Feedback extends AppCompatActivity {
    Spinner feedback_spinner;
    EditText feedback_text;
    DatabaseReference ref;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        feedback_spinner = findViewById(R.id.feedback_spinner);
        feedback_text = findViewById(R.id.feedback_text);
        submit = findViewById(R.id.submit);

        List<String> category = new ArrayList<>();
        category.add("Select a category");
        category.add("Product & Quality");
        category.add("Designs");
        category.add("App");
        category.add("Service");
        category.add("General");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,category);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feedback_spinner.setAdapter(categoryAdapter);

        feedback_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        final String yourcategory = feedback_spinner.getSelectedItem().toString();
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(!yourcategory.equals("Select a Category")){
                                    String text;
                                    text = feedback_text.getText().toString().trim();
                                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Feedback");
                                    ref = databaseReference.child(yourcategory);
                                    ref.push().setValue(text);
                                }

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                            submit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String text;
                                    text = feedback_text.getText().toString().trim();
                                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Feedback");
                                    ref = databaseReference.child("General");
                                    ref.push().setValue(text);
                                }
                            });
                    }
                });
            }
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(getCurrentFocus()!=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}

