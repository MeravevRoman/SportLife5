package com.example.myapplication.ui.preview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Main;
import com.example.myapplication.R;
import com.example.myapplication.di.components.PagesComponent;
import com.example.myapplication.ui.activities.WorkoutsActivity;
import com.example.myapplication.ui.auth.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class PreviewActivity extends AppCompatActivity {

    private PagesComponent pagesComponent;

    private Button btnSignInAsCoach;
    private Button btnSignInAsSportsman;

    @Inject
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_activity);
        this.getSupportActionBar().hide();

        pagesComponent = ((Main) getApplicationContext()).getAppComponent().pagesComponent().create();
        pagesComponent.inject(this);

        btnSignInAsCoach = findViewById(R.id.btnSignInAsCoach);
        btnSignInAsSportsman = findViewById(R.id.btnSignInAsSportsman);

        // пока что без подключения к бд
        btnSignInAsCoach.setOnClickListener(v -> {
            // работа с тренером
            if (mAuth.getCurrentUser() != null) {
                Intent intent = new Intent(PreviewActivity.this, WorkoutsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(PreviewActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnSignInAsSportsman.setOnClickListener(v -> {
            // работа со спортсменом
            if (mAuth.getCurrentUser() != null) {
                Intent intent = new Intent(PreviewActivity.this, WorkoutsActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(PreviewActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
