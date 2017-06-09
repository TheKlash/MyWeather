package ru.nway.myweather.ui;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

import ru.nway.myweather.App;
import ru.nway.myweather.R;
import ru.nway.myweather.model.weather.Currently;
import ru.nway.myweather.ui.details.CurrentlyFragment;
import ru.nway.myweather.ui.details.DailyFragment;
import ru.nway.myweather.ui.details.HourlyFragment;
import ru.nway.myweather.util.RequestCode;

public class WeatherFragment extends Fragment
{
    //SharedPreferences for saving data
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    //WeatherFragment proper
    private TextView mCityTextView;
    private TextView mTimeTextView;
    private ImageView mImageView;
    private TextView mTemperatureTextView;
    private TextView mWeatherTextView;
    private Activity mActivity;
    private static String cityName;
    private Button mUpdateButton;
    private FragmentManager fragmentManager;
    private FragmentTabHost mTabHost;
    //CurrentlyFragment views
    private LayoutInflater layoutInflater;
    //Data for tabs
    private double[] currentlyData;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        preferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        layoutInflater = getLayoutInflater(savedInstanceState);

        mActivity = getActivity();
        fragmentManager = mActivity.getFragmentManager();

        try
        {
            mCityTextView = (TextView)view.findViewById(R.id.cityTextView);
            mTimeTextView = (TextView)view.findViewById(R.id.timeTextView);

            mImageView = (ImageView)view.findViewById(R.id.imageView);

            mUpdateButton = (Button)view.findViewById(R.id.updateButton);
            mUpdateButton.setOnClickListener(updateButtonOnClickListener);

            mTemperatureTextView = (TextView)view.findViewById(R.id.tempTextView);
            mWeatherTextView = (TextView)view.findViewById(R.id.weatherTextView);

            mTabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
            mTabHost.setup(mActivity, getChildFragmentManager(), android.R.id.tabcontent);
            mTabHost.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

            mTabHost.addTab(mTabHost.newTabSpec("Currently").setIndicator("Currently"),
                    CurrentlyFragment.class, null);
            mTabHost.addTab(mTabHost.newTabSpec("Hourly").setIndicator("Hourly"),
                    HourlyFragment.class, null);
            mTabHost.addTab(mTabHost.newTabSpec("Daily").setIndicator("Daily"),
                    DailyFragment.class, null);

            //mTabHost.setOnTabChangedListener(onTabChangeListener);

        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    void updateCity(String city)
    {
        cityName = city;
        mCityTextView.setText(cityName);
    }

    void updateTime(String time)
    {
        mTimeTextView.setText(time);
    }

    void updateWeather(ArrayList<String> data)
    {
        Log.i(App.TAG, "Вызываем updateWeather в WeatherFragment, data[0] = " + data.get(0));
        String temp = data.get(0);
        mTemperatureTextView.setText(temp);
        String weather = data.get(1);
        mWeatherTextView.setText(weather);
        String icon = data.get(2);

        switch (icon)
        {
            case "clear-day":
                mImageView.setImageResource(R.drawable.sunny);
                break;
            case "clear-night":
                mImageView.setImageResource(R.drawable.clear);
                break;
            case "partly-cloudy-day":
                mImageView.setImageResource(R.drawable.partly_cloudy_day);
                break;
            case "partly-cloudy-night":
                mImageView.setImageResource(R.drawable.partly_cloudy_night);
                break;
            case "cloudy":
                mImageView.setImageResource(R.drawable.cloudy);
                break;
            case "sleet":
                mImageView.setImageResource(R.drawable.mod_sleet);
                break;
            case "wind":
                mImageView.setImageResource(R.drawable.wind);
                break;
            case "snow":
                mImageView.setImageResource(R.drawable.mod_snow);
                break;
            case "fog":
                mImageView.setImageResource(R.drawable.fog);
                break;
            case "rain":
                mImageView.setImageResource(R.drawable.mod_rain);
                break;
            default:
                mImageView.setImageResource(R.drawable.overcast);
                break;
        }

        //adding to SharedPreferences
        editor = preferences.edit();
        editor.putString("temp", temp);
        editor.putString("weather", weather);
        editor.putString("icon", icon);
        editor.commit();
    }



    void updateCurrently(double[] currently) throws Exception
    {
        currentlyData = currently;
    }

    void updateHourly(ArrayList<String> hourly)
    {

    }

    void updateDaily(ArrayList<String> daily)
    {

    }

    View.OnClickListener updateButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((FragmentCallback)mActivity).fragmentCallback(RequestCode.UPDATE_WEATHER, cityName);

        }
    };

    /*
    TabHost.OnTabChangeListener onTabChangeListener = new TabHost.OnTabChangeListener() {
        @Override
        public void onTabChanged(String tabId)
        {
            switch(tabId)
            {
                case "Currently":
                {
                    updateCurrently();
                    break;
                }

                case "Hourly": {break;}
                case "Daily": {break;}
                default: Log.i("tabs", "wooops");
            }
        }
    };
    */

    public double[] getCurrentlyData()
    {
        return currentlyData;
    }

    @Override
    public void onDestroy()
    {
        editor = preferences.edit();
        editor.clear();
        editor.apply();
        super.onDestroy();
    }
}
