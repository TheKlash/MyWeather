package ru.nway.myweather.converters;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import ru.nway.myweather.entity.MainWeatherData;

/**
 * Created by Klash on 22.02.2017.
 */

public class MainWeatherDataCoverter implements JsonDeserializer<MainWeatherData>
{
    @Override
    public MainWeatherData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        final JsonObject jsonObject = json.getAsJsonObject();
        JsonArray weather = jsonObject.get("weather").getAsJsonArray();
        JsonArray main = jsonObject.get("main").getAsJsonArray();

        return null;
    }
}
