package com.example.atlas.services;

import com.example.atlas.models.Pais;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Interfaz que representa la API de Atlas
 */
public interface AtlasAPI {

    String BASE_URL = "https://restcountries.com/v3.1/";

    @GET("all?fields=name,flags,translations,capital,region,subregion,borders,area,population,timezones,latlng,tld,currencies,languages")
    Call<List<Pais>> getPaises();

    @GET("name/{name}")
    Call<List<Pais>> getPaisByName(@Path("name") String name);

}
