package com.lightiriya.displayed;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class vpadapter2 extends FragmentStateAdapter {

    private String[] titles=new String[]{"Friends","Followers","General","Groups"};

    public vpadapter2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public vpadapter2(@NonNull Fragment fragment) {
        super(fragment);
    }

    public vpadapter2(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new FriendChat();
            case 1:
                return new FollowerChat();
            case 2:
                return new GeneralChat();
            case 3:
                return new GroupChat();

        }
        return new homefragment();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
