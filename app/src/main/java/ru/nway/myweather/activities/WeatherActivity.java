package ru.nway.myweather.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nway.myweather.App;
import ru.nway.myweather.R;
import ru.nway.myweather.entity.MainWeatherData;
import ru.nway.myweather.servicies.ConnectionService;
import ru.nway.myweather.servicies.WeatherApi;

public class WeatherActivity extends AppCompatActivity {

    private WeatherApi api;
    private MainWeatherData forecast;
    private TextView mWeatherView;
    private String cityName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        cityName = getIntent().getStringExtra("cityName");
        TextView mCityName = (TextView)findViewById(R.id.cityName);
        mCityName.setText(cityName);

        mWeatherView = (TextView)findViewById(R.id.weatherView);

        api = ConnectionService.createService(WeatherApi.class);


    }

    @Override
    protected void onResume() {

        super.onResume();

        api.getForecast(cityName, App.getAppKey()).enqueue(new Callback<MainWeatherData>() {
            @Override
            public void onResponse(Call<MainWeatherData> call, Response<MainWeatherData> response)
            {
                forecast = response.body();
                Double celsiumTemp = forecast.getMain().getTemp() - 273.1;
                String tempString = "Getting weather";
                if (celsiumTemp >= 0)
                    tempString = String.format("+" + "%.1f" + " C", celsiumTemp);
                else
                    tempString = String.format("%.1f" + " C", celsiumTemp);
                mWeatherView.setText(tempString);
            }

            @Override
            public void onFailure(Call<MainWeatherData> call, Throwable t) {
                Log.d("PIDOR BLYAT", "SUKA", new Exception("CONNECTION FAILURE"));
            }
        });

    }
}
