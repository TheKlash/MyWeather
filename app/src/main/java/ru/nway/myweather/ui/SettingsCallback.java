package ru.nway.myweather.ui;

import android.content.SharedPreferences;

/**
 * Created by Klash on 17.06.2017.
 */

public interface SettingsCallback
{
    public void settingsCallback(SharedPreferences sharedPreferences, String key);
}
