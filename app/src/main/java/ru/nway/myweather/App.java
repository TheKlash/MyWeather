package ru.nway.myweather;

import android.app.Application;
import android.content.Context;

import ru.nway.myweather.util.CityHashHolder;

/**
 * Created by Klash on 17.02.2017.
 */

public class App extends Application
{
    private static App instance;

    public static App getInstance()
    {
        return instance;
    }

    public static Context getContext()
    {
        return instance.getApplicationContext();
    }

    public static final String WEATHER_KEY = "542f73e4e98d7ecbf4899a3996b3e069";
    public static final String TIMEZONE_KEY = "JMTR0MBI76LH";
    public static final String TIMEZONE_URL = "http://api.timezonedb.com/v2/get-time-zone";
    public static final String LANG = "en";
    public static final String UNITS = "si";
    public static final String TAG = "ЕБУЧИЙ БАГ";

    public static final CityHashHolder hash = new CityHashHolder();

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
