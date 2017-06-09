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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ru.nway.myweather.App;
import ru.nway.myweather.R;
import ru.nway.myweather.model.weather.Currently;
import ru.nway.myweather.model.weather.Daily;
import ru.nway.myweather.model.weather.Hourly;
import ru.nway.myweather.model.weather.MainWeatherData;
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
    //This fragment's view
    private View view;
    //Data for tabs
    public double[] currentlyData;


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
        mActivity = getActivity();
        fragmentManager = mActivity.getFragmentManager();

        try
        {
            this.view = view;

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


            mTabHost.setOnTabChangedListener(onTabChangeListener);

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

    void update(MainWeatherData data)
    {
        Currently currently = data.getCurrently();
        Hourly hourly = data.getHourly();
        Daily daily = data.getDaily();

        String temp = Double.toString(currently.getTemperature());
        mTemperatureTextView.setText(temp);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd h:mm a");
        String time = formatter.format(new Date((long)currently.getTime()*1000));
        mTimeTextView.setText(time);

        String icon = currently.getIcon();
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

        String weather = currently.getSummary();
        mWeatherTextView.setText(weather);

        currentlyData = new double[4];
        currentlyData[0] = currently.getWindSpeed();
        currentlyData[1] = currently.getHumidity();
        currentlyData[2] = currently.getPressure();
        currentlyData[3] = currently.getVisibility();
        updateCurrentlyFragment();
    }

    private void updateCurrentlyFragment()
    {
        CurrentlyFragment fragment = (CurrentlyFragment)getChildFragmentManager().findFragmentByTag("Currently");
        fragment.setData();
    }


    TabHost.OnTabChangeListener onTabChangeListener = new TabHost.OnTabChangeListener() {
        @Override
        public void onTabChanged(String tabId)
        {
            updateCurrentlyFragment();
        }
    };


    View.OnClickListener updateButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((FragmentCallback)mActivity).fragmentCallback(RequestCode.UPDATE_WEATHER, cityName);

        }
    };


    @Override
    public void onDestroy()
    {
        editor = preferences.edit();
        editor.clear();
        editor.apply();
        super.onDestroy();
    }
}
