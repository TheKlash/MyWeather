package ru.nway.myweather.ui;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.nway.myweather.App;
import ru.nway.myweather.R;
import ru.nway.myweather.util.RequestCode;

public class WeatherFragment extends Fragment
{
    private TextView mCityTextView;
    private TextView mTimeTextView;
    private ImageView mImageView;
    private TextView mTemperatureTextView;
    private TextView mWeatherTextView;
    private Button mBackButton;
    private Activity mActivity;
    private static String cityName;

    public static WeatherFragment newInstance(int index) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        super.onActivityCreated(savedInstanceState);
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

        try
        {
            mCityTextView = (TextView)view.findViewById(R.id.cityTextView);
            mTimeTextView = (TextView)view.findViewById(R.id.timeTextView);

            mImageView = (ImageView)view.findViewById(R.id.imageView);
            mImageView.setOnClickListener(imageOnClickListener);

            mTemperatureTextView = (TextView)view.findViewById(R.id.temperatureTextView);
            mWeatherTextView = (TextView)view.findViewById(R.id.weatherTextView);

            mBackButton = (Button) view.findViewById(R.id.backButton);
            mBackButton.setOnClickListener(backOnClickListener);

        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        return view;
    }

    void updateTime(String time)
    {
        mTimeTextView.setText(time);
    }

    void updateWeather(ArrayList<String> data)
    {
        cityName = data.get(0);
        mCityTextView.setText(cityName);
        String temp = data.get(1);
        mTemperatureTextView.setText(temp);
        String weather = data.get(2);
        mWeatherTextView.setText(weather);
        String icon = data.get(3);

        switch (icon)
        {
            case "01d":
                mImageView.setImageResource(R.drawable.sunny);
                break;
            case "01n":
                mImageView.setImageResource(R.drawable.clear);
                break;
            case "02d":
                mImageView.setImageResource(R.drawable.partly_cloudy_day);
                break;
            case "02n":
                mImageView.setImageResource(R.drawable.partly_cloudy_night);
                break;
            case "03d":
                mImageView.setImageResource(R.drawable.cloudy);
                break;
            case "03n":
                mImageView.setImageResource(R.drawable.cloudy);
                break;
            case "04d":
                mImageView.setImageResource(R.drawable.overcast);
                break;
            case "04n":
                mImageView.setImageResource(R.drawable.overcast);
                break;
            case "09d":
                mImageView.setImageResource(R.drawable.heavy_rain);
                break;
            case "09n":
                mImageView.setImageResource(R.drawable.heavy_rain);
                break;
            case "10d":
                mImageView.setImageResource(R.drawable.heavy_rain_swrs_day);
                break;
            case "10n":
                mImageView.setImageResource(R.drawable.heavy_rain_rwrs_night);
                break;
            case "11d":
                mImageView.setImageResource(R.drawable.cloud_rain_thunder);
                break;
            case "11n":
                mImageView.setImageResource(R.drawable.cloud_rain_thunder);
                break;
            case "13d":
                mImageView.setImageResource(R.drawable.heavy_snow_swrs_day);
                break;
            case "13n":
                mImageView.setImageResource(R.drawable.heavy_snow_swr_night);
                break;
            case "50d":
                mImageView.setImageResource(R.drawable.fog);
                break;
            case "50n":
                mImageView.setImageResource(R.drawable.fog);
                break;
        }
    }

    View.OnClickListener imageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((FragmentCallback)mActivity).fragmentCallback(RequestCode.UPDATE_WEATHER, cityName);
        }
    };

    View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((FragmentCallback)mActivity).fragmentCallback(RequestCode.CALL_RECYCLER);
        }
    };
}
