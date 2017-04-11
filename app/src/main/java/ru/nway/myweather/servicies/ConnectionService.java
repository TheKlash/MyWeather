package ru.nway.myweather.servicies;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Response;
import ru.nway.myweather.App;
import ru.nway.myweather.model.timezone.TimezoneData;
import ru.nway.myweather.model.weather.MainWeatherData;
import ru.nway.myweather.ui.Controller;
import ru.nway.myweather.util.CityHashHolder;

public class ConnectionService extends Service
{
    private Looper looper;
    private ServiceHandler mServiceHandler;
    private String city;

    private final class ServiceHandler extends Handler
    {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            callWeatherServer(city);

            stopSelf(msg.arg1);
        }

        private void callWeatherServer(final String cityName)
        {
            WeatherApi weatherApi = RetrofitService.createService(WeatherApi.class);
            double[] coord = CityHashHolder.getCoords(city);
            weatherApi.getCurrentWeather(
                    App.WEATHER_KEY,
                    coord[0],
                    coord[1]).enqueue(new retrofit2.Callback<MainWeatherData>()
            {
                @Override
                public void onResponse(Call<MainWeatherData> call, Response<MainWeatherData> response) {
                    try {
                        Thread.sleep(1300);
                    }
                    catch ( InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    MainWeatherData weatherData = response.body();
                    String icon = weatherData.getCurrently().getIcon();

                    Double celsiumTemp = weatherData.getCurrently().getTemperature();
                    String tempString;
                    if (celsiumTemp > 0.0)
                        tempString = String.format("+" + "%.1f" + " C", celsiumTemp);
                    else
                        tempString = String.format("%.1f" + " C", celsiumTemp);

                    String weather = weatherData.getCurrently().getSummary();

                    /*
                    double lat = weatherData.getLatitude();
                    double lon = weatherData.getLongitude();
                    callTimezoneServer(lat, lon);
                    */

                    ArrayList<String> callResults = new ArrayList<>();
                    Controller.callUpdateCity(cityName);
                    callResults.add(tempString);
                    callResults.add(weather);
                    callResults.add(icon);
                    Controller.callUpdateWeather(callResults);
                }

                @Override
                public void onFailure(Call<MainWeatherData> call, Throwable t) {

                }
            });
        }

        private void callTimezoneServer(double lat, double lon)
        {
            TimezoneAPI timezoneAPI = RetrofitService.createService(TimezoneAPI.class);
            timezoneAPI.getLocalTime(App.TIMEZONE_URL, App.TIMEZONE_KEY, "json",
                    "position", lat, lon).enqueue(new retrofit2.Callback<TimezoneData>() {
                @Override
                public void onResponse(Call<TimezoneData> call, Response<TimezoneData> response) {
                    String time = response.body().getFormatted();
                    Controller.callTimeUpdate(time);
                }

                @Override
                public void onFailure(Call<TimezoneData> call, Throwable t) {

                }
            });
        }

    }

    public ConnectionService()
    {
        super();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("ServiceStartArguments");
        thread.start();

        looper = thread.getLooper();
        mServiceHandler = new ServiceHandler(looper);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        city = intent.getStringExtra("city");
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.handleMessage(msg);

        return START_NOT_STICKY;
    }
}
