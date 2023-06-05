package com.example.myapplication.di.components;

import com.example.myapplication.ui.activities.WorkoutsActivity;
import com.example.myapplication.ui.auth.LoginActivity;
import com.example.myapplication.ui.auth.RegistrationActivity;
import com.example.myapplication.ui.preview.PreviewActivity;
import com.example.myapplication.di.modules.AppModule;
import com.example.myapplication.di.modules.DatabaseModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DatabaseModule.class})
public interface AppComponent {
    void inject(PreviewActivity activity);
    void inject(RegistrationActivity activity);
    void inject(LoginActivity activity);
    void inject(WorkoutsActivity activity);
}
