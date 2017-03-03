package ru.nway.myweather.model.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Klash on 03.03.2017.
 */

public class Coord implements Serializable
{
    @SerializedName("lon")
    @Expose
    private String lon;

    @SerializedName("lat")
    @Expose
    private String lat;

    public Coord()
    {

    }

    public Coord(String lon, String lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
