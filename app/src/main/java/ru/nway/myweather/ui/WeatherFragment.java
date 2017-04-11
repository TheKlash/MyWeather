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
    private Activity mActivity;
    private static String cityName;

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

        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        return view;
    }

    void updateCity(String city)
    {
        mCityTextView.setText(city);
    }

    void updateTime(String time)
    {
        mTimeTextView.setText(time);
    }

    void updateWeather(ArrayList<String> data)
    {
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
