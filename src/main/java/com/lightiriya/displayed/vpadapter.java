package com.lightiriya.displayed;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class vpadapter extends FragmentStateAdapter {
    private String[] titles=new String[]{"Login","Signup "};

    public vpadapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public vpadapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public vpadapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new loginfragment();
            case 1:
                return new signupfragment();
        }
        return new loginfragment();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
