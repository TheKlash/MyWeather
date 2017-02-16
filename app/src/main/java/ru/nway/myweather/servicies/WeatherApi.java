package ru.nway.myweather.servicies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.nway.myweather.entity.Forecast;

/**
 * Created by Klash on 14.02.2017.
 */

public interface WeatherApi
{
    @GET("data/2.5/weather?q={cityname}")
    public Call<Forecast> getForecast(@Path("cityname") String cityname);
}
