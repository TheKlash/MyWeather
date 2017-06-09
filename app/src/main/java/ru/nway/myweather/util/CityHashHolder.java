package ru.nway.myweather.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ru.nway.myweather.App;
import ru.nway.myweather.model.weather.Datum_;
import ru.nway.myweather.model.weather.Datum__;
import ru.nway.myweather.model.weather.MainWeatherData;

import static com.google.android.gms.internal.zzt.TAG;

/**
 * Created by Klash on 05.04.2017.
 */

public class CityHashHolder {
    private HashMap<String, double[]> citiesHash;
    private HashMap<String, MainWeatherData> statsHash;

    public CityHashHolder() {
        citiesHash = new HashMap<>();
        statsHash = new HashMap<>();
    }

    public HashMap<String, double[]> getCitiesHash() {
        return citiesHash;
    }

    public void setCitiesHash(HashMap<String, double[]> citiesHash) {
        this.citiesHash = citiesHash;
    }

    public double[] getCoords(String cityName) {
        return citiesHash.get(cityName);
    }

    public void addCity(String cityName, double lat, double lon) {
        double[] coords = new double[2];
        coords[0] = lat;
        coords[1] = lon;
        citiesHash.put(cityName, coords);
        Log.i(TAG, "Added new city to hash: " + cityName +
                " lat: " + lat +
                " lon: " + lon);
    }

    public void removeCity(String cityName) {
        citiesHash.remove(cityName);
        statsHash.remove(cityName);
    }

    public ArrayList<String> getStrings() {
        ArrayList list = new ArrayList();
        for (Map.Entry<String, double[]> entry : citiesHash.entrySet()) {
            list.add(entry.getKey() + "&&" + entry.getValue()[0] + "&&" + entry.getValue()[1]);
        }

        return list;
    }

    public void setStats(String city, MainWeatherData stats) {
        statsHash.put(city, stats);
    }

    public MainWeatherData getStats(String city)
    {
        return statsHash.get(city);
    }
}
