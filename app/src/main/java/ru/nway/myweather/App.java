package ru.nway.myweather;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;

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
    public static String TAG = "ЕБУЧИЙ БАГ";
    //preferences
    public static String LANG = "en";
    public static String UNITS = "si";
    public static String TEMP_POSTFX = "\u00b0C";
    public static String DATE_FORMAT_LONG = "EEEE, MMMM d";
    public static String DATE_FORMAT_SHORT = "E, MMMM d";
    public static String TIME_FORMAT = "h:mm a";
    //SharedPreferences
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public static final CityHashHolder hash = new CityHashHolder();

    @Override
    public void onCreate() {
        instance = this;
        preferences = instance.getApplicationContext().getSharedPreferences("AppPreferences", MODE_PRIVATE);
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

    private SharedPreferences.OnSharedPreferenceChangeListener listener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
                {
                    String value;
                    switch (key)
                    {
                        case "units":
                        {
                            value = sharedPreferences.getString(key, "si");
                            if (value.equals("us"))
                                TEMP_POSTFX = "\u00b0F";
                            else
                                TEMP_POSTFX = "\u00b0C";
                            UNITS = value;
                            break;
                        }
                        case "time_format":
                        {
                            value = sharedPreferences.getString(key, "h:mm a");
                            TIME_FORMAT = value;
                            break;
                        }
                        case "date_format":
                        {
                            value = sharedPreferences.getString(key, "MMMM d");
                            DATE_FORMAT_LONG = "EEEE " + value;
                            DATE_FORMAT_SHORT = "E " +value;
                            break;
                        }
                    }
                }
            };
}
