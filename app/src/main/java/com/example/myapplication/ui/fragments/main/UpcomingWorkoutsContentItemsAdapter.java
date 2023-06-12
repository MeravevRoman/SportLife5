package com.example.myapplication.ui.fragments.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.models.WorkoutItemDto;

import java.util.List;

public class UpcomingWorkoutsContentItemsAdapter extends RecyclerView.Adapter<UpcomingWorkoutsContentItemsAdapter.ViewHolder> {
    private List<WorkoutItemDto> itemsList;

    public UpcomingWorkoutsContentItemsAdapter(List<WorkoutItemDto> itemsList) {
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_content_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkoutItemDto workoutItemDto = itemsList.get(position);
        holder.tvWorkoutContentValue.setText(String.valueOf(workoutItemDto.getValue()));
        holder.tvWorkoutContentType.setText(workoutItemDto.getType());
    }

    @Override
    public int getItemCount() {
        // добавить проверку на ноль
        return itemsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvWorkoutContentValue;
        private TextView tvWorkoutContentType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvWorkoutContentValue = itemView.findViewById(R.id.tvWorkoutContentValue);
            this.tvWorkoutContentType = itemView.findViewById(R.id.tvWorkoutContentType);
        }

    }
}
