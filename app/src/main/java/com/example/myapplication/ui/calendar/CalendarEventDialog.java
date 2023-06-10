package com.example.myapplication.ui.calendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.utils.DBOpenHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarEventDialog extends BottomSheetDialogFragment {

    private Date dayId;

    private DBOpenHelper dbOpenHelper;
    private Context context;

    public CalendarEventDialog(Date dayId, Context context) {
        this.dayId = dayId;
        this.context = context;
    }

//    public interface CalendarEventListener {
//        void sendRequestEvent(String value);
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_calendar_event, container, false);
        // в заголовке указываем выбранную дату
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        TextView tvEventDate = view.findViewById(R.id.textViewCalendarEventDate);
        tvEventDate.setText(sdf.format(dayId));

        EditText editTextCalendarEventWorkoutType = view.findViewById(R.id.editTextCalendarEventWorkoutType);
        Button addButtonCalendarEventWorkoutType = view.findViewById(R.id.addButtonCalendarEventWorkoutType);
        addButtonCalendarEventWorkoutType.setOnClickListener(v -> {
//            CalendarEventListener listener = (CalendarEventListener) getActivity();
//            listener.sendRequestEvent(editTextCalendarEventWorkoutType.getText().toString());
            // записываем тренировку в бд
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dayId);
            SimpleDateFormat ssd = new SimpleDateFormat("MM");
            String mont = ssd.format(dayId);
            System.out.println(editTextCalendarEventWorkoutType.getText().toString() + " ; " + "123456" + " ; " + sdf.format(dayId) + " ; " + mont + " ; " + calendar.get(Calendar.YEAR));
            addEvent(editTextCalendarEventWorkoutType.getText().toString(), "123456", sdf.format(dayId), mont, String.valueOf(calendar.get(Calendar.YEAR)));
            dismiss();
        });
        return view;
    }

    private void addEvent(String event, String uid, String date, String month, String year) {
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.saveEvent(event, uid, date, month, year, database);
        dbOpenHelper.close();
        Toast.makeText(context, "Тренировка добавлена в журнал", Toast.LENGTH_SHORT).show();
    }
}
