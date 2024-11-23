package com.example.atlas.models;

import com.google.gson.annotations.SerializedName;


public class Pais {
    @SerializedName("name")
    private Name name;

    @SerializedName("flags")
    private Flags flags;

    public Name getName() {
        return name;
    }

    public Flags getFlags() {
        return flags;
    }

    public static class Name {
        @SerializedName("common")
        private String common;

        public String getCommon() {
            return common;
        }
    }

    public static class Flags {
        @SerializedName("png")
        private String png;

        public String getPng() {
            return png;
        }
    }



}
