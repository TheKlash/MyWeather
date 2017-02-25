
package ru.nway.myweather.entity;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather implements Serializable
{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("icon")
    @Expose
    private String icon;
    private final static long serialVersionUID = -8413275473414946773L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Weather() {
    }

    /**
     * 
     * @param id
     * @param icon
     * @param description
     * @param main
     */
    public Weather(int id, String main, String description, String icon) {
        super();
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Weather withId(int id) {
        this.id = id;
        return this;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public Weather withMain(String main) {
        this.main = main;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Weather withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Weather withIcon(String icon) {
        this.icon = icon;
        return this;
    }

}
