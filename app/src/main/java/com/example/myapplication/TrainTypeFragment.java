package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.adapters.TrainTypeListAdapter;
import com.example.myapplication.dialogs.bottoms.TrainTypeAddDialog;
import com.example.myapplication.models.TrainTypeDto;

import java.util.ArrayList;
import java.util.List;

public class TrainTypeFragment extends Fragment implements TrainTypeAddDialog.OnInputListener {

    private RecyclerView recyclerView;
    private List<TrainTypeDto> trainTypeDtoList;
    private Button addTrainTypeBtn;

    public TrainTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trainTypeDtoList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_train_type, container, false);

        addTrainTypeBtn = view.findViewById(R.id.addNewTrainTypeBtn);

        addTrainTypeBtn.setOnClickListener(v -> {
            TrainTypeAddDialog trainTypeAddDialog = new TrainTypeAddDialog();
            trainTypeAddDialog.show(getParentFragmentManager(), "train type add dialog");
        });

        trainTypeDtoList.add(TrainTypeDto.builder().title("Силовая").build());
        trainTypeDtoList.add(TrainTypeDto.builder().title("Беговая").build());
        trainTypeDtoList.add(TrainTypeDto.builder().title("Тренировка в зале").build());
        trainTypeDtoList.add(TrainTypeDto.builder().title("Тренировка на улице").build());
        trainTypeDtoList.add(TrainTypeDto.builder().title("Игровая тренировка").build());

        TrainTypeListAdapter trainTypeListAdapter = new TrainTypeListAdapter(this.getContext(), trainTypeDtoList);
        try {
            recyclerView = view.findViewById(R.id.select_train_type_list);
            recyclerView.setAdapter(trainTypeListAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void sendInput(TrainTypeDto trainTypeDto) {
        Log.d("SPORT_APP", "train type added: " + trainTypeDto.toString());
    }
}