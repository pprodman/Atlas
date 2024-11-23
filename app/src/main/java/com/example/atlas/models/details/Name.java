package com.example.atlas.models.details;

import com.google.gson.annotations.SerializedName;

/**
 * Clase que representa el nombre de un pais
 */
public class Name {

    @SerializedName("common")
    private String common;

    @SerializedName("official")
    private String official;

    public String getCommon() {
        return common;
    }

    public String getOfficial() {
        return official;
    }

}
