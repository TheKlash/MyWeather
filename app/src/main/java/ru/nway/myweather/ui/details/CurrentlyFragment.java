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
import java.util.Formatter;

import ru.nway.myweather.App;
import ru.nway.myweather.R;
import ru.nway.myweather.ui.WeatherFragment;

/**
 * Created by Klash on 19.04.2017.
 */

public class CurrentlyFragment extends Fragment
{
    private WeatherFragment weatherFragment;
    private double[] data;
    private TextView mWindSpeed;
    private TextView mHumidity;
    private TextView mPressure;
    private TextView mPrecipProbability;
    private TextView mCover;
    private TextView mDirection;
    private Bundle bundle;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = savedInstanceState;
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
        mPrecipProbability = (TextView)view.findViewById(R.id.visibilityTextView);
        mCover = (TextView)view.findViewById(R.id.coverTextView);
        mDirection = (TextView)view.findViewById(R.id.directionTextView);

        return view;
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onResume()
    {
        if (data != null)
        {
            setStats();
        }
        super.onResume();
    }

    public void setData()
    {
        this.data =  weatherFragment.currentlyData;
        setStats();
    }

    private void setStats()
    {
        String humidityString = String.format("%.2f", data[1]*100)+"%";
        String coverString = String.format("%.2f", data[4]*100)+"%";

        mWindSpeed.setText(String.valueOf(data[0]) + " " + App.WIND_UNIT);
        mHumidity.setText(String.valueOf(humidityString));
        mPressure.setText(String.valueOf((int)data[2]) + " " + App.PRESSURE_UNIT);
        mPrecipProbability.setText(String.valueOf((int)(data[3]*100)) + "%");
        mCover.setText(coverString);

        double direction = data[5];
        String dirString = "";
        if (direction >= 337.5 || (direction >= 0 && direction < 22.5))
            dirString = "N";
        else if (direction >= 22.5 && direction < 67.5)
            dirString = "NE";
        else if (direction >= 67.5 && direction < 112.5)
            dirString = "E";
        else if (direction >= 112.5 && direction < 157.5)
            dirString = "SE";
        else if (direction >= 157.5 && direction < 202.5)
            dirString = "S";
        else if (direction >= 202.5 && direction < 247.5)
            dirString = "SW";
        else  if (direction >= 247.5 && direction < 292.5)
            dirString = "W";
        else if (direction >= 292.5 && direction < 337.5)
            dirString = "NW";

        mDirection.setText(dirString);
    }
}
