package ru.nway.myweather.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ru.nway.myweather.R;
import ru.nway.myweather.util.CityList;

public class CitiesActivity extends AppCompatActivity {

    private ListView citiesList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        citiesList = (ListView)findViewById(R.id.citiesList);
        cities = CityList.getInstance().getCitiesList();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cities);
        citiesList.setAdapter(adapter);
    }
}