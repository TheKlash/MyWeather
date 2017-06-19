package ru.nway.myweather.ui.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ru.nway.myweather.App;
import ru.nway.myweather.R;
import ru.nway.myweather.model.weather.Datum__;
import ru.nway.myweather.ui.WeatherFragment;

/**
 * Created by Klash on 19.04.2017.
 */

public class DailyFragment extends DetailRecyclerFragment
{
    private DetailRecyclerAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mDailyRecycler;
    private ArrayList<Datum__> data;
    private WeatherFragment weatherFragment;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_daily, container, false);
        weatherFragment = (WeatherFragment)getParentFragment();
        mDailyRecycler = (RecyclerView)view.findViewById(R.id.daily_recycler);
        return view;
    }

    @Override
    public void onResume()
    {
        weatherFragment.updateDailyFragment();
        super.onResume();
    }

    @Override
    protected void setRecycler(ArrayList<HourlyDailyData> dataset) {
        adapter = new DetailRecyclerFragment.DetailRecyclerAdapter(dataset);
        mDailyRecycler.setAdapter(adapter);

        mLayoutManager = new LinearLayoutManager(App.getContext());
        mDailyRecycler.setLayoutManager(mLayoutManager);
    }

    public void setData()
    {
        data = weatherFragment.dailyData;
        parseData();
    }

    private void parseData()
    {
        ArrayList<HourlyDailyData> dataset = new ArrayList<>();
        for (Datum__ d: data)
        {
            SimpleDateFormat formatter = new SimpleDateFormat(App.DATE_FORMAT_SHORT);
            formatter.setTimeZone(weatherFragment.getTimeZone());
            String time = formatter.format(new Date((long)d.getTime()*1000));
            String temp = (int)Math.round(d.getTemperatureMin())
                    + ".."
                    + (int)Math.round(d.getApparentTemperatureMax());
            String icon = d.getIcon();

            HourlyDailyData hd = new HourlyDailyData(time, temp, icon);
            dataset.add(hd);
        }

        setRecycler(dataset);
    }
}
