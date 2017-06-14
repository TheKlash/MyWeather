package ru.nway.myweather.ui;

import ru.nway.myweather.R;

/**
 * Created by Klash on 12.06.2017.
 */

public class IconPicker
{
    private int res;

    public int pick(String icon)
    {
        switch (icon)
        {
            case "clear-day":
                res = R.drawable.sunny;
                break;
            case "clear-night":
                res = R.drawable.clear;
                break;
            case "partly-cloudy-day":
                res = R.drawable.partly_cloudy_day;
                break;
            case "partly-cloudy-night":
                res =R.drawable.partly_cloudy_night;
                break;
            case "cloudy":
                res = R.drawable.cloudy;
                break;
            case "sleet":
                res = R.drawable.mod_sleet;
                break;
            case "wind":
                res = R.drawable.wind;
                break;
            case "snow":
                res = R.drawable.mod_snow;
                break;
            case "fog":
                res = R.drawable.fog;
                break;
            case "rain":
                res = R.drawable.mod_rain;
                break;
            default:
                res = R.drawable.overcast;
                break;
        }

        return res;
    }
}
