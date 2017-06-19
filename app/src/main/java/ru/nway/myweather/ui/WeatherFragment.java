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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import ru.nway.myweather.App;
import ru.nway.myweather.R;
import ru.nway.myweather.model.weather.Currently;
import ru.nway.myweather.model.weather.Daily;
import ru.nway.myweather.model.weather.Datum_;
import ru.nway.myweather.model.weather.Datum__;
import ru.nway.myweather.model.weather.Hourly;
import ru.nway.myweather.model.weather.MainWeatherData;
import ru.nway.myweather.ui.details.CurrentlyFragment;
import ru.nway.myweather.ui.details.DailyFragment;
import ru.nway.myweather.ui.details.DetailRecyclerFragment;
import ru.nway.myweather.ui.details.HourlyFragment;
import ru.nway.myweather.util.RequestCode;

public class WeatherFragment extends Fragment
{
    //WeatherFragment proper
    private TextView mCityTextView;
    private TextView mTimeTextView;
    private ImageView mImageView;
    private TextView mTemperatureTextView;
    private TextView mWeatherTextView;
    private Activity mActivity;
    private String cityName;
    private Button mUpdateButton;
    private FragmentManager fragmentManager;
    private FragmentTabHost mTabHost;
    //This fragment's view
    private View view;
    //Data for tabs
    public double[] currentlyData;
    public ArrayList<Datum_> hourlyData;
    public ArrayList<Datum__> dailyData;
    //Parsed MainWeatherData
    private Currently currently;
    private Hourly hourly;
    private Daily daily;
    //Detail Fragments
    private CurrentlyFragment currentlyFragment;
    private HourlyFragment hourlyFragment;
    private DailyFragment dailyFragment;
    //TimeZone
    private TimeZone timeZone;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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
        this.view = view;

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
            mTabHost.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            mTabHost.addTab(mTabHost.newTabSpec("Currently").setIndicator("Currently"),
                    CurrentlyFragment.class, null);
            mTabHost.addTab(mTabHost.newTabSpec("Hourly").setIndicator("Hourly"),
                    HourlyFragment.class, null);
            mTabHost.addTab(mTabHost.newTabSpec("Daily").setIndicator("Daily"),
                    DailyFragment.class, null);
            mTabHost.setVerticalScrollBarEnabled(true);

        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    private void setIcon(String icon)
    {
        IconPicker picker = new IconPicker();
        mImageView.setImageResource(picker.pick(icon));
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
        currently = data.getCurrently();
        hourly = data.getHourly();
        daily = data.getDaily();

        timeZone = TimeZone.getTimeZone(data.getTimezone());

        String temp = Integer.toString((int)Math.round(currently.getTemperature())) + App.TEMP_POSTFX;
        mTemperatureTextView.setText(temp);

        SimpleDateFormat formatter = new SimpleDateFormat(App.DATE_FORMAT_LONG + " " + App.TIME_FORMAT);
        formatter.setTimeZone(timeZone);
        String time = formatter.format(new Date((long)currently.getTime()*1000));
        mTimeTextView.setText(time);

        String icon = currently.getIcon();
        setIcon(icon);

        String weather = currently.getSummary();
        mWeatherTextView.setText(weather);
        updateCurrentlyFragment();
        Log.d("CONGRATS", "you're reached the final base!");
    }

    public void updateCurrentlyFragment()
    {
        currentlyFragment = (CurrentlyFragment)getChildFragmentManager().findFragmentByTag("Currently");

        currentlyData = new double[6];
        currentlyData[0] = currently.getWindSpeed();
        currentlyData[1] = currently.getHumidity();
        currentlyData[2] = currently.getPressure();
        currentlyData[3] = currently.getPrecipProbability();
        currentlyData[4] = currently.getCloudCover();
        currentlyData[5] = currently.getWindBearing();

        currentlyFragment.setData();
    }

    public void updateHourlyFragment()
    {
        hourlyFragment = (HourlyFragment) getChildFragmentManager().findFragmentByTag("Hourly");
        hourlyData = new ArrayList<>(hourly.getData());
        hourlyFragment.setData();
    }

    public void updateDailyFragment()
    {
        dailyFragment = (DailyFragment) getChildFragmentManager().findFragmentByTag("Daily");
        dailyData = new ArrayList<>(daily.getData());
        dailyFragment.setData();
    }

    public TimeZone getTimeZone()
    {
        return timeZone;
    }

    View.OnClickListener updateButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((FragmentCallback)mActivity).fragmentCallback(RequestCode.UPDATE_WEATHER, cityName);

        }
    };

    FragmentTabHost.OnTabChangeListener onTabChangeListener = new TabHost.OnTabChangeListener() {
        @Override
        public void onTabChanged(String tabId) {
            //updateDetails();
            }
    };
}
