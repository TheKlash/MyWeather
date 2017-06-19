package ru.nway.myweather;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import ru.nway.myweather.ui.SettingsCallback;
import ru.nway.myweather.util.CityHashHolder;

/**
 * Created by Klash on 17.02.2017.
 */

public class App extends Application implements SettingsCallback
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
    public static String TAG = "ЕБУЧИЙ БАГ";
    //preferences
    public static String LANG = "en";
    public static String UNITS = "si";
    public static String TEMP_POSTFX;
    public static String DATE_FORMAT_LONG;
    public static String DATE_FORMAT_SHORT;
    public static String TIME_FORMAT;
    //SharedPreferences
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    //Units
    public static String WIND_UNIT;
    public static String PRESSURE_UNIT;

    public static final CityHashHolder hash = new CityHashHolder();

    @Override
    public void onCreate() {
        instance = this;
        preferences = instance.getApplicationContext().getSharedPreferences("AppPreferences", MODE_PRIVATE);
        String[] keys = {"units", "time_format", "date_format"};
        for (String key:keys)
        {
            varValuePicker(this.preferences, key);
        }

        super.onCreate();
    }

    public SharedPreferences getPreferences()
    {
        return this.preferences;
    }

    public SharedPreferences.Editor getEditor()
    {
        return this.editor;
    }

    @Override
    public void settingsCallback(SharedPreferences sharedPreferences, String key)
    {
        String value = varValuePicker(sharedPreferences, key);
        preferences.edit().putString(key, value).apply();
    }

    private String varValuePicker(SharedPreferences preferences, String key)
    {
        String value = "";
        switch (key)
        {
            case "units":
            {
                value = preferences.getString(key, "si");
                if (value.equals("us"))
                {
                    TEMP_POSTFX = "\u00b0F";
                    WIND_UNIT = "Mph";
                    PRESSURE_UNIT = "mb";
                }
                else
                {
                    TEMP_POSTFX = "\u00b0C";
                    WIND_UNIT = "mps";
                    PRESSURE_UNIT = "hPa";
                }
                UNITS = value;
                break;
            }
            case "time_format":
            {
                value = preferences.getString(key, "h:mm a");
                TIME_FORMAT = value;
                break;
            }
            case "date_format":
            {
                value = preferences.getString(key, "MMMM d");
                DATE_FORMAT_LONG = "EEEE " + value;
                DATE_FORMAT_SHORT = "E " +value;
                break;
            }
        }

        return value;
    }
}
