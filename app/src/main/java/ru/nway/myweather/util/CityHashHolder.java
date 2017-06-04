package ru.nway.myweather.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ru.nway.myweather.App;
import ru.nway.myweather.model.weather.Datum_;
import ru.nway.myweather.model.weather.Datum__;

import static com.google.android.gms.internal.zzt.TAG;

/**
 * Created by Klash on 05.04.2017.
 */

public class CityHashHolder
{
    private HashMap<String, double[]> citiesHash;
    private HashMap<String, ArrayList<String>> statsHash;
    private HashMap<String, double[]> currentlyHash;
    private HashMap<String, ArrayList<Datum_>> hourlyHash;
    private HashMap<String, ArrayList<Datum__>> dailyHash;

    public CityHashHolder()
    {
        citiesHash = new HashMap<>();
        statsHash = new HashMap<>();
        currentlyHash = new HashMap<>();
        hourlyHash = new HashMap<>();
        dailyHash = new HashMap<>();
    }

    public HashMap<String, double[]> getCitiesHash() {
        return citiesHash;
    }

    public void setCitiesHash(HashMap<String, double[]> citiesHash) {
        this.citiesHash = citiesHash;
    }

    public double[] getCoords(String cityName)
    {
        return citiesHash.get(cityName);
    }

    public void addCity(String cityName, double lat, double lon)
    {
        double[] coords = new double[2];
        coords[0] = lat;
        coords[1] = lon;
        citiesHash.put(cityName, coords);
        Log.i(TAG, "Added new city to hash: " + cityName +
                " lat: " + lat +
                " lon: " + lon);
    }

    public void removeCity(String cityName)
    {
        citiesHash.remove(cityName);
        statsHash.remove(cityName);
        currentlyHash.remove(cityName);
    }

    public ArrayList<String> getStrings()
    {
        ArrayList list = new ArrayList();
        for (Map.Entry<String, double[]> entry: citiesHash.entrySet())
        {
            list.add(entry.getKey() + "&&" + entry.getValue()[0] + "&&" + entry.getValue()[1]);
        }

        return list;
    }

    public void setStats(String cityName, ArrayList<String> stats)
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

    public ArrayList<String> getStats(String cityName)
    {
        return statsHash.get(cityName);
    }

    public void setCurrently(String cityName, double[] curr)
    {
        Set<String> citiesSet = statsHash.keySet();
        if (citiesSet.contains(cityName))
        {
            Log.i(App.TAG, "contains");
            currentlyHash.remove(cityName);
            currentlyHash.put(cityName, curr);
        }
        else
        {
            Log.i(App.TAG, "not contains");
            currentlyHash.put(cityName, curr);
        }
    }

    public double[] getCurrently(String cityName)
    {
        return currentlyHash.get(cityName);
    }
}

