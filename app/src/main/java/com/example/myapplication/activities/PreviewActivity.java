package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class PreviewActivity extends AppCompatActivity {

    private Button btnSignInAsCoach;
    private Button btnSignInAsSportsman;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_activity);
        this.getSupportActionBar().hide();

        btnSignInAsCoach = findViewById(R.id.btnSignInAsCoach);
        btnSignInAsSportsman = findViewById(R.id.btnSignInAsSportsman);

        // пока что без авторизации
        // экран авторизации в работе
        btnSignInAsCoach.setOnClickListener(v -> {
            // работа с тренером
            Intent intent = new Intent(PreviewActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        btnSignInAsSportsman.setOnClickListener(v -> {
            // работа со спортсменом
            Intent intent = new Intent(PreviewActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
