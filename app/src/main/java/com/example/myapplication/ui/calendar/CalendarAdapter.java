package com.example.myapplication.ui.calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class CalendarAdapter extends BaseAdapter {
    private ArrayList<String> days;
    private LayoutInflater inflater;

    public CalendarAdapter(Context ctx, ArrayList<String> days) {
        this.days = days;
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        int amount = 0;
        if (days.size() > 1) {
            amount = days.size();
        }
        return amount;
    }

    @Override
    public Object getItem(int i) {
        String result = null;
        if (days.size() >= i) {
            result = days.get(i);
        }
        return result;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        ViewHolder holder = null;

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.control_calendar_day, parent, false);
            holder.tvDay = view.findViewById(R.id.day);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // настраиваем оформление TextView
        holder.tvDay.setTextColor(Color.BLACK);
        holder.tvDay.setText(days.get(i));

        return view;
    }

    public class ViewHolder {
        private TextView tvDay;
    }
}