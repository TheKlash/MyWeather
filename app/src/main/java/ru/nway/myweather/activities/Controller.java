package ru.nway.myweather.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nway.myweather.App;
import ru.nway.myweather.R;
import ru.nway.myweather.entity.MainWeatherData;
import ru.nway.myweather.entity.Weather;
import ru.nway.myweather.servicies.ConnectionService;
import ru.nway.myweather.servicies.DataService;
import ru.nway.myweather.servicies.WeatherApi;

/**
 * Created by Klash on 15.02.2017.
 */

class Controller
{
    private DataService dataService;
    private static WeatherApi api;
    private static MainWeatherData data;

    static
    {
        api = ConnectionService.createService(WeatherApi.class);
    }

    Controller()
    {
        dataService = new DataService();
    }

    ArrayList<String> getRecyclerDataSet()
    {
        return dataService.loadFile();
    }

    RecyclerAdapter getRecylerAdapter(ArrayList<String> dataset)
    {
        return new RecyclerAdapter(dataset);
    }

    private static class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
    {
        private ArrayList<String> mDataset;

        public static class ViewHolder extends RecyclerView.ViewHolder
        {
            public TextView myTextView;
            public CardView cardView;
            private final Context context;

            private View.OnClickListener mOnclickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView clickedTextView = (TextView)v.findViewById(R.id.item_text);
                    String cityName = clickedTextView.getText().toString();

                    Intent intent = new Intent(context, WeatherActivity.class);
                    intent.putExtra("cityName", cityName);
                    context.startActivity(intent);
                }
            };

            public ViewHolder(View v)
            {
                super(v);
                v.setOnClickListener(mOnclickListener);
                cardView = (CardView)v.findViewById(R.id.cardViewItem);
                myTextView = (TextView)v.findViewById(R.id.item_text);
                context = v.getContext();
            }
        }

        public RecyclerAdapter(ArrayList<String> dataset)
        {
            mDataset = dataset;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_item, parent, false);

            // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.myTextView.setText(mDataset.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

    public static MainWeatherData callServer(String city)
    {

        api.getForecast(city, App.getAppKey()).enqueue(new Callback<MainWeatherData>() {
            @Override
            public void onResponse(Call<MainWeatherData> call, Response<MainWeatherData> response) {
                if (response.isSuccessful())
                {
                    data = response.body();
                }
            }

            @Override
            public void onFailure(Call<MainWeatherData> call, Throwable t) {

            }
        });

        return data;
    }

}
