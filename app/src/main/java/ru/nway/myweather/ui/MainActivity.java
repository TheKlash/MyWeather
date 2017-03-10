package ru.nway.myweather.ui;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import ru.nway.myweather.App;
import ru.nway.myweather.R;
import ru.nway.myweather.servicies.ConnectionService;
import ru.nway.myweather.util.RequestCode;

public class MainActivity extends FragmentActivity implements FragmentCallback, ConnectionCallback {

    private static FragmentManager fragmentManager;
    private RecyclerFragment mRecyclerFragment;
    private NewCityFragment mNewCityFragment;
    private WeatherFragment mWeatherFragment;
    private static ConnectionService connectionService;
    private static ArrayList<String> result;
    private int counter;

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(App.getContext(), "MainActivity рестартанул!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        result = new ArrayList<>();
        fragmentManager = getSupportFragmentManager();
        connectionService = new ConnectionService();

        mRecyclerFragment = FragmentsHolder.getRecyclerInstance();
        mNewCityFragment = FragmentsHolder.getNewCityInstance();
        mWeatherFragment = FragmentsHolder.getWeatherInstance();

        this.setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_left, mRecyclerFragment)
                    .commit();
        }
    }

    @Override
    protected void onStop() {

    }




    @Override
    public void connectionCallback(ArrayList<String> result) {
        mWeatherFragment.update(result);
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

    public void fragmentCallback(int requestCode, String city)
    {


        switch (requestCode)
        {
            case (RequestCode.CALL_WEATHER):
            {
                counter++;
                //MainActivity.callServer(city);
                /*WeatherFragment searchWeatherFragment = (WeatherFragment)fragmentManager
                        .findFragmentById(R.id.container_right); //Проверяем, есть ли на лейауте правый фрагмент
                if (searchWeatherFragment == null)
                {
                    fragmentManager.beginTransaction().replace(R.id.container_left, mWeatherFragment).commit();
                }*/
                Toast.makeText(App.getContext(), "Calls counter: " + counter, Toast.LENGTH_SHORT).show();
                startService(new Intent(this, ConnectionService.class).putExtra("city", city));
                fragmentManager.beginTransaction().replace(R.id.container_left, mWeatherFragment).addToBackStack("heh").commit();
                break;
            }
            case (RequestCode.UPDATE_WEATHER):
            {
                startService(new Intent(this, ConnectionService.class).putExtra("city", city));
                break;
            }
            case (RequestCode.ADD_NEW_CITY):
            {
                Controller.addCity(city);
                break;
            }
        }

    }
}
