package com.example.zolaapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.zolaapp.fragment.ContactsFragment;
import com.example.zolaapp.fragment.DiaryFragment;
import com.example.zolaapp.fragment.ExploreFragment;
import com.example.zolaapp.fragment.MessageFragment;
import com.example.zolaapp.fragment.ProfileFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:return new MessageFragment();
            case 1:return new ContactsFragment();
            case 2:return new ExploreFragment();
            case 3:return new DiaryFragment();
            case 4:return new ProfileFragment();
            default:return new MessageFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
