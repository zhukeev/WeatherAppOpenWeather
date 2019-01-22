package com.example.weatherapp.Retrofit;

import com.example.weatherapp.model.WeatherForecastResult;
import com.example.weatherapp.model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {
    @GET("weather")
    Observable<WeatherResult> getWeatherByLatLng(@Query("lat") String lat,
                                                 @Query("lon") String lng,
                                                 @Query("appid") String appId,
                                                 @Query("units") String unit);

    @GET("forecast")
    Observable<WeatherForecastResult> getForecastWeatherByLatLng(@Query("lat") String lat,
                                                         @Query("lon") String lng,
                                                         @Query("appid") String appId,
                                                         @Query("units") String unit);
}
