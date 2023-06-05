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

public class WeightDialog extends BottomSheetDialogFragment {

    public interface WeightListener {
        void sendWeightInput(String value);
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weight_dialog, container, false);
        EditText editTextWeight = view.findViewById(R.id.editTextWeight);
        Button btnAddWeight = view.findViewById(R.id.btnAddWeight);
        btnAddWeight.setOnClickListener(v -> {
            WeightListener listener = (WeightListener) getActivity();
            listener.sendWeightInput(editTextWeight.getText().toString());
            dismiss();
        });
        return view;
    }
}
