package ru.nway.myweather.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Klash on 25.02.2017.
 */

public class Simplified
{
    @SerializedName("temp")
    Double temp;

    @SerializedName("weather")
    String weather;

    public Simplified() {
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Simplified(Double temp, String weather) {

        this.temp = temp;
        this.weather = weather;
    }
}
