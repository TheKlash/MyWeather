package ru.nway.myweather.ui;

import java.util.ArrayList;

import ru.nway.myweather.model.weather.Datum_;
import ru.nway.myweather.model.weather.Datum__;

/**
 * Created by Klash on 10.03.2017.
 */

public interface ControllerCallback
{
    void updateWeather(ArrayList<String> result);
    void updateTime(String time);
    void udpateCity(String name);
    void updateCurrently(double[] currently);
    //void updateHourly(ArrayList<Datum_> hourly);
    //void updateDaily(ArrayList<Datum__> daily);
}
