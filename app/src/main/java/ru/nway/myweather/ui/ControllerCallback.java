package ru.nway.myweather.ui;

import java.util.ArrayList;

/**
 * Created by Klash on 10.03.2017.
 */

public interface ControllerCallback
{
    void updateWeather(ArrayList<String> result);
    void updateTime(String time);
}
