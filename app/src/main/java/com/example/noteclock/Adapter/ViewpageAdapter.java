package com.example.noteclock.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.noteclock.fragment.FragmentHome;
import com.example.noteclock.fragment.FragmentSearch;
import com.example.noteclock.fragment.FragmentToDay;


public class ViewpageAdapter extends FragmentStateAdapter {
    public ViewpageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FragmentHome();
            case 1:
                return new FragmentToDay();
            case 2:
                return new FragmentSearch();
            default:
                return new FragmentHome();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
