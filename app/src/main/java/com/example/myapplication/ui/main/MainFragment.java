package com.example.myapplication.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.models.UserDto;
import com.example.myapplication.data.models.WorkoutDto;
import com.example.myapplication.data.models.WorkoutItemDto;
import com.example.myapplication.ui.graph.BarView;
import com.example.myapplication.ui.workouts.WorkoutsActivity;
import com.example.myapplication.ui.calendar.CalendarView;
import com.example.myapplication.utils.GraphUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class MainFragment extends Fragment {

    private Context context;

    private CalendarView calendarView;

    private RecyclerView rvUpcomingWorkouts;
    private List<WorkoutDto> upcomingWorkoutDtoList;

    private TextView textViewUserTitle;

    private BarView barView;

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

        List<WorkoutItemDto> workoutItemList = new ArrayList<>();
//        workoutItemList.add(new WorkoutItemDto().builder().type("сет").value(4).build());
        workoutItemList.add(new WorkoutItemDto().builder().type("повт").value(15).build());
        workoutItemList.add(new WorkoutItemDto().builder().type("интервал, с").value(15).build());
        upcomingWorkoutDtoList = new ArrayList<>();
        upcomingWorkoutDtoList.add(WorkoutDto.builder()
                .id(1)
                .workoutDate(new Date())
                .workoutTitle("Силовая тренировка")
                .items(workoutItemList)
                .build());
        upcomingWorkoutDtoList.add(WorkoutDto.builder()
                .id(2)
                .workoutDate(new Date())
                .workoutTitle("Беговая тренировка")
                .items(workoutItemList)
                .build());

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

//      Удалил кнопку с главного экрана
//        final ImageButton imageButton = view.findViewById(R.id.imageButton15);
//        imageButton.setOnClickListener(v -> {
//            Intent activityChangeIntent = new Intent(context, NewWorkoutActivity.class);
//            context.startActivity(activityChangeIntent);
//        });

        // upcoming workouts block
        UpcomingWorkoutsListAdapter upcomingWorkoutsListAdapter = new UpcomingWorkoutsListAdapter(context, upcomingWorkoutDtoList);
        try {
            rvUpcomingWorkouts = view.findViewById(R.id.upcoming_workouts_list);
            rvUpcomingWorkouts.setAdapter(upcomingWorkoutsListAdapter);
            rvUpcomingWorkouts.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        } catch (Exception e) {
            Log.e("SPORT_APP", e.getMessage());
        }

        barView = view.findViewById(R.id.bar_view);
        setData(barView);

        return view;
    }

    private void setData(BarView barView){
        // данные для названий столбиков (например названия месяцев)
        List<String> keys = new ArrayList<>(Arrays.asList("Янв", "Фев", "Март", "Апр", "Май", "Июнь", "Июль",
                "Авг", "Сен", "Окт", "Ноя", "Дек"));
        // данные для количества единиц в единицу времени
        List<Integer> values = new ArrayList<>(Arrays.asList(530, 301, 480, 577, 213));

        int max = GraphUtils.getMax(values);
        barView.setValueList(values, max);
        barView.setKeyList(keys);
    }
}
