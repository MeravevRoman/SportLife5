package com.example.myapplication.ui.competitions;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class CompetitionsFragmentAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments;

    public CompetitionsFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }
}
