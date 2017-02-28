package ru.nway.myweather.servicies;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import ru.nway.myweather.App;

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
                    citiesList.add(s.next());
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

                writer.write("Moscow\n");
                writer.write("St. Petersbourgh\n");
                writer.write("Tokyo\n");

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

    public static void saveToFile(String city)
    {
        Context CONTEXT = App.getContext();
        citiesList.add(city);
        String write = city + "\n";
        try
        {
            FileOutputStream os = CONTEXT.openFileOutput(FILENAME, Context.MODE_APPEND);
            os.write(write.getBytes());
            os.close();
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
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
