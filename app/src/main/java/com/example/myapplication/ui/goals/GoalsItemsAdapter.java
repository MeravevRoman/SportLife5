package com.example.myapplication.ui.goals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.models.CompetitionDto;

import java.text.SimpleDateFormat;
import java.util.List;

public class GoalsItemsAdapter extends RecyclerView.Adapter<GoalsItemsAdapter.ViewHolder> {

    private Context context;
    private List<CompetitionDto> competitions;

    public GoalsItemsAdapter(Context context, List<CompetitionDto> competitions) {
        this.competitions = competitions;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_competitions_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CompetitionDto competition = competitions.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        holder.tvDate.setText(dateFormat.format(competition.getDate().getTime()));
        holder.tvTime.setText(timeFormat.format(competition.getDate().getTime()));
        holder.tvTitle.setText(competition.getTitle());
        holder.tvPlace.setText(competition.getPlace());
        holder.tvAnounce.setText(competition.getAnounce());
    }

    @Override
    public int getItemCount() {
        return competitions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDate;
        private TextView tvTime;
        private TextView tvTitle;
        private TextView tvPlace;
        private TextView tvAnounce;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvDate = itemView.findViewById(R.id.textView64);
            this.tvTime = itemView.findViewById(R.id.textView65);
            this.tvPlace = itemView.findViewById(R.id.textView68);
            this.tvTitle = itemView.findViewById(R.id.textView66);
            this.tvAnounce = itemView.findViewById(R.id.textView67);
        }
    }
}
