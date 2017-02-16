package ru.nway.myweather.util;

import java.util.ArrayList;

import ru.nway.myweather.entity.Sys;

/**
 * Created by Klash on 13.02.2017.
 */

public final class CityList
{
    private static volatile CityList instance;
    private static ArrayList<String> citiesList;

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

    public static class CityListHolder
    {
        private static final CityList listHolder = new CityList();
    }

    public static CityList getInstance()
    {
        return CityListHolder.listHolder;
    }

    public static String[] getCitiesList()
    {
        System.out.println("ArrayList size: " + citiesList.size());
        String[] array = citiesList.toArray(new String[citiesList.size()]);
        System.out.println("Array size: " + citiesList.size());
        return array;
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
