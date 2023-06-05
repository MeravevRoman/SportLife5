package com.example.myapplication.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.models.WorkoutDto;

import java.util.List;

public class WorkoutsListAdapter extends RecyclerView.Adapter<WorkoutsListAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<WorkoutDto> workoutList;

    public WorkoutsListAdapter(Context ctx, List<WorkoutDto> workoutList) {
        this.inflater = LayoutInflater.from(ctx);
        this.workoutList = workoutList;
    }

    @NonNull
    @Override
    public WorkoutsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.workouts_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutsListAdapter.ViewHolder holder, int position) {
        WorkoutDto upcomingWorkoutDto = workoutList.get(position);
        holder.workoutDate.setText(upcomingWorkoutDto.getWorkoutDate());
        holder.workoutType.setText(upcomingWorkoutDto.getWorkoutType());
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView workoutDate;
        private TextView workoutType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutDate = itemView.findViewById(R.id.workoutDate);
            workoutType = itemView.findViewById(R.id.workoutType);
        }
    }
}
