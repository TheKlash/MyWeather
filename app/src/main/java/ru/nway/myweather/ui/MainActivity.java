package ru.nway.myweather.ui;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import java.util.ArrayList;
import ru.nway.myweather.R;
import ru.nway.myweather.model.weather.MainWeatherData;
import ru.nway.myweather.servicies.ConnectionService;
import ru.nway.myweather.util.RequestCode;

public class MainActivity extends FragmentActivity implements FragmentCallback, ControllerCallback {

    private static FragmentManager fragmentManager;
    private RecyclerFragment mRecyclerFragment;
    private NewCityFragment mNewCityFragment;
    private WeatherFragment mWeatherFragment;

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();

        mRecyclerFragment = new RecyclerFragment();
        mNewCityFragment = new NewCityFragment();
        mWeatherFragment = new WeatherFragment();

        this.setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_left, mRecyclerFragment)
                    .commit();
        }

        Controller.setActivity(this);
    }

    @Override
    public void updateWeather(ArrayList<String> result)
    {
        mWeatherFragment.updateWeather(result);
    }

    @Override
    public void updateTime(String time)
    {
        mWeatherFragment.updateTime(time);
    }

    @Override
    public void udpateCity(String name) {
        mWeatherFragment.updateCity(name);
    }

    public void fragmentCallback(int requestCode)
    {
        switch (requestCode)
        {
            case (RequestCode.CALL_NEW_CITY):
            {
                fragmentManager.beginTransaction().replace(R.id.container_left, mNewCityFragment).addToBackStack("kek").commit();
                break;
            }

            case (RequestCode.CALL_RECYCLER):
            {
                fragmentManager.beginTransaction().replace(R.id.container_left, mRecyclerFragment).commit();
                break;
            }
        }
    }

    @Override
    public void fragmentCallback(int requestCode, String cityName, double lat, double lon)
    {
        switch (requestCode)
        {
            case (RequestCode.ADD_NEW_CITY):
            {
                Controller.addCity(cityName, lat, lon);
                mRecyclerFragment.notifyAdded(cityName);
                break;
            }
        }
    }

    public void fragmentCallback(int requestCode, String city)
    {
        switch (requestCode)
        {
            case (RequestCode.CALL_WEATHER):
            {
                startService(new Intent(this, ConnectionService.class).putExtra("city", city));
                fragmentManager.beginTransaction().replace(R.id.container_left, mWeatherFragment).addToBackStack("heh").commit();
                break;
            }
            case (RequestCode.UPDATE_WEATHER):
            {
                startService(new Intent(this, ConnectionService.class).putExtra("city", city));
                break;
            }
        }


    }
}
