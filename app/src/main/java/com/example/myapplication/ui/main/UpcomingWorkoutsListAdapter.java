package com.example.myapplication.ui.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.models.WorkoutDto;
import com.example.myapplication.data.models.WorkoutItemDto;

import java.util.List;

public class UpcomingWorkoutsListAdapter extends RecyclerView.Adapter<UpcomingWorkoutsListAdapter.ViewHolder> {

    private Context ctx;
    private final LayoutInflater inflater;
    private final List<WorkoutDto> workoutList;

    public UpcomingWorkoutsListAdapter(Context ctx, List<WorkoutDto> workoutList) {
        this.inflater = LayoutInflater.from(ctx);
        this.workoutList = workoutList;
    }

    @NonNull
    @Override
    public UpcomingWorkoutsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_workouts_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingWorkoutsListAdapter.ViewHolder holder, int position) {
        WorkoutDto upcomingWorkoutDto = workoutList.get(position);
        holder.workoutTitle.setText(upcomingWorkoutDto.getWorkoutTitle());

        List<WorkoutItemDto> workoutsContent = upcomingWorkoutDto.getItems();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(holder.rvWorkoutsContent.getContext(), LinearLayoutManager.HORIZONTAL, false);
        holder.rvWorkoutsContent.setLayoutManager(layoutManager);
        try {
            UpcomingWorkoutsContentItemsAdapter contentItemsAdapter = new UpcomingWorkoutsContentItemsAdapter(workoutsContent);
            holder.rvWorkoutsContent.setLayoutManager(layoutManager);
            holder.rvWorkoutsContent.setAdapter(contentItemsAdapter);
        } catch(Exception e) {
            Log.e("SPORT_APP", e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        // добавить проверку на ноль
        return workoutList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView workoutTitle;
        private RecyclerView rvWorkoutsContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutTitle = itemView.findViewById(R.id.tvWorkoutTitle);
            rvWorkoutsContent = itemView.findViewById(R.id.rvWorkoutsContent);
        }
    }
}
