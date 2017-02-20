package ru.nway.myweather.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.nway.myweather.App;
import ru.nway.myweather.R;

public class CitiesActivity extends AppCompatActivity {

    private Controller controller;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        controller = new Controller();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mRecyclerView = (RecyclerView)findViewById(R.id.cities_recycler);

        mAdapter = new RecyclerAdapter(controller.getRecyclerDataSet());
        mRecyclerView.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(App.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);


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

}
