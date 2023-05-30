package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextUserName;
    private EditText editTextPhone;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ImageButton imageButtonUserPhoto;
    private Button btnSaveUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        this.getSupportActionBar().hide();

        editTextUserName = findViewById(R.id.editTextUsername);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        imageButtonUserPhoto = findViewById(R.id.imageButtonUserPhoto);
        btnSaveUser = findViewById(R.id.finishRegistration);

        btnSaveUser.setOnClickListener(v -> {
            if (!editTextIsEmpty(editTextUserName) && !editTextIsEmpty(editTextEmail) && !editTextIsEmpty(editTextPassword) && !editTextIsEmpty(editTextPhone)) {
                // если все поля заполнены, отправляем данные формы в базу данных
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean editTextIsEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }
}
