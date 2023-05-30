package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.myapplication.adapters.TrainTypeListAdapter;
import com.example.myapplication.dialogs.bottoms.TrainTypeAddDialog;
import com.example.myapplication.models.TrainTypeDto;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TrainTypeActivity extends AppCompatActivity implements TrainTypeAddDialog.OnInputListener,
        TrainTypeListAdapter.TrainTypeSelectListener {


    private final Gson gson = new Gson();
    private RecyclerView recyclerView;
    private List<TrainTypeDto> trainTypeDtoList;
    private Button addTrainTypeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_type);

        this.getSupportActionBar().hide();

        trainTypeDtoList = new ArrayList<>();
        addTrainTypeBtn = findViewById(R.id.addNewTrainTypeBtn);

        addTrainTypeBtn.setOnClickListener(v -> {
            TrainTypeAddDialog trainTypeAddDialog = new TrainTypeAddDialog();
            trainTypeAddDialog.show(getSupportFragmentManager(), "train type add dialog");
        });

        trainTypeDtoList.add(TrainTypeDto.builder().id(1).title("Силовая").build());
        trainTypeDtoList.add(TrainTypeDto.builder().id(2).title("Беговая").build());
        trainTypeDtoList.add(TrainTypeDto.builder().id(3).title("Тренировка в зале").build());
        trainTypeDtoList.add(TrainTypeDto.builder().id(4).title("Тренировка на улице").build());
        trainTypeDtoList.add(TrainTypeDto.builder().id(5).title("Игровая тренировка").build());

        TrainTypeListAdapter trainTypeListAdapter = new TrainTypeListAdapter(this, trainTypeDtoList);
        try {
            recyclerView = findViewById(R.id.select_train_type_list);
            recyclerView.setAdapter(trainTypeListAdapter);
        } catch (Exception e) {
            Log.e("SPORT_APP", e.getMessage());
        }

    }

    @Override
    public void sendInput(TrainTypeDto trainTypeDto) {
        Log.d("SPORT_APP", "Добавлен новый тип тренировки: " + trainTypeDto.toString());
        trainTypeDtoList.add(trainTypeDto);
    }

    @Override
    public void selectTrainType(TrainTypeDto dto) {
        Log.d("SPORT_APP", "Выбран тип тренировки: " + dto.toString());
        Intent i = new Intent();
        i.putExtra("selectedTrainType", gson.toJson(dto));
        setResult(RESULT_OK, i);
        finish();
    }
}