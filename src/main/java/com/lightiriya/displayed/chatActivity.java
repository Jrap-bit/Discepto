package com.lightiriya.displayed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class chatActivity extends AppCompatActivity {
    vpadapter2 vpadapter2;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    private String[] titles=new String[]{"Friends","Followers","General","Groups"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewpagers);
        vpadapter2=new vpadapter2(this);
        viewPager.setAdapter(vpadapter2);
        new TabLayoutMediator(tabLayout,viewPager,(tab, position) -> tab.setText(titles[position])).attach();

    }
}