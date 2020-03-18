package com.charles.weather.views.detail;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.charles.weather.R;
import com.charles.weather.api.model.Time;
import com.google.android.material.snackbar.Snackbar;

/**
 *
 */

public class DetailView extends AppCompatActivity {

    Context context;
    LinearLayout main;
    TextView txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        context = DetailView.this;

        txtName = findViewById(R.id.info_detail);


        try {
            displayData((Time) getIntent().getSerializableExtra("data"));
        } catch (Exception e) {
            displayMessage(getString(R.string.error_detail));
        }
    }


    public void displayMessage(String message) {
        Snackbar.make(main, message, Snackbar.LENGTH_LONG).show();
    }

    public void displayData(Time data) {
        txtName.setText(String.format(context.getString(R.string.info), data.getStartTime(), data.getEndTime(), data.getParameter().getParameterName() + data.getParameter().getParameterUnit()));
    }
}