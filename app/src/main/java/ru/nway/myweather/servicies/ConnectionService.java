package ru.nway.myweather.servicies;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import ru.nway.myweather.App;
import ru.nway.myweather.model.timezone.TimezoneData;
import ru.nway.myweather.model.weather.MainWeatherData;
import ru.nway.myweather.retrofit.RetrofitService;
import ru.nway.myweather.ui.Controller;

/**
 * Created by Klash on 10.03.2017.
 */

public class ConnectionService extends Service
{
    private static String time = "unable to get time";
    private Looper looper;
    private ServiceHandler mServiceHandler;
    private String city;
    private static String STATUS;
    private static int serviceCounter;
    private static int callCounter;

    private final class ServiceHandler extends Handler
    {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (STATUS.equals("READY"))
            {
                STATUS = "CALLING";
                callWeatherServer(city);
            }
            stopSelf(msg.arg1);
        }

        private void callWeatherServer(String city)
        {
            WeatherApi weatherApi = RetrofitService.createService(WeatherApi.class);
            weatherApi.getCurrentWeather(city, App.WEATHER_KEY).enqueue(new retrofit2.Callback<MainWeatherData>()
            {
                @Override
                public void onResponse(Call<MainWeatherData> call, Response<MainWeatherData> response) {
                    if (STATUS.equals("CALLING"))
                        call.cancel();

                    callCounter++;
                    Toast.makeText(App.getContext(), "Calling. Response counter: " + callCounter, Toast.LENGTH_LONG).show();

                    MainWeatherData weatherData = response.body();
                    String icon = weatherData.getWeather().get(0).getIcon();
                    String cityString = weatherData.getName() + ", " + weatherData.getSys().getCountry().toUpperCase();

                    Double celsiumTemp = weatherData.getMain().getTemp() - 273.1;
                    String tempString;
                    if (celsiumTemp > 0.0)
                        tempString = String.format("+" + "%.1f" + " C", celsiumTemp);
                    else
                        tempString = String.format("%.1f" + " C", celsiumTemp);

                    String weather = weatherData.getWeather().get(0).getDescription();


                    double lat = weatherData.getCoord().getLat();
                    double lon = weatherData.getCoord().getLon();
                    callTimezoneServer(lat, lon);

                    ArrayList<String> callResults = new ArrayList<>();
                    callResults.add(cityString);
                    callResults.add(tempString);
                    callResults.add(weather);
                    callResults.add(icon);
                    callResults.add(time);
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
                    time = response.body().getFormatted();
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
        STATUS = "READY";
        HandlerThread thread = new HandlerThread("ServiceStartArguments");
        thread.start();
        serviceCounter++;

        looper = thread.getLooper();
        mServiceHandler = new ServiceHandler(looper);

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done, service counter: " + serviceCounter, Toast.LENGTH_SHORT).show();
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
