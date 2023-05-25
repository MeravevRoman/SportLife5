package com.example.myapplication.dialogs.scrollers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;

public class SleepTimeDialog extends DialogFragment {

    private NumberPicker hourPicker;
    private NumberPicker minutesPicker;
    private NumberPicker secondsPicker;

    public OnInputListener mOnInputListener;

    public interface OnInputListener {
        void sendInput(long seconds);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.scheduler_dialog, null);

        String[] hours = new String[25];
        for (int i = 0; i < hours.length; i++)
            hours[i] = i + " ч";
        hourPicker = view.findViewById(R.id.hourPicker);
        hourPicker.setMaxValue(24);
        hourPicker.setMinValue(0);
        hourPicker.setDisplayedValues(hours);

        String[] minutes = new String[60];
        for (int i = 0; i < minutes.length; i++)
            minutes[i] = i + " мин";
        minutesPicker = view.findViewById(R.id.minutesPicker);
        minutesPicker.setMaxValue(59);
        minutesPicker.setMinValue(0);
        minutesPicker.setDisplayedValues(minutes);

        String[] seconds = new String[60];
        for (int i = 0; i < seconds.length; i++)
            seconds[i] = i + " сек";
        secondsPicker = view.findViewById(R.id.secondsPicker);
        secondsPicker.setMaxValue(59);
        secondsPicker.setMinValue(0);
        secondsPicker.setDisplayedValues(seconds);

        builder.setView(view)
                .setPositiveButton("ОК", (dialogInterface, i) -> {
                    // process success action
                    long sleepTimeInSeconds = hourPicker.getValue() * 3600 + minutesPicker.getValue() * 60 + secondsPicker.getValue();
                    OnInputListener listener = (OnInputListener) getActivity();
                    listener.sendInput(sleepTimeInSeconds);
                    System.out.println("sleep time: " + sleepTimeInSeconds);
                })
                .setNegativeButton("Отменить", (dialogInterface, i) -> {
                    // process fail action
                })
                .setNeutralButton("Очистить", ((dialogInterface, i) -> {
                    // process neutral action
                }));
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mOnInputListener = (OnInputListener) getActivity();
        } catch (ClassCastException e) {
            Log.e("SPORT_APP", "onAttach: ClassCastException: " + e.getMessage());
        }
    }
}
