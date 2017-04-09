package ru.nway.myweather.servicies;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
                    String[] cityData = s.next().split("&&");
                    CityHashHolder.addCity(
                            cityData[0],
                            Double.parseDouble(cityData[1]),
                            Double.parseDouble(cityData[2])
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
                PrintWriter writer = new PrintWriter(new FileOutputStream(file));

                writer.write("Moscow&&0.0&&0.0\n");
                writer.write("St. Petersbourgh&&0.0&&0.0\n");
                writer.write("Tokyo&&0.0&&0.0\n");

                writer.close();
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

            CityHashHolder.addCity(city, lat, lon);
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void removeCity(String city)
    {
        Context CONTEXT = App.getContext();
        citiesList.remove(city);
        try
        {
            FileOutputStream os = CONTEXT.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            for (String cityName: citiesList)
            {
                String write = cityName + "\n";
                os.write(write.getBytes());
            }

            os.close();

            CityHashHolder.removeCity(city);
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
