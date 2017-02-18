package ru.nway.myweather.activities;

import java.util.ArrayList;

import ru.nway.myweather.servicies.DataService;

/**
 * Created by Klash on 15.02.2017.
 */

class Controller
{
    private DataService dataService;

    Controller()
    {
        dataService = new DataService();
    }

    ArrayList<String> getRecyclerDataSet()
    {
        return dataService.loadFile();
    }
}
