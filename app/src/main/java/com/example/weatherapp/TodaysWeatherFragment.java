package com.example.weatherapp;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.API.WeatherAPI;
import com.example.weatherapp.Retrofit.IOpenWeatherMap;
import com.example.weatherapp.Retrofit.RetrofitClient;
import com.example.weatherapp.model.WeatherResult;
import com.squareup.picasso.Picasso;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodaysWeatherFragment extends Fragment {

    ImageView img_weather;
    TextView txt_city_name,txt_wind,txt_pressure,txt_humidity,txt_sunrise,txt_sunset,txt_temperature,txt_descr,txt_date_time,txt_Geo_coord;
    LinearLayout weather_panel;
    ProgressBar loading;

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;


    static TodaysWeatherFragment instance;

    public static TodaysWeatherFragment getInstance() {
        if (instance == null) {
            instance = new TodaysWeatherFragment();
        }

        return instance;
    }

    public TodaysWeatherFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_todays_weather, container, false);

        img_weather = itemView.findViewById(R.id.img_weather);
        txt_city_name = itemView.findViewById(R.id.txt_city_name);
        txt_date_time = itemView.findViewById(R.id.txt_date_time);
        txt_descr = itemView.findViewById(R.id.txt_description);
        txt_Geo_coord = itemView.findViewById(R.id.txt_geo_coord);
        txt_humidity = itemView.findViewById(R.id.txt_humidity);
        txt_pressure = itemView.findViewById(R.id.txt_pressure);
        txt_sunrise = itemView.findViewById(R.id.txt_sunrise);
        txt_sunset = itemView.findViewById(R.id.txt_sunset);
        txt_temperature = itemView.findViewById(R.id.txt_temperature);
        txt_wind = itemView.findViewById(R.id.txt_wind);

        weather_panel = itemView.findViewById(R.id.weather_panel);
        loading = itemView.findViewById(R.id.progress);

        getWeatherInformation();

        return itemView;
    }

    private void getWeatherInformation() {
        compositeDisposable.add(mService.getWeatherByLatLng(String.valueOf(WeatherAPI.current_location.getLatitude()),
                String.valueOf(WeatherAPI.current_location.getLongitude()),
                WeatherAPI.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @Override
                    public void accept(WeatherResult weatherResult) throws Exception {

                        //GETTING IMAGES

                        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/")
                                .append(weatherResult.getWeather().get(0).getIcon())
                        .append(".png").toString()).into(img_weather);

                        // DATA

                        txt_city_name.setText(weatherResult.getName());
                        txt_wind.setText(new StringBuilder(String.valueOf(weatherResult.getWind().getSpeed()))
                                .append(" m/s ")
                                .append(weatherResult.getWind().getDeg()));
                        txt_descr.setText(new StringBuilder("Weather in ")
                        .append(weatherResult.getName().toString()));
                        txt_date_time.setText(WeatherAPI.converetUnixToDate(weatherResult.getDt()));
                        txt_temperature.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp()))
                                .append(" °C").toString());
                        txt_pressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure()))
                                .append(" hpa").toString());
                        txt_humidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity()))
                                .append(" %").toString());
                        txt_sunset.setText(WeatherAPI.converetUnixToTime(weatherResult.getSys().getSunset()));
                        txt_sunrise.setText(WeatherAPI.converetUnixToTime(weatherResult.getSys().getSunrise()));
                        txt_Geo_coord.setText(new StringBuilder(weatherResult.getCoord().toString()));

                        //LAYOUT

                        weather_panel.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);




                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity()," "+ throwable.getMessage()  , Toast.LENGTH_SHORT).show();
                    }
                })

        );
    }

    @Override
    public void onStop() {
        super.onStop();

        compositeDisposable.clear();
    }
}
