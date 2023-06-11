package com.example.myapplication.ui.calendar.month;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.ui.calendar.CalendarEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MonthAdapter extends BaseAdapter {

    private List<Date> dates;
    private List<CalendarEvent> events;
    private Calendar currentDate;
    private LayoutInflater inflater;

    public MonthAdapter(Context ctx, List<Date> dates, List<CalendarEvent> events, Calendar currentDate) {
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dates = dates;
        this.events = events;
        this.currentDate = currentDate;
    }

    @Override
    public int getCount() {
        int amount = 0;
        if (dates.size() > 1) {
            amount = dates.size();
        }
        return amount;
    }

    @Override
    public Date getItem(int i) {
        Date result = null;
        if (dates.size() >= i) {
            result = dates.get(i);
        }
        return result;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        ViewHolder holder;

        Date monthDate = dates.get(i);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDate);

        int day = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int month = dateCalendar.get(Calendar.MONTH) + 1;
        int year = dateCalendar.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.control_calendar_month_item, parent, false);
            holder.tvDay = view.findViewById(R.id.day);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // настраиваем оформление TextView для дней текущего месяца
        if (month == currentMonth && year == currentYear) {
            holder.tvDay.setTextColor(Color.BLACK);
        } else {
            holder.tvDay.setTextColor(Color.GRAY);
        }
        // настраиваем оформление TextView
//        holder.tvDay.setTextColor(Color.BLACK);
        holder.tvDay.setText(String.valueOf(day));

        Calendar eventCalendar = Calendar.getInstance();
        List<String> lst = new ArrayList<>();
        for (int j = 0; j < events.size(); j++) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date d;
            try {
                d = sdf.parse(events.get(j).getDate());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            eventCalendar.setTime(d);
            if (day == eventCalendar.get(Calendar.DAY_OF_MONTH) &&
                    month == eventCalendar.get(Calendar.MONTH) + 1 &&
                    year == eventCalendar.get(Calendar.YEAR)) {
                lst.add(events.get(j).getEvent());
                view.setBackground(view.getContext().getResources().getDrawable(R.drawable.shape_day_with_events));
            }

        }

        return view;
    }

    public class ViewHolder {
        private TextView tvDay;
    }
}
