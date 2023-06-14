package com.example.myapplication.ui.goals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.models.CompetitionDto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PastGoalsFragment extends Fragment {

    private RecyclerView recyclerView;

    public PastGoalsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_goals_tab_past, container, false);
        recyclerView = view.findViewById(R.id.rvGoals);
        List<CompetitionDto> competitions = new ArrayList<>();
        for (int i = 1; i < 2; i++) {
            competitions.add(new CompetitionDto().builder()
                    .id(i)
                    .title("VII Традиционный Пискаревский полумарафон")
                    .description("VII Традиционный Пискаревский полумарафон")
                    .date(Calendar.getInstance())
                    .place("Санкт-Петербург")
                    .anounce("Регистрация до 13/05/23 08:45")
                    .result(null)
                    .build());
        }
        GoalsItemsAdapter adapter = new GoalsItemsAdapter(getContext(), competitions);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
