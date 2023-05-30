package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button btnChangePassword;
    private EditText editTextRestoredEmail;

    private TextView textView59;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_activity);
        this.getSupportActionBar().hide();

        btnChangePassword = findViewById(R.id.btnChangePassword);
        editTextRestoredEmail = findViewById(R.id.editTextRestoredEmail);
        textView59 = findViewById(R.id.textView59);

        btnChangePassword.setOnClickListener(v -> {
            if (editTextRestoredEmail.getText().toString().trim().length() != 0) {
                // отправить сообщение на указанную почту, оповестить об этом пользователя
                // произойдет сброс старого пароля, старый пароль поместится в архив, чтобы его нельзя было повторить
                // пользователь сможет войти в сервис с помощью нового пароля

                // с телефонными кодами возникнет проблема в оплате услуг -
                // по-началу затрачиваться на смс-оповещения не обязательно
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            } else {
                textView59.setTextColor(Color.RED);
            }
        });
    }
}
