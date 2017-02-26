package ru.nway.myweather;

import android.app.Application;
import android.content.Context;

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

    private static final String WEATHER_APP_KEY = "573f56d1fe48cd84a3d961d79a6dca99";
    private static final String TIMEZONE_APP_KEY = "JMTR0MBI76LH";

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }

    public static String getWeatherAppKey()
    {
        return WEATHER_APP_KEY;
    }
    public static String getTimezoneAppKey()
    {
        return TIMEZONE_APP_KEY;
    }
}
