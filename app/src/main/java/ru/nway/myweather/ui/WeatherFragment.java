package ru.nway.myweather.ui;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

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
    private static String cityName;
    private Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity mActivity = (MainActivity)context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        try
        {
            mCityTextView = (TextView)getView().findViewById(R.id.cityTextView);
            mTimeTextView = (TextView)getView().findViewById(R.id.timeTextView);

            mImageView = (ImageView)getView().findViewById(R.id.imageView);
            mImageView.setOnClickListener(imageOnClickListener);

            mTemperatureTextView = (TextView)getView().findViewById(R.id.temperatureTextView);
            mWeatherTextView = (TextView)getView().findViewById(R.id.weatherTextView);

            mBackButton = (Button) getView().findViewById(R.id.backButton);
            mBackButton.setOnClickListener(backOnClickListener);

        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        return view;
    }

    static void update(ArrayList<String> data)
    {
        cityName = data.get(0);
    }

    static void setTime(String time)
    {

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
