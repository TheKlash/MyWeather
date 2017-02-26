package ru.nway.myweather.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nway.myweather.App;
import ru.nway.myweather.R;
import ru.nway.myweather.model.timezone.TimezoneData;
import ru.nway.myweather.model.weather.MainWeatherData;
import ru.nway.myweather.servicies.ConnectionService;
import ru.nway.myweather.servicies.TimezoneAPI;
import ru.nway.myweather.servicies.WeatherApi;

public class WeatherActivity extends AppCompatActivity {

    private WeatherApi weatherApi;
    private TimezoneAPI timezoneAPI;
    private MainWeatherData forecast;
    private TextView mWeatherView;
    private TextView mTimeTextView;
    private TextView mTemperatureTextView;
    private ImageButton mImageButton;
    private TextView mCityTextView;
    private String cityName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        cityName = getIntent().getStringExtra("cityName");

        mWeatherView = (TextView)findViewById(R.id.weatherTextView);
        mCityTextView = (TextView)findViewById(R.id.cityTextView);
        mTimeTextView = (TextView)findViewById(R.id.timeTextView);
        mTemperatureTextView = (TextView)findViewById(R.id.temperatureTextView);

        mImageButton = (ImageButton)findViewById(R.id.mWeatherImageButton);
        mImageButton.setOnClickListener(imageButtonOnClickListener);

        weatherApi = ConnectionService.createService(WeatherApi.class);
    }

    @Override
    protected void onResume() {

        super.onResume();
        callWeatherServer();
        Toast.makeText(this, "Weather for " + cityName + " updated", Toast.LENGTH_SHORT).show();
    }

    View.OnClickListener imageButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            callWeatherServer();
            Toast.makeText(App.getContext(), "Weather for " + cityName + " updated", Toast.LENGTH_SHORT).show();
        }
    };


    private void callTimezoneServer(double lat, double lon)
    {
        timezoneAPI = ConnectionService.createService(TimezoneAPI.class);

        timezoneAPI.getLocalTime("http://api.timezonedb.com/v2/get-time-zone", App.getTimezoneAppKey(), "json",
               "position" , lat, lon).enqueue(new Callback<TimezoneData>() {
            @Override
            public void onResponse(Call<TimezoneData> call, Response<TimezoneData> response)
            {
                TimezoneData time = response.body();
                mTimeTextView.setText(time.getFormatted());
            }

            @Override
            public void onFailure(Call<TimezoneData> call, Throwable t) {

            }
        });
    }

    private void callWeatherServer()
    {
        weatherApi.getForecast(cityName, App.getWeatherAppKey()).enqueue(new Callback<MainWeatherData>()
        {
            @Override
            public void onResponse(Call<MainWeatherData> call, Response<MainWeatherData> response)
            {
                forecast = response.body();

                mCityTextView.setText(forecast.getName() +", " + forecast.getSys().getCountry().toUpperCase());
                callTimezoneServer(forecast.getCoord().getLat(), forecast.getCoord().getLon());

                Double celsiumTemp = forecast.getMain().getTemp() - 273.1;
                String tempString;
                if (celsiumTemp >= 0)
                    tempString = String.format("+" + "%.1f" + " C", celsiumTemp);
                else
                    tempString = String.format("%.1f" + " C", celsiumTemp);

                mTemperatureTextView.setText(tempString);

                mWeatherView.setText(forecast.getWeather().get(0).getDescription());
            }

            @Override
            public void onFailure(Call<MainWeatherData> call, Throwable t) {
                Log.d("PIDOR BLYAT", "SUKA", new Exception("CONNECTION FAILURE"));
            }
        });
    }
}
