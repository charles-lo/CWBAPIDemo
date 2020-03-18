package com.charles.weather.api;

import com.charles.weather.api.model.Time;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class WeatherViewModel extends ViewModel {

    public LiveData<List<Time>> data = AppRepository.getInstance().getData();
    public LiveData<Boolean> networkStatus = AppRepository.getInstance().getNetworkStatus();

    public void fetchData() {
        AppRepository.getInstance().fetchData();
    }

    public void setNetworkStatus(Boolean status) {
        AppRepository.getInstance().setNetworkStatus(status);
    }
}
