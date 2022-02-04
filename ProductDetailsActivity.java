package com.example.login2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.login2.Model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    private Button addToCartbtn,addToWishlistbtn;
    private ImageView productImage;
    private String downloadImageUrl;
    private ElegantNumberButton numberButton;
    private TextView productName,productCategory,productSellerName,productPrice,productDescription;
    private TextView productWarranty,productDimensions;
    FirebaseUser CurrentUser;
    String uid;
    DatabaseReference ref;
    private String productID="" , state="Normal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        addToCartbtn= findViewById(R.id.add_product_to_cart_btn);
        addToWishlistbtn= findViewById(R.id.add_product_to_wishlist_btn);
        productImage= findViewById(R.id.product_image_details);
        numberButton = findViewById(R.id.number_btn);
        productName= findViewById(R.id.product_name_details);
        productCategory= findViewById(R.id.product_category_details);
        productSellerName= findViewById(R.id.product_seller_details);
        productPrice= findViewById(R.id.product_cost_details);
        productDescription= findViewById(R.id.product_description_details);
        productWarranty= findViewById(R.id.product_warranty_details);
        productDimensions= findViewById(R.id.product_dimension_details);
        CurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = CurrentUser.getUid();

        productID = getIntent().getStringExtra("pid");

        getProductDetails(productID);


        ref = FirebaseDatabase.getInstance().getReference().child("Products");
        ref.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                downloadImageUrl = dataSnapshot.child("image").getValue().toString();
                //Toast.makeText(ProductDetailsActivity.this, downloadImageUrl, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addToWishlistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String saveCurrentTime, saveCurrentDate;

                Calendar calForDate = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
                saveCurrentDate=currentDate.format(calForDate.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime=currentDate.format(calForDate.getTime());
                final DatabaseReference wishListref = FirebaseDatabase.getInstance().getReference().child("Wish List");

                final HashMap<String,Object> wishMap = new HashMap<>();
                wishMap.put("pid",productID);
                wishMap.put("pname",productName.getText().toString());
                wishMap.put("price",productPrice.getText().toString());
                wishMap.put("date",saveCurrentDate);
                wishMap.put("time",saveCurrentTime);
                wishMap.put("discount","");
                wishMap.put("image",downloadImageUrl);

                wishListref.child("User View").child(CurrentUser.getUid())
                        .child("Products").child(productID)
                        .updateChildren(wishMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                  Toast.makeText(ProductDetailsActivity.this, "Added to Wish List.", Toast.LENGTH_SHORT).show();
                                  Intent mainActivity = new Intent(ProductDetailsActivity.this,MainActivity.class);
                                  startActivity(mainActivity);
                                  finish();
                                  }
                             });
                        }
                    });

        addToCartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if(state.equals("Order Placed")){
                    Toast.makeText(ProductDetailsActivity.this, "You can add more products once the order is shipped", Toast.LENGTH_LONG).show();
                }
                else{

                    //adding to cart list
                    String saveCurrentTime, saveCurrentDate;

                    Calendar calForDate = Calendar.getInstance();
                    SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
                    saveCurrentDate=currentDate.format(calForDate.getTime());

                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                    saveCurrentTime=currentDate.format(calForDate.getTime());

                    final DatabaseReference cartListref = FirebaseDatabase.getInstance().getReference().child("Cart List");

                    final HashMap<String,Object> cartMap = new HashMap<>();
                    cartMap.put("pid",productID);
                    cartMap.put("pname",productName.getText().toString());
                    cartMap.put("price",productPrice.getText().toString());
                    cartMap.put("date",saveCurrentDate);
                    cartMap.put("time",saveCurrentTime);
                    cartMap.put("quantity",numberButton.getNumber());
                    cartMap.put("discount","");
                    cartMap.put("image",downloadImageUrl);

                    cartListref.child("User View").child(CurrentUser.getUid())
                            .child("Products").child(productID)
                            .updateChildren(cartMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        cartListref.child("Admin View").child(CurrentUser.getUid())
                                                .child("Products").child(productID)
                                                .updateChildren(cartMap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(ProductDetailsActivity.this, "Added to Cart List.", Toast.LENGTH_SHORT).show();
                                                        Intent mainActivity = new Intent(ProductDetailsActivity.this,MainActivity.class);
                                                        startActivity(mainActivity);
                                                        finish();
                                                    }
                                                });
                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        CheckOrderState();
    }

    private void getProductDetails(final String productID) {
        DatabaseReference productref = FirebaseDatabase.getInstance().getReference().child("Products");

        productref.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Products products = dataSnapshot.getValue(Products.class);
                    productName.setText(products.getPname());
                    productDescription.setText(products.getDescription());
                    productPrice.setText(products.getPrice());
                    productCategory.setText(products.getCategory());
                    productSellerName.setText(products.getSeller());
                    productWarranty.setText(products.getWarranty());
                    productDimensions.setText(products.getDimensions());
                    Picasso.get().load(products.getImage()).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void CheckOrderState(){
        DatabaseReference orderRef;
        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(CurrentUser.getUid());

        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String shippingState = dataSnapshot.child("state").getValue().toString();

                    if(shippingState.equals("shipped")){
                        state = "Order Shipped";
                    }
                    else if(shippingState.equals("not shipped")){
                        state = "Order Placed";
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
