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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login2.Model.Wishlist;
import com.example.login2.ViewHolder.WishlistViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Fragment_wishlist extends androidx.fragment.app.Fragment {

    Activity activity;
    public Fragment_wishlist(Activity activity){
        this.activity = activity;
    }
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button augmentedbtn;
    FirebaseUser CurrentUser;
    String uid;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wishlist,container,false);

        recyclerView = v.findViewById(R.id.wish_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);

        augmentedbtn = (Button) v.findViewById(R.id.augmented_reality);
        CurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = CurrentUser.getUid();

        augmentedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, kotlin.class);
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        final DatabaseReference wishListRef = FirebaseDatabase.getInstance().getReference().child("Wish List");

        FirebaseRecyclerOptions<Wishlist> options = new FirebaseRecyclerOptions.Builder<Wishlist>()
                .setQuery(wishListRef.child("User View")
                        .child(CurrentUser.getUid()).child("Products"),Wishlist.class)
                        .build();

        FirebaseRecyclerAdapter<Wishlist,WishlistViewHolder> adapter = new FirebaseRecyclerAdapter<Wishlist, WishlistViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull WishlistViewHolder holder, int position, @NonNull final Wishlist model) {
                holder.txtProductName.setText(model.getPname());
                holder.txtProductPrice.setText("Price = Rs."+model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.imageView2);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Add to Cart",
                                        "Remove"
                                };

                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setTitle("Wishlist Options: ");


                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i==0) {
                                    Intent intent = new Intent(activity, ProductDetailsActivity.class);
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);
                                }
                                if(i==1) {
                                    wishListRef.child("User View")
                                            .child(CurrentUser.getUid())
                                            .child("Products")
                                            .child(model.getPid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        Toast.makeText(activity, "Item removed from your wishlist", Toast.LENGTH_SHORT).show();
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
            public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_items_layout,parent,false);
                WishlistViewHolder holder =  new WishlistViewHolder(view);
                return holder;

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
