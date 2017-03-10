package ru.nway.myweather.servicies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import ru.nway.myweather.model.timezone.TimezoneData;

/**
 * Created by Klash on 27.02.2017.
 */

interface TimezoneAPI
{
    @GET
    Call<TimezoneData> getLocalTime(@Url String url,
                                    @Query("key") String key,
                                    @Query("format") String format,
                                    @Query("by") String by,
                                    @Query("lat") double lat,
                                    @Query("lng") double lng);
}
