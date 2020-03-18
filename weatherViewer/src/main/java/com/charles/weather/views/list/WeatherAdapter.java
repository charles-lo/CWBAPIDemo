package com.charles.weather.views.list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charles.weather.R;
import com.charles.weather.api.model.Time;
import com.charles.weather.views.detail.DetailView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 *
 */

public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_IMAGE = 1;
    private Context context;
    private List<Time> data;

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        LinearLayout row;
        TextView txtInfo;

        WeatherViewHolder(View view) {
            super(view);
            row = view.findViewById(R.id.item_row);

            txtInfo = view.findViewById(R.id.info);
            row.setOnClickListener(view1 -> {
                Intent detail = new Intent(context, DetailView.class);
                detail.putExtra("data", data.get(getAdapterPosition()));
                context.startActivity(detail);
            });
        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    WeatherAdapter(Context context, List<Time> weathers) {
        this.context = context;
        this.data = weathers;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_item, parent, false);
            return new WeatherViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
            return new ImageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof WeatherViewHolder) {
            Time time = data.get(position);

            WeatherViewHolder holder = (WeatherViewHolder) viewHolder;
            holder.txtInfo.setText(String.format(context.getString(R.string.info), time.getStartTime(), time.getEndTime(), time.getParameter().getParameterName() + time.getParameter().getParameterUnit()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position) == null ? VIEW_TYPE_IMAGE : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}