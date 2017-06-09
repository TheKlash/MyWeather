package ru.nway.myweather.ui.details;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ru.nway.myweather.R;
import ru.nway.myweather.ui.WeatherFragment;

/**
 * Created by Klash on 19.04.2017.
 */

public class CurrentlyFragment extends Fragment
{
    private WeatherFragment weatherFragment;
    private double[] data;
    private View hourlyView;
    private View dailyView;
    private TextView mWindSpeed;
    private TextView mHumidity;
    private TextView mPressure;
    private TextView mVisibility;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        weatherFragment = (WeatherFragment)getParentFragment();
        View view = inflater.inflate(R.layout.fragment_currently, container, false);

        mWindSpeed = (TextView)view.findViewById(R.id.windSpeedTextView);
        mHumidity = (TextView)view.findViewById(R.id.humidityTextView);
        mPressure = (TextView)view.findViewById(R.id.pressureTextView);
        mVisibility = (TextView)view.findViewById(R.id.visibilityTextView);

        return view;
    }

    @Override
    public void onStart()
    {
        data = weatherFragment.getCurrentlyData();

        /*
        mWindSpeed.setText(String.valueOf(data[0]));
        mHumidity.setText(String.valueOf(data[1]));
        mPressure.setText(String.valueOf(data[2]));
        mVisibility.setText(String.valueOf(data[3]));
        */

        super.onStart();
    }
}
