package com.example.weatherapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.API.WeatherAPI;
import com.example.weatherapp.R;
import com.example.weatherapp.model.WeatherForecastResult;
import com.squareup.picasso.Picasso;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder> {

    Context context;
    WeatherForecastResult weatherForecastResult;

    public WeatherForecastAdapter(Context context, WeatherForecastResult weatherForecastResult) {
        this.context = context;
        this.weatherForecastResult = weatherForecastResult;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_date_time,txt_description,txt_temperature;
        ImageView img_weather;
        public MyViewHolder(View itemView) {
            super(itemView);

            img_weather = itemView.findViewById(R.id.img_weather);
            txt_date_time = itemView.findViewById(R.id.txt_date);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_temperature = itemView.findViewById(R.id.txt_temperature);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.weather_forecast_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                .append(weatherForecastResult.getList().get(position).getWeather().get(0).getIcon())
                .append(".png").toString()).into(holder.img_weather);

        holder.txt_date_time.setText(new StringBuilder(WeatherAPI.converetUnixToDate(weatherForecastResult
        .getList().get(position).getDt())));
        holder.txt_description.setText(new StringBuilder(weatherForecastResult.getList().get(position)
                              .getWeather().get(0).getDescription()));
        holder.txt_temperature.setText(new StringBuilder(weatherForecastResult.getList().get(position)
                              .getMain().getTemp().toString()).append(" °C"));

    }

    @Override
    public int getItemCount() {
        return weatherForecastResult.getList().size();
    }
}
