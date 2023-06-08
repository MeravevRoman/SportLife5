package com.example.myapplication.ui.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarView extends LinearLayout {

    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;
    private OnClickItemCalendar listener;

    public CalendarView(Context context) {
        super(context);
    }
    public CalendarView(Context context, AttributeSet attr) {
        super(context);
        initControl(context);
    }

    private void initControl(Context ctx) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.control_calendar, this);

        header = findViewById(R.id.calendar_header);
        btnNext = findViewById(R.id.calendar_next_button);
        btnPrev = findViewById(R.id.calendar_prev_button);
        txtDate = findViewById(R.id.calendar_date_display);
        grid = findViewById(R.id.calendar_grid);

        Calendar currentDate = Calendar.getInstance();

        updateCalendar(currentDate);

        btnNext.setOnClickListener(v -> {
            currentDate.add(Calendar.MONTH, +1);
            updateCalendar(currentDate);
        });
        btnPrev.setOnClickListener(v -> {
            currentDate.add(Calendar.MONTH, -1);
            updateCalendar(currentDate);
        });

        grid.setOnItemClickListener((adapterView, view, i, l) -> {
            OnClickItemCalendar listener = (OnClickItemCalendar) ctx;
            listener.onClickItem((String) grid.getItemAtPosition(i));
        });
    }

    private void updateCalendar(Calendar currentDate)
    {
        grid.setAdapter(null);
        ArrayList<String> cells = new ArrayList<>();
        // число дней в месяце
        int monthDays = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        currentDate.set(Calendar.DAY_OF_MONTH, 1);
        // день, с которого начинается месяц
        int monthBeginnigCell = currentDate.get(Calendar.DAY_OF_WEEK) - 1;
        // заполняем пустые ячейки, если месяц начался не с понедельника
        for (int i = 0; i < monthBeginnigCell - 1; i++) {
            cells.add("");
        }
        // заполняем остальные дни месяца
        for (int i = 1; i < monthDays + 1; i++)
        {
            cells.add("" + i);
        }
        // заполняем таблицу (gridview) с днями
        CalendarAdapter adapter = new CalendarAdapter(getContext(), cells);
        grid.setAdapter(adapter);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
        txtDate.setText(sdf.format(currentDate.getTime()));
    }

    public void setOnDateChangeListener(OnClickItemCalendar listener) {
        this.listener = listener;
    }


    public interface OnClickItemCalendar {
        void onClickItem(String dayId);
    }

}