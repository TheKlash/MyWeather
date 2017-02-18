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

/**
 * Created by Klash on 17.02.2017.
 */

public class DataService
{
    private static final String FILENAME = "Cities";
    private final Context CONTEXT = App.getContext();

    public ArrayList<String> loadFile()
    {
        FileInputStream is;
        ArrayList<String> citiesList = new ArrayList<>();;

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

            citiesList = loadFile();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

        return citiesList;
    }

    public ArrayList<String> saveToFile()
    {
        //Будет сохранять в файл а потом возвращать loadFile();
        return null;
    }
}
