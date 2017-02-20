package ru.nway.myweather.entity;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Rain implements Serializable
{

    @SerializedName("3h")
    @Expose
    private Integer _3h;
    private final static long serialVersionUID = 4114708547081063885L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Rain() {
    }

    /**
     *
     * @param _3h
     */
    public Rain(Integer _3h) {
        super();
        this._3h = _3h;
    }

    public Integer get3h() {
        return _3h;
    }

    public void set3h(Integer _3h) {
        this._3h = _3h;
    }

}

