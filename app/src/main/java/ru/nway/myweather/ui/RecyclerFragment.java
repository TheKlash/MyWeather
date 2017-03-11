package ru.nway.myweather.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private static Activity mActivity;
    private static RecyclerAdapter adapter;


    public static RecyclerFragment newInstance(int index) {
        RecyclerFragment fragment = new RecyclerFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        mActivity = getActivity();
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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRecycler();
    }

    private void setRecycler()
    {
        ArrayList<String> mDataset = Controller.getRecyclerDataSet();
        adapter = new RecyclerAdapter(mDataset);
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

    private static class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
    {
        private ArrayList<String> mDataset;

        static class ViewHolder extends RecyclerView.ViewHolder
        {
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

            return new ViewHolder(v);
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
            adapter.notifyItemRemoved(direction);
            adapter.notifyDataSetChanged();
        }
    };

}
