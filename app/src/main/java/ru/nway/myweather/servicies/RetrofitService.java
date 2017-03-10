package ru.nway.myweather.servicies;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.nway.myweather.model.weather.MainWeatherDataDeserializer;
import ru.nway.myweather.model.weather.MainWeatherData;

/**
 * Created by Klash on 15.02.2017.
 */

class RetrofitService
{
    private static final String BASE_URL = "http://api.openweathermap.org/";

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder().addInterceptor(logging);

    private static Retrofit retrofit = builder.client(httpClient.build()).build();

    public static <S> S createService(Class<S> serviceClass)
    {
        return retrofit.create(serviceClass);
    }

    private static GsonConverterFactory buildGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        // Adding custom deserializers
        gsonBuilder.registerTypeAdapter(MainWeatherData.class, new MainWeatherDataDeserializer());
        Gson myGson = gsonBuilder.create();

        return GsonConverterFactory.create(myGson);
    }


}
