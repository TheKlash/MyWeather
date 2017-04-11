package ru.nway.myweather.servicies;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import ru.nway.myweather.model.weather.MainWeatherData;


/**
 * Created by Klash on 14.02.2017.
 */

interface WeatherApi
{
    @GET("{key}/{lat},{lon}")
    Call<MainWeatherData> getCurrentWeather(@Path("key") String key,
                                            @Path("lat") double lat,
                                            @Path("lon") double lon);
}
