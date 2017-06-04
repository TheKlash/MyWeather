package ru.nway.myweather.servicies;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.Scanner;

import ru.nway.myweather.App;
import ru.nway.myweather.util.CityHashHolder;

/**
 * Created by Klash on 17.02.2017.
 */

public class DataService
{
    private static final String FILENAME = "Cities";
    private static ArrayList<String> citiesList;

    static
    {
        citiesList = new ArrayList<>();
        loadFile();
    }

    public static ArrayList<String> getCitiesList()
    {
        return citiesList;
    }

    public static void loadFile()
    {
        Context CONTEXT = App.getContext();
        FileInputStream is;

        try
        {
            is = CONTEXT.openFileInput(FILENAME);
            Scanner s = new Scanner(is).useDelimiter("\\n");

            while (true)
            {
                if (s.hasNext())
                {
                    String curString = s.next();
                    Log.i("READING STRING", curString);
                    String[] cityData = curString.split("&&");

                    double lat = 0.0;
                    double lon = 0.0;
                    try
                    {
                        lat = Double.parseDouble(cityData[1]);
                        lon = Double.parseDouble(cityData[2]);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    App.hash.addCity(
                            cityData[0],
                            lat,
                            lon
                    );
                    citiesList.add(cityData[0]);
                }
                else
                {
                    break;
                }
            }

            s.close();
            is.close();
        }
        catch (FileNotFoundException e)
        {
            try
            {
                File file = new File(CONTEXT.getFilesDir(), FILENAME);
                file.createNewFile();
            }
            catch (IOException e1)
            {
                System.out.println("CHECK FILE DIRECTORY!!!!");
            }

            loadFile();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public static void saveToFile(String city, double lat, double lon)
    {
        Context CONTEXT = App.getContext();
        citiesList.add(city);
        String write = city + "&&" + lat + "&&" + lon + "\n";
        try
        {
            FileOutputStream os = CONTEXT.openFileOutput(FILENAME, Context.MODE_APPEND);
            os.write(write.getBytes());
            os.close();

            App.hash.addCity(city, lat, lon);
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void removeCity(String cityName)
    {
        Context CONTEXT = App.getContext();
        App.hash.removeCity(cityName);
        ArrayList<String> recordsList = App.hash.getStrings();
        citiesList.remove(cityName);
        try
        {
            FileOutputStream os = CONTEXT.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            for (String record: recordsList)
            {
                String write = record + "\n";
                os.write(write.getBytes());
            }

            os.close();
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
