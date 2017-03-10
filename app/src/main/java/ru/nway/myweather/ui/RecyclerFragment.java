package ru.nway.myweather.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.nway.myweather.App;
import ru.nway.myweather.R;
import ru.nway.myweather.util.RequestCode;

public class RecyclerFragment extends Fragment
{
    private RecyclerView mCitiesRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mFab;
    private static Activity mActivity;

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
        Controller.setActivity(mActivity);
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
        /*if (mDataset.size() > 0)
            ((FragmentCallback)mActivity).fragmentCallback(RequestCode.UPDATE_WEATHER, mDataset.get(0));*/
        mCitiesRecycler.setAdapter(Controller.getRecyclerAdapter(mDataset));

        ItemTouchHelper itemTouchHelper = Controller.getItemTouchHelper();
        itemTouchHelper.attachToRecyclerView(mCitiesRecycler);

        mLayoutManager = new LinearLayoutManager(App.getContext());
        mCitiesRecycler.setLayoutManager(mLayoutManager);
    }

    View.OnClickListener fabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((FragmentCallback)mActivity).fragmentCallback(RequestCode.CALL_NEW_CITY);
        }
    };
}
