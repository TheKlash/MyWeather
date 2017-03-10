package ru.nway.myweather.ui;

/**
 * Created by Klash on 10.03.2017.
 */
//This class will hold fragments' instances
//Probably needs refactoring

public class FragmentsHolder
{
    private static NewCityFragment newCityInstance;
    private static RecyclerFragment recyclerInstance;
    private static WeatherFragment weatherInstance;

    static
    {
        newCityInstance = NewCityFragment.newInstance(0);
        recyclerInstance = RecyclerFragment.newInstance(0);
        weatherInstance = WeatherFragment.newInstance(0);
    }

    public static NewCityFragment getNewCityInstance() {
        return newCityInstance;
    }

    public static RecyclerFragment getRecyclerInstance() {
        return recyclerInstance;
    }

    public static WeatherFragment getWeatherInstance() {
        return weatherInstance;
    }
}
