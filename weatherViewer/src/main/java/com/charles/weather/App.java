package com.charles.weather;

import android.app.Application;

import com.charles.weather.api.AppRepository;
import com.charles.weather.data.BoxManager;

public class App extends Application {

    @Override
    public void onCreate() {
        init();
        super.onCreate();
    }

    private void init() {
        BoxManager.init(this);
        AppRepository.getInstance();
    }
}
