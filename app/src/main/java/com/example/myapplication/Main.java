package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.di.components.AppComponent;
import com.example.myapplication.di.components.DaggerAppComponent;
import com.example.myapplication.di.modules.AppModule;
import com.example.myapplication.di.modules.DatabaseModule;

public class Main extends Application {

    AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .databaseModule(new DatabaseModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return component;
    }
}
