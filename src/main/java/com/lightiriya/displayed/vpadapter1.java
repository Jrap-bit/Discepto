package com.lightiriya.displayed;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.lightiriya.displayed.addfragment;
import com.lightiriya.displayed.homefragment;
import com.lightiriya.displayed.notificationfragment;
import com.lightiriya.displayed.profilefragment;
import com.lightiriya.displayed.searchfragment;

public class vpadapter1 extends FragmentStateAdapter {
    private String[] titles=new String[]{"home","search","add","notification","profile"};

    public vpadapter1(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public vpadapter1(@NonNull Fragment fragment) {
        super(fragment);
    }

    public vpadapter1(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
       switch(position){
           case 0:
               return new homefragment();
           case 1:
               return new searchfragment();
           case 2:
               return new addfragment();
           case 3:
               return new notificationfragment();
           case 4:
               return new profilefragment();
       }
        return new homefragment();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
