package ru.nway.myweather.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.nway.myweather.App;
import ru.nway.myweather.R;
import ru.nway.myweather.model.weather.Main;
import ru.nway.myweather.servicies.DataService;
import ru.nway.myweather.util.RequestCode;

/**
 * Created by Klash on 15.02.2017.
 */

public class Controller {

    private static Activity mActivity;
    private static RecyclerAdapter adapter;

    static ArrayList<String> getRecyclerDataSet() {
        return DataService.getCitiesList();
    }

    static RecyclerAdapter getRecyclerAdapter(ArrayList<String> dataset) {
        if (adapter == null) {
            adapter = new RecyclerAdapter(dataset);
        }

        return adapter;
    }

    static void setActivity(Activity activity) {
        mActivity = activity;
    }

    private static class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        private ArrayList<String> mDataset;

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView myTextView;
            CardView cardView;

            private View.OnClickListener mOnclickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView clickedTextView = (TextView) v.findViewById(R.id.item_text);
                    String cityName = clickedTextView.getText().toString();
                    ((MainActivity) mActivity).fragmentCallback(RequestCode.CALL_WEATHER, cityName);
                }
            };

            ViewHolder(View v) {
                super(v);
                v.setOnClickListener(mOnclickListener);
                cardView = (CardView) v.findViewById(R.id.cardViewItem);
                myTextView = (TextView) v.findViewById(R.id.item_text);
            }
        }

        RecyclerAdapter(ArrayList<String> dataset) {
            mDataset = dataset;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_item, parent, false);

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

    private static ItemTouchHelper.SimpleCallback recyclerItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            TextView textView = (TextView) viewHolder.itemView.findViewById(R.id.item_text);
            String cityName = textView.getText().toString();

            DataService.removeCity(cityName);
            Toast.makeText(App.getContext(), "Deleting " + cityName, Toast.LENGTH_SHORT).show();
            adapter.notifyItemRemoved(direction);
            adapter.notifyDataSetChanged();
        }
    };

    static ItemTouchHelper getItemTouchHelper() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(recyclerItemTouchCallback);
        return itemTouchHelper;
    }

    static void addCity(String cityName) {
        DataService.saveToFile(cityName);
        adapter.notifyItemInserted(adapter.getItemCount());
        adapter.notifyDataSetChanged();
    }

    public static void callUpdateWeather(ArrayList<String> list)
    {
        ((ConnectionCallback)mActivity).connectionCallback(list);
    }
}
