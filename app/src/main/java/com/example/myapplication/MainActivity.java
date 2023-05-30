package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.example.myapplication.adapters.WorkoutsListAdapter;
import com.example.myapplication.models.WorkoutDto;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.*;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navigationView;
    private int selectedIndex;
    private RecyclerView rvUpcomingWorkouts;
    private RecyclerView rvPastWorkouts;
    private List<WorkoutDto> upcomingWorkoutDtoList;
    private List<WorkoutDto> pastWorkoutDtoList;

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        this.getSupportActionBar().hide();

        upcomingWorkoutDtoList = new ArrayList<>();
        upcomingWorkoutDtoList.add(WorkoutDto.builder().workoutDate("27.05").workoutType("Силовая").build());
        upcomingWorkoutDtoList.add(WorkoutDto.builder().workoutDate("28.05").workoutType("Беговая").build());
        upcomingWorkoutDtoList.add(WorkoutDto.builder().workoutDate("29.05").workoutType("Беговая").build());
        upcomingWorkoutDtoList.add(WorkoutDto.builder().workoutDate("25.05").workoutType("Силовая").build());

        pastWorkoutDtoList = new ArrayList<>();
        pastWorkoutDtoList.add(WorkoutDto.builder().workoutDate("20.05").workoutType("Беговая").build());
        pastWorkoutDtoList.add(WorkoutDto.builder().workoutDate("18.05").workoutType("Силовая").build());
        pastWorkoutDtoList.add(WorkoutDto.builder().workoutDate("19.05").workoutType("Беговая").build());
        pastWorkoutDtoList.add(WorkoutDto.builder().workoutDate("15.05").workoutType("Силовая").build());

        final ImageButton imageButton = findViewById(R.id.imageButton15);
        imageButton.setOnClickListener(view -> {
            Intent activityChangeIntent = new Intent(MainActivity.this, NewTrainActivity.class);
            MainActivity.this.startActivity(activityChangeIntent);
        });

        // upcoming workouts block
        WorkoutsListAdapter upcomingWorkoutsListAdapter = new WorkoutsListAdapter(this, upcomingWorkoutDtoList);
        try {
            rvUpcomingWorkouts = findViewById(R.id.upcoming_workouts_list);
            rvUpcomingWorkouts.setAdapter(upcomingWorkoutsListAdapter);
        } catch (Exception e) {
            Log.e("SPORT_APP", e.getMessage());
        }
        // upcoming workouts end
        // past workouts block
        WorkoutsListAdapter pastWorkoutsListAdapter = new WorkoutsListAdapter(this, pastWorkoutDtoList);
        try {
            rvPastWorkouts = findViewById(R.id.past_workouts_list);
            rvPastWorkouts.setAdapter(pastWorkoutsListAdapter);
        } catch (Exception e) {
            Log.e("SPORT_APP", e.getMessage());
        }
        // past workouts end

        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (selectedIndex != id) {
            switch (id) {
                case R.id.page_1:
//                    replaceFragment(new ProfileFragment());
                    return true;
                case R.id.page_2:
                    return true;
                case R.id.page_3:
//                    replaceFragment(new EditorFragment());
                    return true;
                case R.id.page_4:
                    return true;
                case R.id.page_5:
                    return true;

            }
        }
        return false;
    }
}
