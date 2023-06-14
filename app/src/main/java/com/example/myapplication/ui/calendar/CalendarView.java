package com.example.myapplication.ui.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.calendar.month.MonthAdapter;
import com.example.myapplication.ui.calendar.month.MonthTitleAdapter;
import com.example.myapplication.ui.calendar.week.WeekAdapter;
import com.example.myapplication.utils.DBOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarView extends LinearLayout {

    //
    // повторение кода - плохая практика
    // класс переделать - рассмотреть паттерн фабрика в dagger
    //
    private Context context;

    private DBOpenHelper dbOpenHelper;

    private RecyclerView recyclerView;
    private LinearLayout header;
    private GridView grid;
    private ImageView btnPrevWeek;
    private ImageView btnNextWeek;
    private List<Date> dates;
    private List<CalendarEvent> calendarEvents;
    private OnClickItemCalendar listener;
    private Calendar calendar = Calendar.getInstance();;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy");
    private SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

    private static final int MONTH_CALENDAR_DAYS = 42;
    private static final int WEEK_CALENDAR_DAYS = 6;

    private TypedArray attrs;

    public CalendarView(Context context) {
        super(context);
    }
    public CalendarView(Context context, AttributeSet attr) {
        super(context);
        this.context = context;
        // определяем вид отображения календаря (месяц/неделя)
        attrs = context.obtainStyledAttributes(attr, R.styleable.CalendarView, 0, 0);
        switch ((String) attrs.getText(R.styleable.CalendarView_calendarType)) {
            case "month":
                createMonthCalendar();
                break;
            case "week":
                createWeekCalendar();
                break;
            default:
                break;
        }
//        initControl(context);
    }

    private void createMonthCalendar() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_calendar_month, this);

        recyclerView = findViewById(R.id.month_string);
        header = findViewById(R.id.calendar_header);
        grid = findViewById(R.id.calendar_grid);
        dates = new ArrayList<>();
        calendarEvents = new ArrayList<>();

        updateCalendarMonth();

        List<String> months = new ArrayList<>(Arrays.asList("Январь", "Февраль", "Март",
                "Апрель", "Май", "Июнь", "Июль", "Август",
                "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"));

        MonthTitleAdapter adapter = new MonthTitleAdapter(getContext(), months, monthPosition -> {
            int currentMonth = calendar.get(Calendar.MONTH);
            int offset = 0;
            if (currentMonth <= monthPosition) {
                offset = monthPosition - currentMonth;
                calendar.add(Calendar.MONTH, +offset);
                updateCalendarMonth();
            } else {
                offset = currentMonth - monthPosition;
                calendar.add(Calendar.MONTH, -offset);
                updateCalendarMonth();
            }
        });
        recyclerView.setAdapter(adapter);

        // передаем значение даты при нажатии на дату
        grid.setOnItemClickListener((adapterView, view, i, l) -> {
            OnClickItemCalendar listener = (OnClickItemCalendar) context;
            listener.onClickItem((Date) grid.getItemAtPosition(i), context);
        });
    }

    private void createWeekCalendar() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_calendar_week, this);

        grid = findViewById(R.id.calendar_grid);
        btnNextWeek = findViewById(R.id.btnCalendarWeekForward);
        btnPrevWeek = findViewById(R.id.btnCalendarWeekBack);
        dates = new ArrayList<>();
        calendarEvents = new ArrayList<>();

        updateCalendarWeek();

        btnPrevWeek.setOnClickListener(view -> {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            updateCalendarWeek();
        });
        btnNextWeek.setOnClickListener(view -> {
            calendar.add(Calendar.DAY_OF_MONTH, +1);
            updateCalendarWeek();
        });

        // передаем значение даты при нажатии на дату
        grid.setOnItemClickListener((adapterView, view, i, l) -> {
            OnClickItemCalendar listener = (OnClickItemCalendar) context;
            listener.onClickItem((Date) grid.getItemAtPosition(i), context);
        });
    }

    private void updateCalendarMonth()
    {
        // подготовка данных перед отображением на экране
        dates.clear();
        grid.setAdapter(null);

        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);

        // день, с которого начинается месяц
        int monthBeginningDay = monthCalendar.get(Calendar.DAY_OF_WEEK) - 2;
        monthCalendar.add(Calendar.DAY_OF_MONTH, -monthBeginningDay);

        while (dates.size() < MONTH_CALENDAR_DAYS) {
            dates.add((Date) monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        SimpleDateFormat ssd = new SimpleDateFormat("MM");
        // добавляем события на странице календаря
        // плохая реализация - каждое нажатие кидает запрос в бд -> нагрузка на сервер и долгие ответы
        // переделать - добавить кеширование
        collectEvents(ssd.format(calendar.getTime()), yearFormat.format(calendar.getTime()));
        // заполняем таблицу (gridview) с днями
        MonthAdapter adapter = new MonthAdapter(getContext(), dates, calendarEvents, calendar);
        grid.setAdapter(adapter);
    }

    private void updateCalendarWeek()
    {
        // подготовка данных перед отображением на экране
        dates.clear();
        grid.setAdapter(null);

        Calendar monthCalendar = (Calendar) calendar.clone();

        while (dates.size() < WEEK_CALENDAR_DAYS) {
            dates.add((Date) monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        SimpleDateFormat ssd = new SimpleDateFormat("MM");
        // добавляем события на странице календаря
        // плохая реализация - каждое нажатие кидает запрос в бд -> нагрузка на сервер и долгие ответы
        // переделать - добавить кеширование
        collectEvents(ssd.format(calendar.getTime()), yearFormat.format(calendar.getTime()));
        // заполняем таблицу (gridview) с днями
        WeekAdapter adapter = new WeekAdapter(getContext(), dates, calendarEvents, calendar);
        grid.setAdapter(adapter);
    }

    private void collectEvents(String month, String year) {
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        System.out.println("try collect data by month " + month + " and year " + year);
        Cursor cursor = dbOpenHelper.readEventPerMonth(month, year, database);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String e = cursor.getString(cursor.getColumnIndex("event"));
            @SuppressLint("Range") String u = cursor.getString(cursor.getColumnIndex("uid"));
            @SuppressLint("Range") String d = cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String m = cursor.getString(cursor.getColumnIndex("month"));
            @SuppressLint("Range") String y = cursor.getString(cursor.getColumnIndex("year"));
            CalendarEvent event = new CalendarEvent(d, d, e, u);
            calendarEvents.add(event);
        }
        cursor.close();
        dbOpenHelper.close();
    }

    public interface OnClickItemCalendar {
        void onClickItem(Date dayId, Context context);
    }

}
