package ru.nway.myweather.util;

import java.util.ArrayList;

/**
 * Created by Klash on 13.02.2017.
 */

public final class CityList
{
    private static volatile CityList instance;
    private static ArrayList<String> citiesList;

    static
    {
        citiesList = new ArrayList<>();
        citiesList.add("Moscow");
        citiesList.add("Saint Petersburg");
        citiesList.add("Tokyo");
        citiesList.add("New York");
        citiesList.add("Beijing");
        citiesList.add("Los Angeles");
    }

    private CityList()
    {

    }

    public static class CityListHolder
    {
        private static final CityList listHolder = new CityList();
    }

    public static CityList getInstance()
    {
        return CityListHolder.listHolder;
    }

    public static ArrayList<String> getCitiesList()
    {
        return citiesList;
    }

    public void addCity(String city)
    {
        citiesList.add(city);
    }

    public void removeCity(String city)
    {
        citiesList.remove(city);
    }
}
