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
import com.example.myapplication.data.models.TrainTypeDto;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class WorkoutTypeAddDialog extends BottomSheetDialogFragment {

    private EditText editTextTrainTypeTitle;
    private Button btnAddTrainType;

    public WorkoutTypeAddDialog.OnInputListener mOnInputListener;

    public interface OnInputListener {
        void sendInput(TrainTypeDto trainTypeDto);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.train_type_add_dialog, container, false);
        editTextTrainTypeTitle = view.findViewById(R.id.editTextAddTrainType);
        btnAddTrainType = view.findViewById(R.id.btnAddTrainType);
        btnAddTrainType.setOnClickListener(v -> {
            WorkoutTypeAddDialog.OnInputListener listener = (WorkoutTypeAddDialog.OnInputListener) getActivity();
            TrainTypeDto trainTypeDto = TrainTypeDto.builder()
                    .title(editTextTrainTypeTitle.getText().toString())
                    .build();
            listener.sendInput(trainTypeDto);
            dismiss();
        });
        return view;
    }

}
