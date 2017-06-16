package ru.nway.myweather.ui.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ru.nway.myweather.App;
import ru.nway.myweather.R;
import ru.nway.myweather.model.weather.Datum_;
import ru.nway.myweather.model.weather.Datum__;
import ru.nway.myweather.ui.WeatherFragment;

/**
 * Created by Klash on 19.04.2017.
 */

public class HourlyFragment extends DetailRecyclerFragment
{
    DetailRecyclerAdapter adapter;
    RecyclerView mHourlyRecycler;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Datum_> data;
    private WeatherFragment weatherFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_hourly, container, false);
        weatherFragment = (WeatherFragment)getParentFragment();
        mHourlyRecycler = (RecyclerView) view.findViewById(R.id.hourly_recycler);

        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }


    @Override
    public void onResume()
    {

        weatherFragment.updateHourlyFragment();
        parseData();

        super.onResume();
    }

    public void setData()
    {
        data = weatherFragment.hourlyData;
        parseData();
    }

    private void parseData()
    {
        ArrayList<HourlyDailyData> hourlyDaily = new ArrayList<>();
        for (int i = 0; i < 24; i++)
        {
            Datum_ d = data.get(i);
            SimpleDateFormat formatter = new SimpleDateFormat(App.TIME_FORMAT);
            String time = formatter.format(new Date((long)d.getTime()*1000));
            String temp = Integer.toString((int)Math.round(d.getTemperature()));
            String icon = d.getIcon();

            Log.i("parseData", time + " " + temp + " " + icon);

            HourlyDailyData hd = new HourlyDailyData(time, temp, icon);
            hourlyDaily.add(hd);
        }

        setRecycler(hourlyDaily);
    }

    @Override
    protected void setRecycler(ArrayList<HourlyDailyData> dataset)
    {
        ArrayList<HourlyDailyData> mDataset = dataset;
        adapter = new DetailRecyclerFragment.DetailRecyclerAdapter(mDataset);
        mHourlyRecycler.setAdapter(adapter);

        mLayoutManager = new LinearLayoutManager(App.getContext());
        mHourlyRecycler.setLayoutManager(mLayoutManager);
    }


}
