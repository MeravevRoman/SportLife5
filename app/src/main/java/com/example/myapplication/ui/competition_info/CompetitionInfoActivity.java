package com.example.myapplication.ui.competition_info;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class CompetitionInfoActivity extends AppCompatActivity {

    private ImageView btnPrevPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition_info);
        this.getSupportActionBar().hide();
        this.btnPrevPage = findViewById(R.id.btnReturnPrevPage);

        btnPrevPage.setOnClickListener(v -> {
            // не работает
            getFragmentManager().popBackStack();
        });
    }

}
