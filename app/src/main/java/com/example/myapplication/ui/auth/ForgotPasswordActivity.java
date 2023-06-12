package com.example.myapplication.ui.auth;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button btnChangePassword;
    private EditText editTextRestoredEmail;

    private TextView textView59;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        this.getSupportActionBar().hide();

        btnChangePassword = findViewById(R.id.btnChangePassword);
        editTextRestoredEmail = findViewById(R.id.editTextRestoredEmail);
        textView59 = findViewById(R.id.textView59);

        btnChangePassword.setOnClickListener(v -> {
            if (editTextRestoredEmail.getText().toString().trim().length() != 0) {
                // отправить сообщение на указанную почту, оповестить об этом пользователя
                // произойдет сброс старого пароля, старый пароль поместится в архив, чтобы его нельзя было повторить
                // пользователь сможет войти в сервис с помощью нового пароля

                // с телефонными кодами возможно возникнет проблема в оплате услуг (если оплата не в рублях)
                // и стоит обратить внимание на закон о хранении персональных данных
                // https://vc.ru/legal/162911-personalnye-dannye-kak-hranit-obrabatyvat-i-zashchishchat-po-zakonu
                // хранить персональные данные в firebase нельзя,
                // но для тестов оставим его, потом переведем на spring data + postgresql
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            } else {
                textView59.setTextColor(Color.RED);
            }
        });
    }
}
