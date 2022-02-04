package com.example.login2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login2.Model.Cart;
import com.example.login2.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

public class Fragment_cart extends androidx.fragment.app.Fragment {

    Activity activity;
    public Fragment_cart(Activity activity){
        this.activity = activity;
    }
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessbtn;
    private TextView txtTotalAmount, txtMsg;
    private int overTotalPrice = 0;
    FirebaseUser CurrentUser;
    String uid;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.cart,container,false);

        recyclerView = v.findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);

        NextProcessbtn = (Button) v.findViewById(R.id.next_process_btn);
        txtTotalAmount = (TextView) v.findViewById(R.id.total_price);
        txtMsg = (TextView) v.findViewById(R.id.msg1);
        CurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = CurrentUser.getUid();


        txtTotalAmount.setText("Total Price : 0");
        NextProcessbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //txtTotalAmount.setText("Total Price = Rs."+String.valueOf(overTotalPrice));
                Intent intent = new Intent(activity, ConfirmFinalOrderActivity.class);
                intent.putExtra("Total Price",String.valueOf(overTotalPrice));
                startActivity(intent);
                //finish();
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        CheckOrderState();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child("User View")
                .child(CurrentUser.getUid()).child("Products"),Cart.class)
                .build();

        FirebaseRecyclerAdapter<Cart,CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model) {
                holder.txtProductQuantity.setText("Quantity = "+model.getQuantity());
                holder.txtProductName.setText(model.getPname());
                holder.txtProductPrice.setText("Price = Rs."+model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.imageView2);
                //holder.imageView2.setImag(model.getImage());
                //holder.imageView2.setImageResource(model.getCart_image());

                //everytime for each product in recyclerView
                int oneTypeProductTPrice = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
                overTotalPrice = overTotalPrice + oneTypeProductTPrice;
                txtTotalAmount.setText("Total Price = Rs."+String.valueOf(overTotalPrice));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]
                                {
                                  "Edit",
                                  "Remove"
                                };

                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setTitle("Cart Options: ");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i==0) {
                                    Intent intent = new Intent(activity, ProductDetailsActivity.class);
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);
                                }
                                if(i==1){
                                    cartListRef.child("User View")
                                            .child(CurrentUser.getUid())
                                            .child("Products")
                                            .child(model.getPid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        Toast.makeText(activity, "Item removed from your cart", Toast.LENGTH_SHORT).show();
                                                        Fragment_search fragment_search = new Fragment_search(activity);
                                                        FragmentManager fragmentManager = getFragmentManager();
                                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                        fragmentTransaction.commit();
                                                    }
                                                }
                                            });
                                }
                            }
                        });

                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder =  new CartViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void CheckOrderState(){
        DatabaseReference orderRef;
        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(CurrentUser.getUid());

        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String shippingState = dataSnapshot.child("state").getValue().toString();
                    String userName = dataSnapshot.child("name").getValue().toString();

                    /*if(shippingState.equals("shipped")){
                        txtTotalAmount.setText(userName+", your order is shipped successfully");
                        recyclerView.setVisibility(View.VISIBLE);

                        //txtMsg.setVisibility(View.VISIBLE);
                        //txtMsg.setText("Congratulations! Your order has been Shipped successfully. Soon you will receive your order at the door step.");
                        NextProcessbtn.setVisibility(View.VISIBLE);
                        //Toast.makeText(activity, "You can purchase more products, once you receive your final order", Toast.LENGTH_SHORT).show();
                    }*/
                    if(shippingState.equals("not shipped")){

                        txtTotalAmount.setText("Shipping State : Not Shipped");
                        recyclerView.setVisibility(View.GONE);

                        txtMsg.setVisibility(View.VISIBLE);
                        NextProcessbtn.setVisibility(View.GONE);
                        Toast.makeText(activity, "You can purchase more products, once you receive your final order", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        txtTotalAmount.setText(userName+", your order is shipped successfully");
                        recyclerView.setVisibility(View.VISIBLE);

                        txtMsg.setVisibility(View.GONE);
                        //txtMsg.setText("Congratulations! Your order has been Shipped successfully. Soon you will receive your order at the door step.");
                        NextProcessbtn.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
