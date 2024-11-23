package com.example.atlas.models.details;

import com.google.gson.annotations.SerializedName;

/**
 * Clase que representa una moneda y sus atributos
 */
public class Currency {

    @SerializedName("name")
    private String name;

    @SerializedName("symbol")
    private String symbol;

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
}
