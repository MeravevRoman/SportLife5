package com.example.myapplication.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.activities.MainActivity;
import com.example.myapplication.activities.TrainTypeActivity;
import com.example.myapplication.dialogs.bottoms.HeightDialog;
import com.example.myapplication.dialogs.scrollers.TrainDurationDialog;
import com.example.myapplication.dialogs.bottoms.WeightDialog;
import com.example.myapplication.dialogs.scrollers.SleepTimeDialog;
import com.example.myapplication.models.TrainTypeDto;
import com.google.gson.Gson;

public class NewTrainActivity extends AppCompatActivity implements SleepTimeDialog.SleepTimeListener,
        HeightDialog.HeightListener,
        WeightDialog.WeightListener,
        TrainDurationDialog.TrainDurationListener {

    private final Gson gson = new Gson();

    private Button button11;
    private Button button4; // Вид тренировки
    private Button button5; // Програма тренировки
    private Button button6; // Продолжительность тренировки
    private Button button7; // Время ночного сна
    private Button button8; // Рост
    private Button button9; // Вес

    private EditText textView2Edt;
    private EditText textView5Edt;
    private EditText textView23Edt;
    private EditText textView26Edt;
    private EditText textView29Edt;
    private EditText textView30Edt;
    private EditText textView40Edt;

    private String textView2;
    private String textView5;
    private String textView23;
    private String textView26;
    private String textView29;
    private String textView30;
    private String textView40;
    private TextView textViewSelectedTrainType;
    private TextView textViewTypedTrainTime;
    private TextView textViewTypedSleepTime;
    private TextView textViewTypedHeight;
    private TextView textViewTypedWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        this.getSupportActionBar().hide();

        button11 = findViewById(R.id.button11);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);

        textView2Edt =findViewById(R.id.textView2);
        textView5Edt =findViewById(R.id.textView5);
        textView23Edt =findViewById(R.id.textView23);
        textView26Edt =findViewById(R.id.textView26);
        textView29Edt =findViewById(R.id.textView29);
        textView30Edt =findViewById(R.id.textView30);
        textView40Edt =findViewById(R.id.textView40);
        textViewSelectedTrainType = findViewById(R.id.textViewSelectedTrainType);
        textViewTypedTrainTime = findViewById(R.id.textViewTypedTrainTime);
        textViewTypedSleepTime = findViewById(R.id.textViewTypedSleepTime);
        textViewTypedHeight = findViewById(R.id.textViewTypedHeight);
        textViewTypedWeight = findViewById(R.id.textViewTypedWeight);



        button11.setOnClickListener(view -> {
            //Пока что для сохранения записи тренировки будет использоваться кнопка добавить видео, как паша добавит кнопку я поменяю

            textView2 = textView2Edt.getText().toString();
            textView5 = textView5Edt.getText().toString();
            textView23 = textView23Edt.getText().toString();
            textView26 = textView26Edt.getText().toString();
            textView29 = textView29Edt.getText().toString();
            textView30 = textView30Edt.getText().toString();
            textView40 = textView40Edt.getText().toString();

            User user = new User().builder()
                    .textView2(textView2)
                    .textView5(textView5)
                    .textView23(textView23)
                    .textView26(textView26)
                    .textView29(textView29)
                    .textView30(textView30)
                    .textView40(textView40)
                    .build();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("name", user.toString());
            startActivity(intent);
        });

        button6.setOnClickListener(view -> {
            // окошко "продолжительность тренировки"
            TrainDurationDialog schedulerDialog = new TrainDurationDialog();
            schedulerDialog.show(getSupportFragmentManager(), "scheduler");
        });

        button7.setOnClickListener(view -> {
            // окошко 'время ночного сна'
            SleepTimeDialog sleepTimeDialog = new SleepTimeDialog();
            sleepTimeDialog.show(getSupportFragmentManager(), "sleep time");
        });
        button8.setOnClickListener(view -> {
            // окошко 'указать рост'
            HeightDialog heightDialog = new HeightDialog();
            heightDialog.show(getSupportFragmentManager(), "height");
        });
        button9.setOnClickListener(view -> {
            // окошко 'указать вес'
            WeightDialog weightDialog = new WeightDialog();
            weightDialog.show(getSupportFragmentManager(), "weight textview");
        });

        button4.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TrainTypeActivity.class);
            startActivityForResult(intent, 1);
        });

    }

    // слушатель для введенной информации о времени ночного сна
    @Override
    public void sendSleepTimeInput(long value) {
        Log.d("SPORT_APP", "sleep time value: " + value);
        textViewTypedSleepTime.setText(String.valueOf(value));
    }

    // слушатель для введенной информации о типе тренировке
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("SPORT_APP", "Выбран тип тренировки: " + data.getStringExtra("selectedTrainType"));
        try {
            textViewSelectedTrainType.setText(gson.fromJson(data.getStringExtra("selectedTrainType"), TrainTypeDto.class).getTitle());
        } catch (Exception e) {
            Log.d("SPORT_APP", e.getMessage());
        }
    }

    // слушатель для введенной информации о росте
    @Override
    public void sendHeightInput(String value) {
        Log.d("SPORT_APP", "height value: " + value);
        textViewTypedHeight.setText(value);
    }

    // слушатель для введенной информации о весе
    @Override
    public void sendWeightInput(String value) {
        Log.d("SPORT_APP", "weight value: " + value);
        textViewTypedWeight.setText(value);
    }

    @Override
    public void sendTrainDurationInput(long value) {
        Log.d("SPORT_APP", "train duration value: " + value);
        textViewTypedTrainTime.setText(String.valueOf(value));
    }
}