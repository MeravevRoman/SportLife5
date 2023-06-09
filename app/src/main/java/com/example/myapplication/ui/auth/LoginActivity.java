package com.example.myapplication.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Main;
import com.example.myapplication.R;
import com.example.myapplication.di.components.PagesComponent;
import com.example.myapplication.ui.workouts.WorkoutsActivity;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {

    private PagesComponent pagesComponent;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button btnLogin;
    private Button btnRegistration;
    private TextView textViewForgotPassword;

    @Inject
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getSupportActionBar().hide();

        pagesComponent = ((Main) getApplicationContext()).getAppComponent().pagesComponent().create();
        pagesComponent.inject(this);

        editTextEmail = findViewById(R.id.editTextLoginEmail);
        editTextPassword = findViewById(R.id.editTextLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistration = findViewById(R.id.btnRegistration);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);

        btnRegistration.setOnClickListener(v -> {
            // переход на экран регистрации
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
        btnLogin.setOnClickListener(v -> {
            if (!editTextIsEmpty(editTextEmail) && !editTextIsEmpty(editTextPassword)) {
                // после подключения к базе данных добавится проверка логина/пароля
                mAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString()).addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(LoginActivity.this, WorkoutsActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
        textViewForgotPassword.setOnClickListener(v -> {
            // переход на экран восстановления пароля
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private boolean editTextIsEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }
}
