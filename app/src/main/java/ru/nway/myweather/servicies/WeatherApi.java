package ru.nway.myweather.servicies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.nway.myweather.model.weather.MainWeatherData;


/**
 * Created by Klash on 14.02.2017.
 */

public interface WeatherApi
{
    @GET("data/2.5/weather")
    public Call<MainWeatherData> getForecast(@Query("q") String cityName, @Query("appid") String appKey);
}
