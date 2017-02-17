package ru.nway.myweather.util;

import java.util.ArrayList;

import ru.nway.myweather.entity.Sys;

/**
 * Created by Klash on 13.02.2017.
 */

public final class CityList
{
    private static CityList instance;
    private ArrayList<String> citiesList;

    private CityList()
    {
        citiesList = new ArrayList<>();
        citiesList.add("Moscow");
        citiesList.add("Saint Petersburg");
        citiesList.add("Tokyo");
        citiesList.add("New York");
        citiesList.add("Beijing");
        citiesList.add("Los Angeles");
    }

    private static class CityListHolder
    {
        private static final CityList listHolder = new CityList();
    }

    public static CityList getInstance()
    {
        return CityListHolder.listHolder;
    }

    public static ArrayList<String> getCitiesList()
    {
        return instance.citiesList;
    }

    public static void addCity(String city)
    {
        instance.citiesList.add(city);
    }

    public static void removeCity(String city)
    {
        instance.citiesList.remove(city);
    }
}
