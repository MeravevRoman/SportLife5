package com.example.myapplication.ui.goals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.R;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoalsFragment extends Fragment implements TabLayoutMediator.TabConfigurationStrategy {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private List<String> tabsTitles;

    public GoalsFragment() {}

    public static GoalsFragment getInstance() {
        return new GoalsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_goals, container, false);

        tabLayout = view.findViewById(R.id.tabsLayoutGoals);
        viewPager = view.findViewById(R.id.tabsPagerGoals);
        tabsTitles = new ArrayList<>(Arrays.asList("Текущие", "Завершенные"));
        setViewPagerAdapter();

        return view;
    }

    public void setViewPagerAdapter() {
        GoalsFragmentAdapter adapter = new GoalsFragmentAdapter(getActivity());
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new CurrentGoalsFragment());
        fragments.add(new PastGoalsFragment());

        adapter.setFragments(fragments);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager, this).attach();
    }

    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        tab.setText(tabsTitles.get(position));
    }
}
