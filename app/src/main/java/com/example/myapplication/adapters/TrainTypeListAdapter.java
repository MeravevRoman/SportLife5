package com.example.myapplication.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.TrainTypeDto;

import java.util.List;

public class TrainTypeListAdapter extends RecyclerView.Adapter<TrainTypeListAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<TrainTypeDto> trainTypeList;

    private TrainTypeSelectListener trainTypeSelectListener;

    public interface TrainTypeSelectListener {
        void selectTrainType(TrainTypeDto dto);
    }

    public TrainTypeListAdapter(Context ctx, List<TrainTypeDto> trainTypeList) {
        this.inflater = LayoutInflater.from(ctx);
        this.trainTypeList = trainTypeList;
        this.trainTypeSelectListener = (TrainTypeSelectListener) ctx;
    }

    @NonNull
    @Override
    public TrainTypeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.select_train_type_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainTypeListAdapter.ViewHolder holder, int position) {
        TrainTypeDto trainTypeDto = trainTypeList.get(position);
        holder.trainTypeTitle.setText(trainTypeDto.getTitle());
        holder.trainTypeSelectBtn.setOnClickListener(v -> {
            try {
                trainTypeSelectListener.selectTrainType(trainTypeDto);
            } catch (Exception e) {
                Log.d("SPORT_APP", e.getMessage());
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return trainTypeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // заполнить полями для описания вида тренировки
        private TextView trainTypeTitle;
        private Button trainTypeSelectBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            trainTypeTitle = itemView.findViewById(R.id.select_train_type_list_item_title);
            trainTypeSelectBtn = itemView.findViewById(R.id.select_train_type_item_from_list);
        }
    }
}
