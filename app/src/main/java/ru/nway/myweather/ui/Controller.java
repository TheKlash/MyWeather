package ru.nway.myweather.ui;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import ru.nway.myweather.App;
import ru.nway.myweather.R;
import ru.nway.myweather.servicies.DataService;
import ru.nway.myweather.util.RequestCode;

/**
 * Created by Klash on 15.02.2017.
 */

public class Controller {

    private static Activity mActivity;

    static void setActivity(Activity activity)
    {
        mActivity = activity;
    }

    static ArrayList<String> getRecyclerDataSet() {
        return DataService.getCitiesList();
    }

    static void addCity(String city, double lat, double lon) {
        DataService.saveToFile(city, lat, lon);
    }

    public static void callUpdateCity(String city)
    {
        ((ControllerCallback)mActivity).udpateCity(city);
    }

    public static void callUpdateWeather(ArrayList<String> result)
    {
        ((ControllerCallback)mActivity).updateWeather(result);
    }

    public static void callUpdateCurrently(double[] currently)
    {
        ((ControllerCallback)mActivity).updateCurrently(currently);
    }

    public static void callUpdateHourly(ArrayList<String> hourly)
    {

    }

    public static void callUpdateDaily(ArrayList<String> daily)
    {

    }

}
