package ru.nway.myweather.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ImageView mImage;
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

        mImage = (ImageView) findViewById(R.id.imageView);
        mImage.setOnClickListener(imageButtonOnClickListener);

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


    private void callTimezoneServer(double lat, double lon, int sunrise, int susnset)
    {
        timezoneAPI = ConnectionService.createService(TimezoneAPI.class);

        timezoneAPI.getLocalTime("http://api.timezonedb.com/v2/get-time-zone", App.getTimezoneAppKey(), "json",
               "position" , lat, lon).enqueue(new Callback<TimezoneData>() {
            @Override
            public void onResponse(Call<TimezoneData> call, Response<TimezoneData> response)
            {
                TimezoneData time = response.body();
                mTimeTextView.setText(time.getFormatted());
                //TODO: реализовать смену дизайна по времени суток
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

                String icon = forecast.getWeather().get(0).getIcon();

                switch(icon)
                {
                    case "01d":
                        mImage.setImageResource(R.drawable.sunny);
                        break;
                    case "02d":
                        mImage.setImageResource(R.drawable.partly_cloudy_day);
                        break;
                    case "03d":
                        mImage.setImageResource(R.drawable.cloudy);
                        break;
                    case "03n":
                        mImage.setImageResource(R.drawable.cloudy);
                        break;
                    case "04d":
                        mImage.setImageResource(R.drawable.overcast);
                        break;
                    case "04n":
                        mImage.setImageResource(R.drawable.overcast);
                        break;
                    case "09d":
                        mImage.setImageResource(R.drawable.heavy_rain);
                        break;
                    case "09n":
                        mImage.setImageResource(R.drawable.heavy_rain);
                        break;
                    case "10d":
                        mImage.setImageResource(R.drawable.heavy_rain_swrs_day);
                        break;
                    case "10n":
                        mImage.setImageResource(R.drawable.heavy_rain_rwrs_night);
                        break;
                    case "11d":
                        mImage.setImageResource(R.drawable.cloud_rain_thunder);
                        break;
                    case "11n":
                        mImage.setImageResource(R.drawable.cloud_rain_thunder);
                        break;
                    case "13d":
                        mImage.setImageResource(R.drawable.heavy_snow_swrs_day);
                        break;
                    case "13n":
                        mImage.setImageResource(R.drawable.heavy_snow_swr_night);
                        break;
                    case "50d":
                        mImage.setImageResource(R.drawable.fog);
                        break;
                    case "50n":
                        mImage.setImageResource(R.drawable.fog);
                        break;
                }

                mCityTextView.setText(forecast.getName() +", " + forecast.getSys().getCountry().toUpperCase());
                callTimezoneServer(forecast.getCoord().getLat(), forecast.getCoord().getLon(),
                        forecast.getSys().getSunrise(), forecast.getSys().getSunset());

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
