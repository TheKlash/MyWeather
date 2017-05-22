package ru.nway.myweather.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ru.nway.myweather.App;

import static com.google.android.gms.internal.zzt.TAG;

/**
 * Created by Klash on 05.04.2017.
 */

public class CityHashHolder
{
    private static HashMap<String, double[]> citiesHash;
    private static HashMap<String, ArrayList<String>> statsHash;

    static
    {
        citiesHash = new HashMap<>();
        statsHash = new HashMap<>();
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
        Log.i(TAG, "Added new city to hash: " + cityName +
                " lat: " + lat +
                " lon: " + lon);
    }

    public static void removeCity(String cityName)
    {
        citiesHash.remove(cityName);
    }

    public static ArrayList<String> getStrings()
    {
        ArrayList list = new ArrayList();
        for (Map.Entry<String, double[]> entry: citiesHash.entrySet())
        {
            list.add(entry.getKey() + "&&" + entry.getValue()[0] + "&&" + entry.getValue()[1]);
        }

        return list;
    }

    public static void setStats(String cityName, ArrayList<String> stats)
    {
        Log.i(App.TAG, "Вызов setStats, stats[0] = " + stats.get(0) + " cityName = " + cityName);
        Set<String> citiesSet = statsHash.keySet();
        if (citiesSet.contains(cityName))
        {
            Log.i(App.TAG, "contains");
            statsHash.remove(cityName);
            statsHash.put(cityName, stats);
        }
        else
        {
            Log.i(App.TAG, "not contains");
            statsHash.put(cityName, stats);
        }
    }

    public static ArrayList<String> getStats(String cityName)
    {
        return statsHash.get(cityName);
    }
}

