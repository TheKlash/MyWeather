package ru.nway.myweather.servicies;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import ru.nway.myweather.App;
import ru.nway.myweather.model.weather.Currently;
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
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            try
            {
                callWeatherServer(city);

            }
            catch (InterruptedException e)
            {
               e.printStackTrace();
            }

            stopSelf(msg.arg1);
        }

        private void callWeatherServer(final String cityName) throws InterruptedException {

            Log.i(App.TAG, "Вызов callWeatherServer, cityName = " + cityName);
            Thread t1 = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    double[] coord;
                    synchronized (App.hash)
                    {
                        coord = App.hash.getCoords(city);
                    }

                    try {
                        WeatherApi weatherApi = RetrofitService.createService(WeatherApi.class);
                        MainWeatherData weatherData = weatherApi.getCurrentWeather(
                                App.WEATHER_KEY,
                                coord[0],
                                coord[1],
                                App.LANG,
                                App.UNITS
                        ).execute().body();

                        Log.i(App.TAG, "Идем дальше");

                        synchronized (App.hash)
                        {
                            App.hash.setStats(cityName, weatherData);
                        }

                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }
            });

            t1.start();
            t1.join();

            synchronized (App.hash)
            {
                Controller.callUpdateCity(city);
                Controller.callUpdate(App.hash.getStats(city));
            }
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
        Log.i(App.TAG, "Вызов onCreate");

        looper = thread.getLooper();
        mServiceHandler = new ServiceHandler(looper);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        city = intent.getStringExtra("city");
        Log.i(App.TAG, "Вызов onStartCommand, city = " + city);
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.handleMessage(msg);

        return START_STICKY;
    }
}
