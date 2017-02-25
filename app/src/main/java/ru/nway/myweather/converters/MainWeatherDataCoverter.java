package ru.nway.myweather.converters;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import ru.nway.myweather.entity.Main;
import ru.nway.myweather.entity.MainWeatherData;
import ru.nway.myweather.entity.Simplified;
import ru.nway.myweather.entity.Weather;

/**
 * Created by Klash on 22.02.2017.
 */

public class MainWeatherDataCoverter implements JsonDeserializer<MainWeatherData>
{
    @Override
    public MainWeatherData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        final GsonBuilder builder = new GsonBuilder();

        MainWeatherData data = new MainWeatherData();
        Main main = new Main();
        Weather weather = new Weather();

        final JsonObject jsonObject = json.getAsJsonObject();
        main = context.deserialize(jsonObject.get("main"), Main.class);

        return data;
    }
}
