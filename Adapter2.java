package com.example.login2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Adapter2 extends PagerAdapter {
    private List<SwipeModel> models;
    private LayoutInflater layoutInflater;
    private Context context;
    DatabaseReference ref;

    public Adapter2(List<SwipeModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
            return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item2,container,false);

        ImageView imageView,imageView1;
       // TextView title,title1;

        imageView1 = view.findViewById(R.id.image2);
     //   title1 = view.findViewById(R.id.title2);

        imageView1.setImageResource(models.get(position).getImage());
        //title1.setText(models.get(position).getTitle());

        //ref = FirebaseDatabase.getInstance().getReference().child("Products");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == 0){
                    AppCompatActivity activity1 = (AppCompatActivity) view.getContext();
                    Intent intent = new Intent(activity1,ProductDetailsActivity.class);
                    intent.putExtra("pid","May 16, 202000:23:36 AM");
                    context.startActivity(intent);
                    //Toast.makeText(context, "one", Toast.LENGTH_SHORT).show();
                }
                else if(position == 1){
                    AppCompatActivity activity1 = (AppCompatActivity) view.getContext();
                    Intent intent = new Intent(activity1,ProductDetailsActivity.class);
                    intent.putExtra("pid","May 15, 202000:22:52 AM");
                    context.startActivity(intent);
                    //Toast.makeText(context, "two", Toast.LENGTH_SHORT).show();
                }
                else if(position == 2){
                    AppCompatActivity activity1 = (AppCompatActivity) view.getContext();
                    Intent intent = new Intent(activity1,ProductDetailsActivity.class);
                    intent.putExtra("pid","May 16, 202003:56:39 AM");
                    context.startActivity(intent);
                    //Toast.makeText(context, "three", Toast.LENGTH_SHORT).show();
                }
                else if(position == 3){
                    AppCompatActivity activity1 = (AppCompatActivity) view.getContext();
                    Intent intent = new Intent(activity1,ProductDetailsActivity.class);
                    intent.putExtra("pid","May 16, 202023:18:41 PM");
                    context.startActivity(intent);
                    //Toast.makeText(context, "four", Toast.LENGTH_SHORT).show();
                }
            }
        });

        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
