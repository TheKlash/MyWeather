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

    private static final String APP_KEY = "573f56d1fe48cd84a3d961d79a6dca99";

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }

    public static String getAppKey()
    {
        return APP_KEY;
    }
}
