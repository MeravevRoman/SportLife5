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

public class HeightDialog extends BottomSheetDialogFragment {

    public interface HeightListener {
        void sendHeightInput(String value);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_height, container, false);
        EditText editTextHeight = view.findViewById(R.id.editTextHeight);
        Button addButtonHeight = view.findViewById(R.id.addButtonHeight);
        addButtonHeight.setOnClickListener(v -> {
            HeightListener listener = (HeightListener) getActivity();
            listener.sendHeightInput(editTextHeight.getText().toString());
            dismiss();
        });
        return view;
    }
}
