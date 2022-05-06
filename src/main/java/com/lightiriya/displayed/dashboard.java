package com.lightiriya.displayed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class dashboard extends AppCompatActivity {
    vpadapter1 vpadapter1;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    private String[] titles=new String[]{"home","search","add","notification","profile"};
    int[] image={R.drawable.ic_baseline_home_24,R.drawable.ic_baseline_search_24,R.drawable.ic_baseline_add_24,R.drawable.ic_baseline_notifications_24,R.drawable.ic_baseline_person_24};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager2=findViewById(R.id.view_page);
        vpadapter1=new vpadapter1(this);

        viewPager2.setAdapter(vpadapter1);
        new TabLayoutMediator(tabLayout,viewPager2,(tab, position) -> tab.setIcon(image[position])).attach();
    }
}