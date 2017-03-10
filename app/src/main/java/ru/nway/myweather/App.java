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

    public static final String WEATHER_KEY = "573f56d1fe48cd84a3d961d79a6dca99";
    public static final String TIMEZONE_KEY = "JMTR0MBI76LH";
    public static final String TIMEZONE_URL = "http://api.timezonedb.com/v2/get-time-zone";

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
