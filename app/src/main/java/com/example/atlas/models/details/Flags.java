package com.example.atlas.models.details;

import com.google.gson.annotations.SerializedName;

/**
 * Clase que representa la bandera de un pais
 */
public class Flags {

    @SerializedName("png")
    private String png;

    public String getPng() {
        return png;
    }
}
