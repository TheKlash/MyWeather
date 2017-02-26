package ru.nway.myweather.entity;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Klash on 22.02.2017.
 */

public class MainWeatherDataDeserializer implements JsonDeserializer<MainWeatherData>
{
    @Override
    public MainWeatherData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        final GsonBuilder builder = new GsonBuilder();

        MainWeatherData data = new MainWeatherData();

        final JsonObject jsonObject = json.getAsJsonObject();
        Coord coord = context.deserialize(jsonObject.get("coord"), Coord.class);

        final Weather[] weatherArray = new Weather[1];
        final JsonArray jsonWeatherArray = jsonObject.get("weather").getAsJsonArray();
        JsonElement jsonWeather = jsonWeatherArray.get(0);
        weatherArray[0] = context.deserialize(jsonWeather, Weather.class);

        final String base = jsonObject.get("base").getAsString();
        Main main = context.deserialize(jsonObject.get("main"), Main.class);
        final int visibility = jsonObject.get("visibility").getAsInt();
        Wind wind  = context.deserialize(jsonObject.get("wind"), Wind.class);
        Clouds clouds = context.deserialize(jsonObject.get("clouds"), Clouds.class);
        int dt = jsonObject.get("dt").getAsInt();
        Sys sys = context.deserialize(jsonObject.get("sys"), Sys.class);
        int id = jsonObject.get("id").getAsInt();
        String name = jsonObject.get("name").getAsString();
        int cod = jsonObject.get("cod").getAsInt();

        data.setCoord(coord);
        data.setBase(base);
        data.setMain(main);

        ArrayList<Weather> weatherArrayList = new ArrayList<>(Arrays.asList(weatherArray));
        data.setWeather(weatherArrayList);
        data.setVisibility(visibility);
        data.setWind(wind);
        data.setClouds(clouds);
        data.setDt(dt);
        data.setSys(sys);
        data.setId(id);
        data.setName(name);
        data.setCod(cod);

        return data;
    }
}
