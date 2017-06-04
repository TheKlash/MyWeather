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
                Controller.callUpdateCity(city);
                Controller.callUpdateWeather(CityHashHolder.getStats(city));
                Controller.callUpdateCurrently(CityHashHolder.getCurrently(city));

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
                    double[] coord = CityHashHolder.getCoords(city);
                    try {
                        WeatherApi weatherApi = RetrofitService.createService(WeatherApi.class);
                        final MainWeatherData weatherData = weatherApi.getCurrentWeather(
                                App.WEATHER_KEY,
                                coord[0],
                                coord[1],
                                App.LANG,
                                App.UNITS
                        ).execute().body();

                        Log.i(App.TAG, "Идем дальше");

                        String icon = weatherData.getCurrently().getIcon();
                        System.out.print(icon);
                        Double celsiumTemp = weatherData.getCurrently().getTemperature();
                        String tempString;
                        if (celsiumTemp > 0.0)
                            tempString = String.format("+" + "%.1f" + " C", celsiumTemp);
                        else
                            tempString = String.format("%.1f" + " C", celsiumTemp);

                        String weather = weatherData.getCurrently().getSummary();

                        ArrayList<String> stats = new ArrayList<>();
                        Log.i(App.TAG, "tempString = " + tempString);
                        stats.add(tempString);
                        Log.i(App.TAG, "weather = " + weather);
                        stats.add(weather);
                        Log.i(App.TAG, "icon = " + icon);
                        stats.add(icon);
                        CityHashHolder.setStats(cityName, stats);

                        //Getting data for CurrentFragment
                        double[] current = new double[4];
                        current[0] = weatherData.getCurrently().getWindSpeed();
                        current[1] = weatherData.getCurrently().getHumidity();
                        current[2] = weatherData.getCurrently().getPressure();
                        current[3] = weatherData.getCurrently().getVisibility();
                        CityHashHolder.setCurrently(cityName, current);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }
            });

            t1.start();
            t1.join();
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
