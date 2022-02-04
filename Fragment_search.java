package com.example.login2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login2.Model.Products;
import com.example.login2.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Fragment_search extends androidx.fragment.app.Fragment implements View.OnClickListener {

    Activity activity;
    public Fragment_search(Activity activity){
        this.activity = activity;
    }
    RelativeLayout slideup;
    private BottomSheetDialog bottomSheetDialog;
    private RecyclerView recyclerView;

    //private List<ProductList> lstProduct;
    private DatabaseReference Productref;
    RecyclerView.LayoutManager layoutManager;
    public String category=null;
    private String filter=null;
    private List<Products> mUploads;
    private Button search_in_category;
    private LinearLayout search_in_all;
    private EditText searchText_in_all;
    private String SearchInput;
    private ImageView SearchImage_all;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search,container,false);
        slideup = v.findViewById(R.id.slide_up);
        recyclerView = v.findViewById(R.id.product_list);
        search_in_category = v.findViewById(R.id.search_in_category);
        search_in_all = (LinearLayout) v.findViewById(R.id.search_in_all);
        searchText_in_all = (EditText) v.findViewById(R.id.searchText_in_all);
        //searchText_in_category = (EditText) v.findViewById(R.id.seachText);
        //SearchImage = (ImageView) v.findViewById(R.id.searchImage);
        SearchImage_all = (ImageView) v.findViewById(R.id.searchImage2);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        Productref = FirebaseDatabase.getInstance().getReference().child("Products");
        //RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),lstProduct);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setAdapter(recyclerViewAdapter);

        //category from fragment_home
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            category = bundle.getString("key");
            Toast.makeText(activity, category, Toast.LENGTH_SHORT).show();

        }
        //filter=category+"trending";
        //Toast.makeText(activity, filter, Toast.LENGTH_SHORT).show();


        bottomSheetDialog = new BottomSheetDialog(activity);
        View bottomSheetDialogView = getLayoutInflater().inflate(R.layout.modal_slideup_fragment,null);
        bottomSheetDialog.setContentView(bottomSheetDialogView);

      //  bottomSheetDialog.show();

        View All = bottomSheetDialogView.findViewById(R.id.all);
        View Trending = bottomSheetDialogView.findViewById(R.id.trending);
        View newProd = bottomSheetDialogView.findViewById(R.id.new_prod);
        View most_popular = bottomSheetDialogView.findViewById(R.id.most_popular);
        //View searchText = bottomSheetDialogView.findViewById(R.id.seachText);
        View close = bottomSheetDialogView.findViewById(R.id.close);
        //View searchImage = bottomSheetDialogView.findViewById(R.id.searchImage);

        All.setOnClickListener(this);
        Trending.setOnClickListener(this);
        newProd.setOnClickListener(this);
        most_popular.setOnClickListener(this);
      //  searchText.setOnClickListener(this);
        close.setOnClickListener(this);
      //  searchImage.setOnClickListener(this);



        search_in_category.setOnClickListener(this);
        if(category==null){
            search_in_category.setVisibility(View.GONE);
            search_in_all.setVisibility(View.VISIBLE);
        }
        else{
            search_in_category.setVisibility(View.VISIBLE);
            search_in_all.setVisibility(View.GONE);
        }

        SearchImage_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchInput = searchText_in_all.getText().toString();
                Toast.makeText(activity, SearchInput, Toast.LENGTH_SHORT).show();
                onStart();
            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    /*    lstProduct = new ArrayList<>();
        lstProduct.add(new ProductList("Sofa","Jeet","$50000",R.drawable.sofa));
        lstProduct.add(new ProductList("Bed","Jeet","$50000",R.drawable.bed));
        lstProduct.add(new ProductList("Chair","Darshi","$50000",R.drawable.chair));
        lstProduct.add(new ProductList("Sofa","Jeet","$50000",R.drawable.sofa));
        lstProduct.add(new ProductList("Bed","Jeet","$50000",R.drawable.bed));
        lstProduct.add(new ProductList("Chair","Darshi","$50000",R.drawable.chair));
        lstProduct.add(new ProductList("Sofa","Jeet","$50000",R.drawable.sofa));
        lstProduct.add(new ProductList("Bed","Jeet","$50000",R.drawable.bed));
        lstProduct.add(new ProductList("Chair","Darshi","$50000",R.drawable.chair));
        lstProduct.add(new ProductList("Sofa","Jeet","$50000",R.drawable.sofa));
        lstProduct.add(new ProductList("Bed","Jeet","$50000",R.drawable.bed));
        lstProduct.add(new ProductList("Chair","Darshi","$50000",R.drawable.chair));
    */}



    @Override
    public void onStart() {
        super.onStart();


   /*     Productref.orderByChild("category").equalTo(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Products products = postSnapshot.getValue(Products.class);
                    mUploads.add(products);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/

        if(category!=null){
            FirebaseRecyclerOptions<Products> options =
                    new FirebaseRecyclerOptions.Builder<Products>()
                            .setQuery(Productref.orderByChild("category").equalTo(category), Products.class)
                            .build();

            FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                    new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {
                            holder.txtname.setText(model.getPname());
                            holder.manufacturer.setText(model.getSeller());
                            holder.cost.setText("Price = Rs." + model.getPrice());
                            Picasso.get().load(model.getImage()).into(holder.imageView);

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(activity,ProductDetailsActivity.class);
                                    intent.putExtra("pid",model.getPid());
                                    startActivity(intent);
                                }
                            });
                        }

                        @NonNull
                        @Override
                        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerlayout,parent,false);
                            ProductViewHolder holder = new ProductViewHolder(view);
                            return holder;
                        }
                    };
            recyclerView.setAdapter(adapter);
            adapter.startListening();
        }else {
            FirebaseRecyclerOptions<Products> options =
                    new FirebaseRecyclerOptions.Builder<Products>()
                            .setQuery(Productref.orderByChild("pname").startAt(SearchInput), Products.class)
                            .build();

            FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                    new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {
                            holder.txtname.setText(model.getPname());
                            holder.manufacturer.setText(model.getSeller());
                            holder.cost.setText("Price = Rs." + model.getPrice());
                            Picasso.get().load(model.getImage()).into(holder.imageView);

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(activity,ProductDetailsActivity.class);
                                    intent.putExtra("pid",model.getPid());
                                    startActivity(intent);
                                }
                            });
                        }

                        @NonNull
                        @Override
                        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerlayout,parent,false);
                            ProductViewHolder holder = new ProductViewHolder(view);
                            return holder;
                        }
                    };
            recyclerView.setAdapter(adapter);
            adapter.startListening();
        }

    }
    private void all_products(){
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(Productref.orderByChild("category").equalTo(category), Products.class)
                        .build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {
                        holder.txtname.setText(model.getPname());
                        holder.manufacturer.setText(model.getSeller());
                        holder.cost.setText("Price = Rs." + model.getPrice());
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(activity,ProductDetailsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerlayout,parent,false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void filter_products(String filter2){
        Bundle bundle = this.getArguments();

        filter2=category+filter2;
        Toast.makeText(activity, filter2, Toast.LENGTH_SHORT).show();
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(Productref.orderByChild("filter").equalTo(filter2), Products.class)
                        .build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {
                        holder.txtname.setText(model.getPname());
                        holder.manufacturer.setText(model.getSeller());
                        holder.cost.setText("Price = Rs." + model.getPrice());
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(activity,ProductDetailsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerlayout,parent,false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){

            case R.id.search_in_category:
                bottomSheetDialog.show();
                break;

            case R.id.close:
                bottomSheetDialog.dismiss();
                break;

            case  R.id.all:
                all_products();
                Toast.makeText(activity, "ALL", Toast.LENGTH_SHORT).show();
                break;

            case  R.id.trending:
                filter_products("trending");
          //      Toast.makeText(activity, "Trending", Toast.LENGTH_SHORT).show();
                break;

            case  R.id.new_prod:
                filter_products("new_product");
            //    Toast.makeText(activity, "New Product", Toast.LENGTH_SHORT).show();
                break;

            case  R.id.most_popular:
                filter_products("most_popular");
              //  Toast.makeText(activity, "Most Popular", Toast.LENGTH_SHORT).show();
                break;

        }
    }

}
