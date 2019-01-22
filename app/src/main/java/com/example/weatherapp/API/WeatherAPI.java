package com.example.weatherapp.API;

import android.location.Location;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherAPI {

    public static final String APP_ID ="acb00aaf671ad0827850bd40aad3e396";
    public static Location current_location = null;

    public static String converetUnixToDate(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd EEE MM yyyy");
        String formatted = simpleDateFormat.format(date);
        return formatted;
    }

    public static String converetUnixToTime(long sunset) {
        Date date = new Date(sunset*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String formatted = simpleDateFormat.format(date);
        return formatted;
    }
}
