package com.example.myapplication.ui.workouts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.Main;
import com.example.myapplication.R;
import com.example.myapplication.di.components.PagesComponent;
import com.example.myapplication.ui.calendar.CalendarEventDialog;
import com.example.myapplication.ui.calendar.CalendarView;
import com.example.myapplication.ui.competitions.CompetitionsFragment;
import com.example.myapplication.ui.goals.GoalsFragment;
import com.example.myapplication.ui.main.MainFragment;
import com.example.myapplication.ui.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.*;

public class WorkoutsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
 CalendarView.OnClickItemCalendar {


    private BottomNavigationView navigationView;
    private int selectedIndex;

    public PagesComponent pagesComponent;

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();

        pagesComponent = ((Main) getApplicationContext()).getAppComponent().pagesComponent().create();
        pagesComponent.inject(this);

        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (selectedIndex != id) {
            switch (id) {
                case R.id.page_1:
                    selectedIndex = R.id.page_1;
                    loadFragment(CompetitionsFragment.getInstance());
                    return true;
                case R.id.page_2:
                    selectedIndex = R.id.page_2;
                    loadFragment(WorkoutsFragment.getInstance());
                    return true;
                case R.id.page_3:
                    selectedIndex = R.id.page_3;
                    loadFragment(new MainFragment(this));
                    return true;
                case R.id.page_4:
                    selectedIndex = R.id.page_4;
                    loadFragment(GoalsFragment.getInstance());
                    return true;
                case R.id.page_5:
                    selectedIndex = R.id.page_5;
                    loadFragment(SettingsFragment.getInstance());
                    return true;
            }
        }
        return false;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_content, fragment);
        ft.commit();
    }
    @Override
    public void onClickItem(Date dayId, Context context) {
        CalendarEventDialog dialog = new CalendarEventDialog(dayId, context);
        dialog.show(getSupportFragmentManager(), "calendar event");
    }
}
