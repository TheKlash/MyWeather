package ru.nway.myweather.servicies;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Klash on 15.02.2017.
 */

public class ConnectionService
{
    private static final String BASE_URL = "http://api.openweathermap.org/";
    private static Retrofit retrofit;

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL).
            addConverterFactory(GsonConverterFactory.create());

    private static OkHttpClient.Builder client = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass)
    {
        return retrofit.create(serviceClass);
    }
}
