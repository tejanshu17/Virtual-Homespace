package com.example.login2.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login2.Interface.ItemClickListner;
import com.example.login2.R;

public class WishlistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductPrice;
    public ItemClickListner itemClickListner;
    public ImageView imageView2;

    public WishlistViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.wish_product_name);
        txtProductPrice = itemView.findViewById(R.id.wish_product_price);
        imageView2 = (ImageView) itemView.findViewById(R.id.wish_image);
    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
