package ru.nway.myweather.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.nway.myweather.App;
import ru.nway.myweather.R;
import ru.nway.myweather.servicies.DataService;
import ru.nway.myweather.util.RequestCode;

public class RecyclerFragment extends Fragment
{
    private RecyclerView mCitiesRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mFab;
    private Activity mActivity;
    private RecyclerAdapter adapter;
    //private FloatingActionButton mSettingsFab;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);

        mCitiesRecycler = (RecyclerView)view.findViewById(R.id.cities_recycler);
        mFab = (FloatingActionButton)view.findViewById(R.id.fab);
        mFab.setOnClickListener(fabOnClickListener);
        //mSettingsFab = (FloatingActionButton)view.findViewById(R.id.settingsFab);
        //mSettingsFab.setOnClickListener(settingsOnClickListener);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        setRecycler(mActivity);
    }

    private void setRecycler(Activity mActivity)
    {
        ArrayList<String> mDataset = Controller.getRecyclerDataSet();
        if (mDataset.isEmpty())
            Toast.makeText(mActivity, "Press button in the right-bottom to add cities", Toast.LENGTH_LONG).show();
        adapter = new RecyclerAdapter(mDataset, mActivity);
        /*if (mDataset.size() > 0)
            ((FragmentCallback)mActivity).fragmentCallback(RequestCode.UPDATE_WEATHER, mDataset.get(0));*/
        mCitiesRecycler.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(recyclerItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mCitiesRecycler);

        mLayoutManager = new LinearLayoutManager(App.getContext());
        mCitiesRecycler.setLayoutManager(mLayoutManager);
    }

    public void notifyAdded(String city) {
        adapter.notifyItemInserted(adapter.getItemCount());
        adapter.notifyDataSetChanged();
    }

    View.OnClickListener fabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((FragmentCallback)mActivity).fragmentCallback(RequestCode.CALL_NEW_CITY);
        }
    };

    View.OnClickListener settingsOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((FragmentCallback)mActivity).fragmentCallback(RequestCode.CALL_SETTINGS);
        }
    };

    private static class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
    {
        private ArrayList<String> mDataset;
        private Activity mActivity;

        static class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView myTextView;
            CardView cardView;
            Activity mActivity;

            private View.OnClickListener mOnclickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(App.TAG, "Вызов листнера ресайкла");
                    TextView clickedTextView = (TextView) v.findViewById(R.id.item_text);
                    String cityName = clickedTextView.getText().toString();
                    Log.i(App.TAG, "Получаем название города: " + cityName);
                    ((MainActivity) mActivity).fragmentCallback(RequestCode.CALL_WEATHER, cityName);
                }
            };

            ViewHolder(View v, Activity mActivity) {
                super(v);
                this.mActivity = mActivity;
                v.setOnClickListener(mOnclickListener);
                cardView = (CardView) v.findViewById(R.id.cardViewItem);
                myTextView = (TextView) v.findViewById(R.id.item_text);
            }
        }

        RecyclerAdapter(ArrayList<String> dataset, Activity mActivity) {
            mDataset = dataset;
            this.mActivity = mActivity;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_item, parent, false);

            return new ViewHolder(v, mActivity);
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

    private ItemTouchHelper.SimpleCallback recyclerItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            TextView textView = (TextView) viewHolder.itemView.findViewById(R.id.item_text);
            String cityName = textView.getText().toString();

            DataService.removeCity(cityName);
            adapter.notifyItemRemoved(direction);
            adapter.notifyDataSetChanged();
        }
    };

}
