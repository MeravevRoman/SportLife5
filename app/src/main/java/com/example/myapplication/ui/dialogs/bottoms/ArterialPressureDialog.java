package com.example.myapplication.ui.dialogs.bottoms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ArterialPressureDialog extends BottomSheetDialogFragment {

    public interface ArterialPressureListener {
        void sendArterialPressureInput(long value);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.arterial_pressure_dialog, container, false);
        EditText editTextArteraialPressure = view.findViewById(R.id.editTextArterialPressure);
        Button btnAddArterialPressure = view.findViewById(R.id.btnAddArterialPressure);
        btnAddArterialPressure.setOnClickListener(v -> {
            ArterialPressureListener listener = (ArterialPressureListener) getActivity();
            listener.sendArterialPressureInput(Long.valueOf(editTextArteraialPressure.getText().toString()));
            dismiss();
        });
        return view;
    }
}
