package ru.nway.myweather.ui;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nway.myweather.App;
import ru.nway.myweather.R;
import ru.nway.myweather.model.weather.MainWeatherData;
import ru.nway.myweather.servicies.DataService;
import ru.nway.myweather.servicies.RetrofitService;
import ru.nway.myweather.servicies.WeatherApi;
import ru.nway.myweather.util.RequestCode;

public class MainActivity extends FragmentActivity implements FragmentCallback {

    private static FragmentManager fragmentManager;
    private static FragmentTransaction fragmentTransaction;
    private RecyclerFragment mRecyclerFragment;
    private NewCityFragment mNewCityFragment;
    private WeatherFragment mWeatherFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        
        mRecyclerFragment = new RecyclerFragment();
        mNewCityFragment = new NewCityFragment();
        mWeatherFragment = new WeatherFragment();
        
        fragmentTransaction.add(R.id.container_left, mRecyclerFragment);
        fragmentTransaction.add(R.id.container_right, mWeatherFragment);
        fragmentTransaction.commit();
    }

    public void fragmentCallback(int requestCode)
    {
        switch (requestCode)
        {
            case (RequestCode.CALL_NEW_CITY):
            {
                fragmentTransaction.replace(R.id.container_left, mNewCityFragment);
                break;
            }

            case (RequestCode.CALL_RECYCLER):
            {
                fragmentTransaction.replace(R.id.container_left, mRecyclerFragment);
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
                //MainActivity.callServer(city);
                WeatherFragment searchWeatherFragment = (WeatherFragment)fragmentManager
                        .findFragmentById(R.id.container_right); //Проверяем, есть ли на лейауте правый фрагмент
                if (searchWeatherFragment == null)
                {
                    fragmentTransaction.replace(R.id.container_left, mWeatherFragment);
                }
                break;
            }
            case (RequestCode.UPDATE_WEATHER):
            {
                //MainActivity.callServer(city);
                break;
            }
        }

    }

    private static void callServer(String city)
    {
        //TODO
    }



}
