package com.example.atlas.models;

import com.example.atlas.models.details.Currency;
import com.example.atlas.models.details.Flags;
import com.example.atlas.models.details.Name;
import com.example.atlas.models.details.Translations;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Clase que representa un país y sus atributos
 * @author: Pablo Rodriguez Manrique
 * @version: 1.0 09/12/2024
 */
public class Pais {

    @SerializedName("name")
    private Name name;

    @SerializedName("translations")
    private Translations translations;

    @SerializedName("flags")
    private Flags flags;

    @SerializedName("population")
    private String population;

    @SerializedName("region")
    private String region;

    @SerializedName("subregion")
    private String subregion;

    @SerializedName("capital")
    private List<String> capital;

    @SerializedName("tld")
    private List<String> tld;

    @SerializedName("area")
    private String area;

    @SerializedName("languages")
    private Map<String, String> languages;

    @SerializedName("latlng")
    private List<Double> latlng;

    @SerializedName("timezones")
    private List<String> timezones;

    @SerializedName("borders")
    private List<String> borders;

    @SerializedName("currencies")
    private Map<String, Currency> currencies;



    public Name getName() {
        return name;
    }

    public Flags getFlags() {
        return flags;
    }

    public Translations getTranslations() {
        return translations;
    }

    public String getPopulation() {
        return population;
    }

    public String getRegion() {
        return region;
    }

    public String getSubregion() {
        return subregion;
    }

    public List<String> getCapital() {
        return capital;
    }

    public List<String> getTld() {
        return tld;
    }

    public String getArea() {
        return area;
    }

    public List<String> getBorders() {
        return borders;
    }

    public List<Double> getLatlng() {
        return latlng;
    }

    public List<String> getTimezones() {
        return timezones;
    }

    public Map<String, Currency> getCurrencies() {
        return currencies;
    }

    public Map<String, String> getLanguages() {
        return languages;
    }


}
