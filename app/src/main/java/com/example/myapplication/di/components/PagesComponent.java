package com.example.myapplication.di.components;

import com.example.myapplication.ui.activities.WorkoutsActivity;
import com.example.myapplication.ui.auth.LoginActivity;
import com.example.myapplication.ui.auth.RegistrationActivity;
import com.example.myapplication.ui.fragments.main.MainFragment;
import com.example.myapplication.ui.preview.PreviewActivity;

import dagger.Subcomponent;

@Subcomponent
public interface PagesComponent {

    @Subcomponent.Factory
    interface Factory {
        PagesComponent create();
    }

    void inject(LoginActivity activity);
    void inject(RegistrationActivity activity);
    void inject(PreviewActivity activity);
    void inject(WorkoutsActivity activity);
    void inject(MainFragment fragment);
}
