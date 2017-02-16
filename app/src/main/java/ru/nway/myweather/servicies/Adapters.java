package ru.nway.myweather.servicies;

/**
 * Created by Klash on 15.02.2017.
 */

public class Adapters
{
    public static final WeatherApi WeatherApiAdpater = ConnectionService.createService(WeatherApi.class);
}
