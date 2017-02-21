package ru.nway.myweather.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nway.myweather.App;
import ru.nway.myweather.R;
import ru.nway.myweather.ententity.Weather;
import ru.nway.myweather.ententity.WeatherData;
import ru.nway.myweather.servicies.ConnectionService;
import ru.nway.myweather.servicies.WeatherApi;

public class WeatherActivity extends AppCompatActivity {

    private WeatherApi api;
    private WeatherData forecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        String cityName = getIntent().getStringExtra("cityName");
        TextView mCityName = (TextView)findViewById(R.id.cityName);
        mCityName.setText(cityName);

        api = ConnectionService.createService(WeatherApi.class);
        api.getForecast(cityName, App.getAppKey()).enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if (response.isSuccessful())
                    forecast = response.body();
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                System.out.print("CALL FAILED");
            }
        });

        TextView mWeatherView = (TextView)findViewById(R.id.weatherView);
        mWeatherView.setText(forecast.getMessage());

    }

}
