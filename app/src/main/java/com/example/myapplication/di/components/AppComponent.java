package com.example.myapplication.di.components;

import com.example.myapplication.di.modules.AppModule;
import com.example.myapplication.di.modules.DatabaseModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DatabaseModule.class})
public interface AppComponent {

    PagesComponent.Factory pagesComponent();

}
