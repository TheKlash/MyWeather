
package ru.nway.myweather.model.weather;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Alert implements Serializable
{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("time")
    @Expose
    private int time;
    @SerializedName("expires")
    @Expose
    private int expires;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("uri")
    @Expose
    private String uri;
    private final static long serialVersionUID = 1774938886182770543L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Alert() {
    }

    /**
     * 
     * @param time
     * @param title
     * @param expires
     * @param description
     * @param uri
     */
    public Alert(String title, int time, int expires, String description, String uri) {
        super();
        this.title = title;
        this.time = time;
        this.expires = expires;
        this.description = description;
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
