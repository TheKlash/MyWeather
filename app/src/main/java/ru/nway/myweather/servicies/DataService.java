package ru.nway.myweather.servicies;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;

import ru.nway.myweather.App;

/**
 * Created by Klash on 17.02.2017.
 */

public class DataService
{
    private static final String FILENAME = "Cities";
    private final Context context = App.getContext();

    public ArrayList<String> loadFile()
    {
        try
        {

        }
        catch (Exception e)
        {
            File file = new File(context.getFilesDir(), FILENAME);
        }

    }

    public ArrayList<String> saveToFile()
    {
        //Будет сохранять в файл а потом возвращать loadFile();
        return null;
    }
}
