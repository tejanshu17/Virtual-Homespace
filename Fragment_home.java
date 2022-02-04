package com.example.login2;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class Fragment_home extends androidx.fragment.app.Fragment {

    ViewPager viewPager,viewPager2;
    Adapter adapter;
    Adapter2 adapter2;
    List<SwipeModel> models,models2;
    Activity activity_this;
    ImageButton homework1,homework2,homework3,homework4;
    ImageButton sofa1,sofa2,sofa3,sofa4;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    Activity activity;
    public Fragment_home(Activity activity){
        this.activity = activity;}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home, container,false);

        models = new ArrayList<>();
        models.add(new SwipeModel(R.drawable.bed2,"BED"));
        models.add(new SwipeModel(R.drawable.sofa2,"SOFA"));
        models.add(new SwipeModel(R.drawable.table2,"TABLE"));
        models.add(new SwipeModel(R.drawable.chair,"CHAIR"));
        models.add(new SwipeModel(R.drawable.stool,"STOOL"));
        models.add(new SwipeModel(R.drawable.home_decor2,"HOME-DECOR"));
        models.add(new SwipeModel(R.drawable.wardrobe2,"WARDROBE"));

        adapter = new Adapter(models,activity);
        viewPager = v.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(80,0,80,0);

        models2 = new ArrayList<>();
        models2.add(new SwipeModel(R.drawable.right,"40% off"));
        models2.add(new SwipeModel(R.drawable.right2,"10% off"));
        models2.add(new SwipeModel(R.drawable.right3,"15% off"));
        models2.add(new SwipeModel(R.drawable.right4,"35% off"));

        adapter2 = new Adapter2(models2,activity);
        viewPager2 = v.findViewById(R.id.viewPager2);
        viewPager2.setAdapter(adapter2);
        viewPager2.setPadding(30,0,350,0);

        homework1 = (ImageButton) v.findViewById(R.id.homework1);
        homework2 = (ImageButton) v.findViewById(R.id.homework2);
        homework3 = (ImageButton) v.findViewById(R.id.homework3);
        homework4 = (ImageButton) v.findViewById(R.id.homework4);

        sofa1 = (ImageButton) v.findViewById(R.id.sofa1);
        sofa2 = (ImageButton) v.findViewById(R.id.sofa2);
        sofa3 = (ImageButton) v.findViewById(R.id.sofa3);
        sofa4 = (ImageButton) v.findViewById(R.id.sofa4);


        homework1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,ProductDetailsActivity.class);
                intent.putExtra("pid","May 16, 202001:09:04 AM");
                startActivity(intent);
            }
        });

        homework2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,ProductDetailsActivity.class);
                intent.putExtra("pid","May 16, 202001:09:04 AM");
                startActivity(intent);
            }
        });

        homework3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,ProductDetailsActivity.class);
                intent.putExtra("pid","May 16, 202001:09:04 AM");
                startActivity(intent);
            }
        });

        homework4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,ProductDetailsActivity.class);
                intent.putExtra("pid","May 16, 202001:09:04 AM");
                startActivity(intent);
            }
        });

        sofa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,ProductDetailsActivity.class);
                intent.putExtra("pid","May 16, 202014:28:22 PM");
                startActivity(intent);
            }
        });

        sofa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,ProductDetailsActivity.class);
                intent.putExtra("pid","May 16, 202003:53:48 AM");
                startActivity(intent);
            }
        });

        sofa3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,ProductDetailsActivity.class);
                intent.putExtra("pid","May 16, 202023:07:40 PM");
                startActivity(intent);
            }
        });

        sofa4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,ProductDetailsActivity.class);
                intent.putExtra("pid","May 16, 202023:04:45 PM");
                startActivity(intent);
            }
        });

        Integer[] colors_temp = {

                ContextCompat.getColor(getActivity(), R.color.color1),
                ContextCompat.getColor(getActivity(), R.color.color2),
                ContextCompat.getColor(getActivity(), R.color.color3),
                ContextCompat.getColor(getActivity(), R.color.color4),
                ContextCompat.getColor(getActivity(), R.color.color5),
                ContextCompat.getColor(getActivity(), R.color.color6),
                ContextCompat.getColor(getActivity(), R.color.color7),
/*            getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4),
                getResources().getColor(R.color.color5),
                getResources().getColor(R.color.color6),
                getResources().getColor(R.color.color7),*/
        };

        colors = colors_temp;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position<(adapter.getCount() -1) && position<colors.length -1) {
                    viewPager.setBackgroundColor((Integer)
                            argbEvaluator.evaluate(positionOffset,
                                    colors[position],
                                    colors[position+1])
                    );
                }else {
                    viewPager.setBackgroundColor(colors[colors.length-1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return v;
    }
}
