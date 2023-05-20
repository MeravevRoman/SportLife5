package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private Button button11;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        button11 = findViewById(R.id.button11);

        textView2Edt =findViewById(R.id.textView2);
        textView5Edt =findViewById(R.id.textView5);
        textView23Edt =findViewById(R.id.textView23);
        textView26Edt =findViewById(R.id.textView26);
        textView29Edt =findViewById(R.id.textView29);
        textView30Edt =findViewById(R.id.textView30);
        textView40Edt =findViewById(R.id.textView40);

        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Пока что для сохранения записи тренировки будет использоваться кнопка добавить видео, как паша добавит кнопку я поменяю

                textView2 = textView2Edt.getText().toString();
                textView5 = textView5Edt.getText().toString();
                textView23 = textView23Edt.getText().toString();
                textView26 = textView26Edt.getText().toString();
                textView29 = textView29Edt.getText().toString();
                textView30 = textView30Edt.getText().toString();
                textView40 = textView40Edt.getText().toString();

                User user = new User(textView2, textView5, textView23, textView26, textView29, textView30, textView40);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("name", user);
                startActivity(intent);

            }
        });
    }
}