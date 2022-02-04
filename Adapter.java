package com.example.login2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class Adapter extends PagerAdapter {
    private List<SwipeModel> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public Adapter(List<SwipeModel> models, Context context) {
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
        View view = layoutInflater.inflate(R.layout.item,container,false);

        ImageView imageView;
        final TextView title;

        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);

        imageView.setImageResource(models.get(position).getImage());
        title.setText(models.get(position).getTitle());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == 0){
                    /*Bundle bundle = new Bundle();
                    bundle.putString("key","Sofa");
                    Fragment_search fragment = new Fragment_search(activity);
                    fragment.setArguments(bundle);
                    FragmentActivity activity2 = (FragmentActivity) context;
                    FragmentManager manager = activity2.getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.Fragmentcontainer,fragment)
                            .commit();
*/
                    Bundle bundle = new Bundle();
                    bundle.putString("key","bed");
                    AppCompatActivity activity1 = (AppCompatActivity) view.getContext();
                    //Fragment_account fragment = new Fragment_account();
                    Fragment_search fragment_search = new Fragment_search(activity1);
                    fragment_search.setArguments(bundle);
                    activity1.getSupportFragmentManager().beginTransaction().replace(R.id.Fragmentcontainer,fragment_search).addToBackStack(null).commit();
                    Toast.makeText(context,"Slide 1 clicked",Toast.LENGTH_SHORT).show();
                }
                else if(position == 1){
                    Bundle bundle = new Bundle();
                    bundle.putString("key","sofa");
                    AppCompatActivity activity1 = (AppCompatActivity) view.getContext();
                    Fragment_search fragment_search = new Fragment_search(activity1);
                    fragment_search.setArguments(bundle);
                    activity1.getSupportFragmentManager().beginTransaction().replace(R.id.Fragmentcontainer,fragment_search).addToBackStack(null).commit();
                    Toast.makeText(context, "Slide 2 clicked", Toast.LENGTH_SHORT).show();
                }
                else if(position == 2){
                    Bundle bundle = new Bundle();
                    bundle.putString("key","table");
                    AppCompatActivity activity1 = (AppCompatActivity) view.getContext();
                    Fragment_search fragment_search = new Fragment_search(activity1);
                    fragment_search.setArguments(bundle);
                    activity1.getSupportFragmentManager().beginTransaction().replace(R.id.Fragmentcontainer,fragment_search).addToBackStack(null).commit();
                    Toast.makeText(context,"Slide 3 clicked",Toast.LENGTH_SHORT).show();
                }
                else if(position == 3){
                    Bundle bundle = new Bundle();
                    bundle.putString("key","chair");
                    AppCompatActivity activity1 = (AppCompatActivity) view.getContext();
                    Fragment_search fragment_search = new Fragment_search(activity1);
                    fragment_search.setArguments(bundle);
                    activity1.getSupportFragmentManager().beginTransaction().replace(R.id.Fragmentcontainer,fragment_search).addToBackStack(null).commit();
                }
                else if(position == 4){
                    Bundle bundle = new Bundle();
                    bundle.putString("key","stool");
                    AppCompatActivity activity1 = (AppCompatActivity) view.getContext();
                    Fragment_search fragment_search = new Fragment_search(activity1);
                    fragment_search.setArguments(bundle);
                    activity1.getSupportFragmentManager().beginTransaction().replace(R.id.Fragmentcontainer,fragment_search).addToBackStack(null).commit();
                }
                else if(position == 5){
                    Bundle bundle = new Bundle();
                    bundle.putString("key","home decor");
                    AppCompatActivity activity1 = (AppCompatActivity) view.getContext();
                    Fragment_search fragment_search = new Fragment_search(activity1);
                    fragment_search.setArguments(bundle);
                    activity1.getSupportFragmentManager().beginTransaction().replace(R.id.Fragmentcontainer,fragment_search).addToBackStack(null).commit();
                }
                else if(position == 6){
                    Bundle bundle = new Bundle();
                    bundle.putString("key","wardrobe");
                    AppCompatActivity activity1 = (AppCompatActivity) view.getContext();
                    Fragment_search fragment_search = new Fragment_search(activity1);
                    fragment_search.setArguments(bundle);
                    activity1.getSupportFragmentManager().beginTransaction().replace(R.id.Fragmentcontainer,fragment_search).addToBackStack(null).commit();
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
