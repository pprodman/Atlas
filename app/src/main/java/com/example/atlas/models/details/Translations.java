package com.example.atlas.models.details;

import com.google.gson.annotations.SerializedName;

/**
 * Clase que representa las Traducciones de los nombres de un pais de Ingles a Español
 */
public class Translations {

    @SerializedName("spa")
    private Spa spa;

    public Spa getSpa() {
        return spa;
    }

    public static class Spa {
        @SerializedName("official")
        private String official;

        @SerializedName("common")
        private String common;

        public String getOfficial() {
            return official;
        }

        public String getCommon() {
            return common;
        }
    }
}


