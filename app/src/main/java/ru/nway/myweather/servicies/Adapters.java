package ru.nway.myweather.servicies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ru.nway.myweather.R;

/**
 * Created by Klash on 15.02.2017.
 */

public class Adapters
{
    public static final WeatherApi WeatherApiAdpater = ConnectionService.createService(WeatherApi.class);
}
