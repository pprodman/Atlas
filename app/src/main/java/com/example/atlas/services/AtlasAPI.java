package com.example.atlas.services;

import com.example.atlas.models.Pais;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AtlasAPI {

    String BASE_URL = "https://restcountries.com/v3.1/";

    @GET("all?fields=name,flags")
    Call<List<Pais>> getPaises();

    @GET("name/{name}")
    Call<List<Pais>> getPaisByName(@Path("name") String name);

}
