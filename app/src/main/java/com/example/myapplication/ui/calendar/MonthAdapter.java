package com.example.myapplication.ui.calendar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private final List<String> months;
    private MonthSelectListener listener;

    public interface MonthSelectListener {
        void selectMonth(int i);
    }

    public MonthAdapter(Context ctx, List<String> months, MonthSelectListener listener) {
        this.inflater = LayoutInflater.from(ctx);
        this.months = months;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.months_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvMonthTitle.setText(months.get(position));
        holder.tvMonthTitle.setOnClickListener(v -> {
            try {
                listener.selectMonth(position);
//                holder.itemView.setBackground(holder.itemView.getContext().getResources().getDrawable(R.drawable.shape_month_item_selected));
            } catch (Exception e) {
                Log.e("CALENDAR", e.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (months.size() > 0) {
            return months.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMonthTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMonthTitle = itemView.findViewById(R.id.monthTitle);
        }



    }
}
