package com.charles.weather.views.list;

import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.charles.weather.R;
import com.charles.weather.api.WeatherViewModel;
import com.charles.weather.api.model.Time;
import com.charles.weather.receiver.ConnectivityStatusReceiver;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.prefs.Prefs;


public class WeatherListView extends AppCompatActivity {
    static final String TAG = WeatherListView.class.getSimpleName();
    static final String OPENED = "opened";

    Context context;
    RelativeLayout main;
    TextView txtNoData;
    ShimmerRecyclerView listUsers;
    SwipeRefreshLayout swipeRefreshLayout;
    Snackbar snackBar;

    private List<Time> dataWeathers = new ArrayList<>();
    private WeatherAdapter adapter;
    Boolean opened;
    private boolean loadingMore = false, isShimmer;


    ConnectivityStatusReceiver connectivityStatusReceiver;
    WeatherViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        context = WeatherListView.this;
        opened = Prefs.with(this).readBoolean(OPENED);
        if (opened) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(WeatherListView.this);
            dialog.setMessage("歡迎回來");
            dialog.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                }
            });

            dialog.show();
        }

        main = findViewById(R.id.list_main);
        txtNoData = findViewById(R.id.txtNoData);
        listUsers = findViewById(R.id.list);
        swipeRefreshLayout = findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this::fetchData);
        adapter = new WeatherAdapter(context, dataWeathers);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listUsers.setLayoutManager(mLayoutManager);
        listUsers.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        listUsers.setItemAnimator(new DefaultItemAnimator());
        listUsers.setAdapter(adapter);

        registerConnectivityMonitor();
        observeData();
        fetchData();
    }

    @Override
    protected void onStop() {
        Prefs.with(this).writeBoolean(OPENED, true);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connectivityStatusReceiver != null) {
            // unregister receiver
            unregisterReceiver(connectivityStatusReceiver);
        }
    }


    private void observeData() {
        viewModel.data.observe(this, result -> {
            if (result != null) {
                txtNoData.setVisibility(View.GONE);
                if (result.size() > 0) {
                    displayWeathers(result);
                } else {
                    if (viewModel.networkStatus.getValue() != null && viewModel.networkStatus.getValue()) {
                        displayMessage(getString(R.string.error_no_data), Snackbar.LENGTH_LONG);
                        dataWeathers.clear();
                    }
                }
            } else {
                displayMessage(getString(R.string.error_network), Snackbar.LENGTH_INDEFINITE);
                if (adapter.getItemCount() < 1) {
                    txtNoData.setVisibility(View.VISIBLE);
                    dataWeathers.clear();
                }
            }
        });
        viewModel.networkStatus.observe(this, result -> {
            if (result != null) {
                if (result) {
                    if (snackBar != null) {
                        snackBar.dismiss();
                        fetchData();
                    }
                } else {
                    displayMessage(getString(R.string.error_network), Snackbar.LENGTH_INDEFINITE);
                }
            }
        });
    }

    private void registerConnectivityMonitor() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        connectivityStatusReceiver = new ConnectivityStatusReceiver();
        registerReceiver(connectivityStatusReceiver, intentFilter);
    }


    public void fetchData() {
        swipeRefreshLayout.setRefreshing(false);
        viewModel.fetchData();
    }

    public void displayWeathers(List<Time> data) {
        if (isShimmer) {
            setLoadingIndicator(false);
        }
        dataWeathers.clear();
        for (Time time : data) {
            dataWeathers.add(time);
            dataWeathers.add(null);
        }

        adapter.notifyDataSetChanged();
    }

    public void displayMessage(String message, int duration) {
        setLoadingIndicator(false);
        snackBar = Snackbar.make(main, message, duration);
        snackBar.show();
    }

    public void setLoadingIndicator(boolean isLoading) {
        if (isLoading) {
            listUsers.showShimmerAdapter();
        } else {
            listUsers.hideShimmerAdapter();
        }
        isShimmer = isLoading;
    }
}