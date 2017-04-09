package ru.nway.myweather.util;

import java.util.HashMap;

/**
 * Created by Klash on 05.04.2017.
 */

public class CityHashHolder
{
    private static HashMap<String, double[]> citiesHash;

    static
    {
        citiesHash = new HashMap<>();
    }

    public static HashMap<String, double[]> getCitiesHash() {
        return citiesHash;
    }

    public static void setCitiesHash(HashMap<String, double[]> citiesHash) {
        CityHashHolder.citiesHash = citiesHash;
    }

    public static double[] getCoords(String cityName)
    {
        return citiesHash.get(cityName);
    }

    public static void addCity(String cityName, double lat, double lon)
    {
        double[] coords = new double[2];
        coords[0] = lat;
        coords[1] = lon;
        citiesHash.put(cityName, coords);
    }

    public static void removeCity(String cityName)
    {
        citiesHash.remove(cityName);
    }
}
