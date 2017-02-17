package ru.nway.myweather.activities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.nway.myweather.R;
import ru.nway.myweather.util.CityList;

/**
 * Created by Klash on 15.02.2017.
 */

class Controller
{
    private CityList cityList;

    Controller()
    {
        cityList = CityList.getInstance();
    }

    ArrayList<String> getRecyclerDataSet()
    {
        return cityList.getCitiesList();
    }
}
