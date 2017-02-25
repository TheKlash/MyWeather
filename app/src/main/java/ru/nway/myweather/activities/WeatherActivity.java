package ru.nway.myweather.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        forecast = new MainWeatherData();

        String cityName = getIntent().getStringExtra("cityName");
        TextView mCityName = (TextView)findViewById(R.id.cityName);
        mCityName.setText(cityName);

        mWeatherView = (TextView)findViewById(R.id.weatherView);

        api = ConnectionService.createService(WeatherApi.class);

        forecast = Controller.callServer(cityName);
        try {
            Thread.sleep(1000);
        }catch (Exception e)
        {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWeatherView.setText(forecast.getSimplified().getTemp().toString());
    }
}
