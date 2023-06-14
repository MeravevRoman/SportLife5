package com.example.myapplication.ui.auth;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Main;
import com.example.myapplication.R;
import com.example.myapplication.data.models.UserDto;
import com.example.myapplication.data.models.enums.GenderType;
import com.example.myapplication.data.models.enums.UserType;
import com.example.myapplication.di.components.PagesComponent;
import com.example.myapplication.ui.workouts.WorkoutsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.inject.Inject;

public class RegistrationActivity extends AppCompatActivity {

    private PagesComponent pagesComponent;

    private EditText editTextUserName;
    private EditText editTextPhone;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ImageButton imageButtonUserPhoto;
    private Button btnSaveUser;
    private TextView textViewAlreadyExistLogin;

    @Inject
    FirebaseDatabase dbInstance;
    @Inject
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        this.getSupportActionBar().hide();

        pagesComponent = ((Main) getApplicationContext()).getAppComponent().pagesComponent().create();
        pagesComponent.inject(this);

        // user form
        editTextUserName = findViewById(R.id.editTextUsername);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        imageButtonUserPhoto = findViewById(R.id.imageButtonUserPhoto);
        //
        btnSaveUser = findViewById(R.id.finishRegistration);
        textViewAlreadyExistLogin = findViewById(R.id.textViewLoginLink);

        btnSaveUser.setOnClickListener(v -> {
            if (!editTextIsEmpty(editTextUserName) && !editTextIsEmpty(editTextEmail) &&
                    !editTextIsEmpty(editTextPassword) && !editTextIsEmpty(editTextPhone)) {
                // если все поля заполнены, отправляем данные формы в базу данных
                Log.d("SPORT_APP", "Все поля заполнены, начинаю отправку данных в бд");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    try {
                        mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        createAccount(UserDto.builder()
                                                .id(UUID.randomUUID().toString())
                                                .username(editTextUserName.getText().toString())
                                                .password(editTextPassword.getText().toString())
                                                .email(editTextEmail.getText().toString())
                                                .phone(editTextPhone.getText().toString())
                                                .gender(GenderType.MALE)
                                                .type(UserType.USER_SPORTSMAN)
                                                .createdAt(LocalDateTime.now().toString())
                                                .build());
                                    } else {
                                        Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } catch (Exception e) {
                        Log.e("SPORT_APP", "Возникла ошибка при попытке регистрации");
                    }
                }
            }
        });

        textViewAlreadyExistLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void createAccount(UserDto userDto) {
        try {
            dbInstance.getReference("users").child(mAuth.getCurrentUser().getUid()).setValue(userDto, (error, ref) -> {
                if (error != null) {
                    Log.e("SPORT_APP", error.getMessage());
                } else {
                    Toast.makeText(getApplicationContext(), "Вы успешно зарегистрировались", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationActivity.this, WorkoutsActivity.class);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            Log.d("SPORT_APP", e.getMessage());
        }

    }

//    private void sendEmailVerification() {
//        final FirebaseUser user = mAuth.getCurrentUser();
//        user.sendEmailVerification()
//            .addOnCompleteListener(this, task -> {
//                // Email sent
//            });
//    }

    /**
     * Проверка полей на заполненность
     * если в поле ничего нет, возвращается true
     * иначе - false
     * @param editText
     * @return
     */
    private boolean editTextIsEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    /**
     * Обработка ситуации потери данных при повороте экрана
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("formUsername", editTextUserName.getText().toString());
        outState.putString("formPassword", editTextPassword.getText().toString());
        outState.putString("formEmail", editTextEmail.getText().toString());
        outState.putString("formPhone", editTextPhone.getText().toString());
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        editTextUserName.setText(savedInstanceState.getString("formUsername"));
        editTextPassword.setText(savedInstanceState.getString("formPassword"));
        editTextEmail.setText(savedInstanceState.getString("formEmail"));
        editTextPhone.setText(savedInstanceState.getString("formPhone"));
    }
}
