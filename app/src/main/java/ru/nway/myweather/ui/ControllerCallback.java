package ru.nway.myweather.ui;

import java.util.ArrayList;

import ru.nway.myweather.model.weather.Datum_;
import ru.nway.myweather.model.weather.Datum__;
import ru.nway.myweather.model.weather.MainWeatherData;

/**
 * Created by Klash on 10.03.2017.
 */

public interface ControllerCallback
{
    void udpateCity(String name);
    void update(MainWeatherData data);
}
