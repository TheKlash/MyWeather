package ru.nway.myweather.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

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
                Snackbar.make(view, "Add city", Snackbar.LENGTH_LONG)
                        .setAction("Add", addCityListener).show();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.cities_recycler);
        setRecycler();

    }

    View.OnClickListener addCityListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent newCityActivityIntent = new Intent(App.getContext(), NewCityActivity.class);
            startActivityForResult(newCityActivityIntent, 0);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(!data.getStringExtra("city").equals(""))
        {
            Toast.makeText(this, data.getStringExtra("city"), Toast.LENGTH_SHORT).show();
            Controller.addCity(data.getStringExtra("city"));
            setRecycler();
        }
    }

    private void setRecycler()
    {
        ArrayList<String> mDataset = controller.getRecyclerDataSet();
        mRecyclerView.setAdapter(controller.getRecyclerAdapter(mDataset));

        ItemTouchHelper itemTouchHelper = Controller.getItemTouchHelper();
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mLayoutManager = new LinearLayoutManager(App.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}

