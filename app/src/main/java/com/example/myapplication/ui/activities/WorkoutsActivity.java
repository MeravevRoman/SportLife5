package com.example.myapplication.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Main;
import com.example.myapplication.R;
import com.example.myapplication.data.models.UserDto;
import com.example.myapplication.ui.adapters.WorkoutsListAdapter;
import com.example.myapplication.data.models.WorkoutDto;
import com.example.myapplication.ui.calendar.CalendarEventDialog;
import com.example.myapplication.ui.calendar.CalendarView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.*;

import javax.inject.Inject;

public class WorkoutsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
 CalendarView.OnClickItemCalendar {

    private CalendarView calendarView;
    private BottomNavigationView navigationView;
    private int selectedIndex;
    private RecyclerView rvUpcomingWorkouts;
    private RecyclerView rvPastWorkouts;
    private List<WorkoutDto> upcomingWorkoutDtoList;
    private List<WorkoutDto> pastWorkoutDtoList;

    private TextView textViewUserTitle;

    @Inject
    FirebaseDatabase dbInstance;
    @Inject
    FirebaseAuth mAuth;

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();

        ((Main)getApplication()).getAppComponent().inject(this);

        calendarView = findViewById(R.id.calendarView);

        textViewUserTitle = findViewById(R.id.textViewUserTitle);

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

        // извлекаем информацию о текущем пользователе
        String currentUid = mAuth.getCurrentUser().getUid();
        UserDto currentUser = new UserDto();
        try {
            dbInstance.getReference("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    currentUser.setEmail(snapshot.child(currentUid).child("email").getValue(String.class));
                    currentUser.setUsername(snapshot.child(currentUid).child("username").getValue(String.class));
                    textViewUserTitle.setText(currentUser.getUsername());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(WorkoutsActivity.this, "Ошибка получения данных из БД", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e("SPORT_APP", e.getMessage());
        }

        final ImageButton imageButton = findViewById(R.id.imageButton15);
        imageButton.setOnClickListener(view -> {
            Intent activityChangeIntent = new Intent(WorkoutsActivity.this, NewWorkoutActivity.class);
            WorkoutsActivity.this.startActivity(activityChangeIntent);
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
                    return true;
                case R.id.page_4:
                    return true;
                case R.id.page_5:
                    return true;
            }
        }
        return false;
    }

    @Override
    public void onClickItem(String dayId) {
        CalendarEventDialog dialog = new CalendarEventDialog(dayId);
        dialog.show(getSupportFragmentManager(), "calendar event");
    }
}
