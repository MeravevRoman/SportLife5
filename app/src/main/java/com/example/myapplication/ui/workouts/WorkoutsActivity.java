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

//    private CalendarView calendarView;
//    private RecyclerView rvUpcomingWorkouts;
//    private RecyclerView rvPastWorkouts;
//    private List<WorkoutDto> upcomingWorkoutDtoList;
//    private List<WorkoutDto> pastWorkoutDtoList;
//
//    private TextView textViewUserTitle;
    public PagesComponent pagesComponent;

//    @Inject
//    FirebaseDatabase dbInstance;
//    @Inject
//    FirebaseAuth mAuth;

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();

        pagesComponent = ((Main) getApplicationContext()).getAppComponent().pagesComponent().create();
        pagesComponent.inject(this);
//
//        calendarView = findViewById(R.id.calendarView);
//
//        textViewUserTitle = findViewById(R.id.textViewUserTitle);
//
//        upcomingWorkoutDtoList = new ArrayList<>();
//        upcomingWorkoutDtoList.add(WorkoutDto.builder().workoutDate("27.05").workoutType("Силовая").build());
//        upcomingWorkoutDtoList.add(WorkoutDto.builder().workoutDate("28.05").workoutType("Беговая").build());
//        upcomingWorkoutDtoList.add(WorkoutDto.builder().workoutDate("29.05").workoutType("Беговая").build());
//        upcomingWorkoutDtoList.add(WorkoutDto.builder().workoutDate("25.05").workoutType("Силовая").build());
//
//        pastWorkoutDtoList = new ArrayList<>();
//        pastWorkoutDtoList.add(WorkoutDto.builder().workoutDate("20.05").workoutType("Беговая").build());
//        pastWorkoutDtoList.add(WorkoutDto.builder().workoutDate("18.05").workoutType("Силовая").build());
//        pastWorkoutDtoList.add(WorkoutDto.builder().workoutDate("19.05").workoutType("Беговая").build());
//        pastWorkoutDtoList.add(WorkoutDto.builder().workoutDate("15.05").workoutType("Силовая").build());
//
//        // извлекаем информацию о текущем пользователе
//        String currentUid = mAuth.getCurrentUser().getUid();
//        UserDto currentUser = new UserDto();
//        try {
//            dbInstance.getReference("users").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    currentUser.setEmail(snapshot.child(currentUid).child("email").getValue(String.class));
//                    currentUser.setUsername(snapshot.child(currentUid).child("username").getValue(String.class));
//                    textViewUserTitle.setText(currentUser.getUsername());
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(WorkoutsActivity.this, "Ошибка получения данных из БД", Toast.LENGTH_SHORT).show();
//                }
//            });
//        } catch (Exception e) {
//            Log.e("SPORT_APP", e.getMessage());
//        }
//
//        final ImageButton imageButton = findViewById(R.id.imageButton15);
//        imageButton.setOnClickListener(view -> {
//            Intent activityChangeIntent = new Intent(WorkoutsActivity.this, NewWorkoutActivity.class);
//            WorkoutsActivity.this.startActivity(activityChangeIntent);
//        });
//
//        // upcoming workouts block
//        WorkoutsListAdapter upcomingWorkoutsListAdapter = new WorkoutsListAdapter(this, upcomingWorkoutDtoList);
//        try {
//            rvUpcomingWorkouts = findViewById(R.id.upcoming_workouts_list);
//            rvUpcomingWorkouts.setAdapter(upcomingWorkoutsListAdapter);
//        } catch (Exception e) {
//            Log.e("SPORT_APP", e.getMessage());
//        }
//        // upcoming workouts end
//        // past workouts block
//        WorkoutsListAdapter pastWorkoutsListAdapter = new WorkoutsListAdapter(this, pastWorkoutDtoList);
//        try {
//            rvPastWorkouts = findViewById(R.id.past_workouts_list);
//            rvPastWorkouts.setAdapter(pastWorkoutsListAdapter);
//        } catch (Exception e) {
//            Log.e("SPORT_APP", e.getMessage());
//        }
//        // past workouts end


// выйти из аккаунта
//                    mAuth.signOut();
//                    Intent previewIntent = new Intent(WorkoutsActivity.this, PreviewActivity.class);
//                    this.startActivity(previewIntent);
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
