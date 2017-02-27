
package ru.nway.myweather.model.timezone;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimezoneData implements Serializable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @SerializedName("countryName")
    @Expose
    private String countryName;
    @SerializedName("zoneName")
    @Expose
    private String zoneName;
    @SerializedName("abbreviation")
    @Expose
    private String abbreviation;
    @SerializedName("gmtOffset")
    @Expose
    private int gmtOffset;
    @SerializedName("dst")
    @Expose
    private String dst;
    @SerializedName("dstStart")
    @Expose
    private int dstStart;
    @SerializedName("dstEnd")
    @Expose
    private int dstEnd;
    @SerializedName("nextAbbreviation")
    @Expose
    private String nextAbbreviation;
    @SerializedName("timestamp")
    @Expose
    private int timestamp;
    @SerializedName("formatted")
    @Expose
    private String formatted;
    private final static long serialVersionUID = 3565843479098943747L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TimezoneData() {
    }

    /**
     * 
     * @param timestamp
     * @param message
     * @param formatted
     * @param countryName
     * @param status
     * @param nextAbbreviation
     * @param countryCode
     * @param gmtOffset
     * @param dstEnd
     * @param dst
     * @param abbreviation
     * @param dstStart
     * @param zoneName
     */
    public TimezoneData(String status, String message, String countryCode, String countryName, String zoneName, String abbreviation, int gmtOffset, String dst, int dstStart, int dstEnd, String nextAbbreviation, int timestamp, String formatted) {
        super();
        this.status = status;
        this.message = message;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.zoneName = zoneName;
        this.abbreviation = abbreviation;
        this.gmtOffset = gmtOffset;
        this.dst = dst;
        this.dstStart = dstStart;
        this.dstEnd = dstEnd;
        this.nextAbbreviation = nextAbbreviation;
        this.timestamp = timestamp;
        this.formatted = formatted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public int getGmtOffset() {
        return gmtOffset;
    }

    public void setGmtOffset(int gmtOffset) {
        this.gmtOffset = gmtOffset;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public int getDstStart() {
        return dstStart;
    }

    public void setDstStart(int dstStart) {
        this.dstStart = dstStart;
    }

    public int getDstEnd() {
        return dstEnd;
    }

    public void setDstEnd(int dstEnd) {
        this.dstEnd = dstEnd;
    }

    public String getNextAbbreviation() {
        return nextAbbreviation;
    }

    public void setNextAbbreviation(String nextAbbreviation) {
        this.nextAbbreviation = nextAbbreviation;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

}
