package com.example.pillsreminder.helpers;

import java.lang.reflect.Array;

public enum PainLevel {

    NONE(0, "Lone"),
    LOW(1, "Low"),
    MEDIUM(2, "Medium"),
    HIGH(3, "High"),
    INTENSE(4, "Intense"),
    FATAL(5, "Fatal");

    private int level;
    private String name;

    //Constructor
    PainLevel(int level, String name) {
        this.level = level;
        this.name = name;
    }

    public String[] getAllPainLevels() {
        String[] levels = {"None", "Low", "Medium", "High", "Intense", "Fatal"};
        return levels;
    }
}
