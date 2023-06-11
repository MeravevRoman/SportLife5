package com.example.myapplication.ui.fragments.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Main;
import com.example.myapplication.R;
import com.example.myapplication.data.models.UserDto;
import com.example.myapplication.data.models.WorkoutDto;
import com.example.myapplication.ui.activities.NewWorkoutActivity;
import com.example.myapplication.ui.activities.WorkoutsActivity;
import com.example.myapplication.ui.adapters.WorkoutsListAdapter;
import com.example.myapplication.ui.calendar.CalendarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainFragment extends Fragment {

    private Context context;

    private CalendarView calendarView;

    private RecyclerView rvUpcomingWorkouts;
    private RecyclerView rvPastWorkouts;
    private List<WorkoutDto> upcomingWorkoutDtoList;
    private List<WorkoutDto> pastWorkoutDtoList;

    private TextView textViewUserTitle;

    @Inject
    FirebaseDatabase dbInstance;
    @Inject
    FirebaseAuth mAuth;

    public MainFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((WorkoutsActivity) getActivity()).pagesComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        calendarView = view.findViewById(R.id.calendarView);

        textViewUserTitle = view.findViewById(R.id.textViewUserTitle);

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
                    Toast.makeText(context, "Ошибка получения данных из БД", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e("SPORT_APP", e.getMessage());
        }

        final ImageButton imageButton = view.findViewById(R.id.imageButton15);
        imageButton.setOnClickListener(v -> {
            Intent activityChangeIntent = new Intent(context, NewWorkoutActivity.class);
            context.startActivity(activityChangeIntent);
        });

        // upcoming workouts block
        WorkoutsListAdapter upcomingWorkoutsListAdapter = new WorkoutsListAdapter(context, upcomingWorkoutDtoList);
        try {
            rvUpcomingWorkouts = view.findViewById(R.id.upcoming_workouts_list);
            rvUpcomingWorkouts.setAdapter(upcomingWorkoutsListAdapter);
        } catch (Exception e) {
            Log.e("SPORT_APP", e.getMessage());
        }
        // upcoming workouts end
        // past workouts block
        WorkoutsListAdapter pastWorkoutsListAdapter = new WorkoutsListAdapter(context, pastWorkoutDtoList);
        try {
            rvPastWorkouts = view.findViewById(R.id.past_workouts_list);
            rvPastWorkouts.setAdapter(pastWorkoutsListAdapter);
        } catch (Exception e) {
            Log.e("SPORT_APP", e.getMessage());
        }
        // past workouts end

        return view;
    }
}
