package com.example.myapplication.ui.goals;

import android.content.Context;
import android.content.Intent;
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
import com.example.myapplication.ui.competition_info.CompetitionInfoActivity;
import com.example.myapplication.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CurrentGoalsFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerView;

    public CurrentGoalsFragment() {
        this.context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_goals_tab_current, container, false);
        recyclerView = view.findViewById(R.id.rvGoals);
        List<CompetitionDto> competitions = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            competitions.add(new CompetitionDto().builder()
                    .id(i)
                    .title("VII Традиционный Пискаревский полумарафон")
                    .description("VII Традиционный Пискаревский полумарафон")
                    .date(Calendar.getInstance())
                    .place("Санкт-Петербург")
                    .anounce("Регистрация до 13/06/23 08:45")
                    .result(null)
                    .build());
        }
        GoalsItemsAdapter adapter = new GoalsItemsAdapter(getContext(), competitions);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), CompetitionInfoActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                //
            }
        }));
        return view;
    }
}
