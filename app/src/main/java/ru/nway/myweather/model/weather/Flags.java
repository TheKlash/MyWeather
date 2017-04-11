
package ru.nway.myweather.model.weather;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Flags implements Serializable
{

    @SerializedName("sources")
    @Expose
    private List<String> sources = null;
    @SerializedName("lamp-stations")
    @Expose
    private List<String> lampStations = null;
    @SerializedName("isd-stations")
    @Expose
    private List<String> isdStations = null;
    @SerializedName("madis-stations")
    @Expose
    private List<String> madisStations = null;
    @SerializedName("units")
    @Expose
    private String units;
    private final static long serialVersionUID = 3132433079660342799L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Flags() {
    }

    /**
     * 
     * @param isdStations
     * @param lampStations
     * @param units
     * @param sources
     * @param madisStations
     */
    public Flags(List<String> sources, List<String> lampStations, List<String> isdStations, List<String> madisStations, String units) {
        super();
        this.sources = sources;
        this.lampStations = lampStations;
        this.isdStations = isdStations;
        this.madisStations = madisStations;
        this.units = units;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public List<String> getLampStations() {
        return lampStations;
    }

    public void setLampStations(List<String> lampStations) {
        this.lampStations = lampStations;
    }

    public List<String> getIsdStations() {
        return isdStations;
    }

    public void setIsdStations(List<String> isdStations) {
        this.isdStations = isdStations;
    }

    public List<String> getMadisStations() {
        return madisStations;
    }

    public void setMadisStations(List<String> madisStations) {
        this.madisStations = madisStations;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

}
