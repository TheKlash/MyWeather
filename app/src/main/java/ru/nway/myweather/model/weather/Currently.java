
package ru.nway.myweather.model.weather;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Currently implements Serializable
{

    @SerializedName("time")
    @Expose
    private int time;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("nearestStormDistance")
    @Expose
    private int nearestStormDistance;
    @SerializedName("precipIntensity")
    @Expose
    private double precipIntensity;
    @SerializedName("precipIntensityError")
    @Expose
    private double precipIntensityError;
    @SerializedName("precipProbability")
    @Expose
    private double precipProbability;
    @SerializedName("precipType")
    @Expose
    private String precipType;
    @SerializedName("temperature")
    @Expose
    private double temperature;
    @SerializedName("apparentTemperature")
    @Expose
    private double apparentTemperature;
    @SerializedName("dewPoint")
    @Expose
    private double dewPoint;
    @SerializedName("humidity")
    @Expose
    private double humidity;
    @SerializedName("windSpeed")
    @Expose
    private double windSpeed;
    @SerializedName("windBearing")
    @Expose
    private int windBearing;
    @SerializedName("visibility")
    @Expose
    private double visibility;
    @SerializedName("cloudCover")
    @Expose
    private double cloudCover;
    @SerializedName("pressure")
    @Expose
    private double pressure;
    @SerializedName("ozone")
    @Expose
    private double ozone;
    private final static long serialVersionUID = -3541716048363562757L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Currently() {
    }

    /**
     * 
     * @param summary
     * @param icon
     * @param pressure
     * @param cloudCover
     * @param visibility
     * @param apparentTemperature
     * @param precipType
     * @param precipIntensity
     * @param temperature
     * @param dewPoint
     * @param ozone
     * @param windSpeed
     * @param time
     * @param humidity
     * @param windBearing
     * @param nearestStormDistance
     * @param precipProbability
     * @param precipIntensityError
     */
    public Currently(int time, String summary, String icon, int nearestStormDistance, double precipIntensity, double precipIntensityError, int precipProbability, String precipType, double temperature, double apparentTemperature, double dewPoint, double humidity, double windSpeed, int windBearing, double visibility, double cloudCover, double pressure, double ozone) {
        super();
        this.time = time;
        this.summary = summary;
        this.icon = icon;
        this.nearestStormDistance = nearestStormDistance;
        this.precipIntensity = precipIntensity;
        this.precipIntensityError = precipIntensityError;
        this.precipProbability = precipProbability;
        this.precipType = precipType;
        this.temperature = temperature;
        this.apparentTemperature = apparentTemperature;
        this.dewPoint = dewPoint;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windBearing = windBearing;
        this.visibility = visibility;
        this.cloudCover = cloudCover;
        this.pressure = pressure;
        this.ozone = ozone;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getNearestStormDistance() {
        return nearestStormDistance;
    }

    public void setNearestStormDistance(int nearestStormDistance) {
        this.nearestStormDistance = nearestStormDistance;
    }

    public double getPrecipIntensity() {
        return precipIntensity;
    }

    public void setPrecipIntensity(double precipIntensity) {
        this.precipIntensity = precipIntensity;
    }

    public double getPrecipIntensityError() {
        return precipIntensityError;
    }

    public void setPrecipIntensityError(double precipIntensityError) {
        this.precipIntensityError = precipIntensityError;
    }

    public double getPrecipProbability() {
        return precipProbability;
    }

    public void setPrecipProbability(double precipProbability) {
        this.precipProbability = precipProbability;
    }

    public String getPrecipType() {
        return precipType;
    }

    public void setPrecipType(String precipType) {
        this.precipType = precipType;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(double apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    public double getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(double dewPoint) {
        this.dewPoint = dewPoint;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindBearing() {
        return windBearing;
    }

    public void setWindBearing(int windBearing) {
        this.windBearing = windBearing;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(double cloudCover) {
        this.cloudCover = cloudCover;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getOzone() {
        return ozone;
    }

    public void setOzone(double ozone) {
        this.ozone = ozone;
    }

}
