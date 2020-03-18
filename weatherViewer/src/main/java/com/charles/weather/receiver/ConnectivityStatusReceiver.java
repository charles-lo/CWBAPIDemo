package com.charles.weather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.charles.weather.api.WeatherViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class ConnectivityStatusReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();
            WeatherViewModel viewModel = new ViewModelProvider((AppCompatActivity) context).get(WeatherViewModel.class);
            if (activeNetworkInfo != null) {
                viewModel.setNetworkStatus(true);
            } else {
                viewModel.setNetworkStatus(false);
            }
        }
    }
}
