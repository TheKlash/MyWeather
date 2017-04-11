package ru.nway.myweather.ui;

/**
 * Created by Klash on 09.03.2017.
 */

interface FragmentCallback {
    void fragmentCallback(int RequestCode);

    void fragmentCallback(int RequestCode, String city);

    void fragmentCallback(int RequestCode, String city, double lat, double lon);
}