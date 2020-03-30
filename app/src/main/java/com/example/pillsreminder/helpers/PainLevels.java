package com.example.pillsreminder.helpers;

import android.support.v4.media.session.ParcelableVolumeInfo;

import java.util.ArrayList;
import java.util.List;

public enum PainLevels {

    UNDIFINED(-1, "Undefined"),
    NONE(0, "None"),
    LOW(1, "Low"),
    MEDIUM(2, "Medium"),
    HIGH(3, "High"),
    INTENSE(4, "Intense"),
    FATAL(5, "Fatal");

    private int level;
    private String name;

    //Constructor
    PainLevels(int level, String name) {
        this.level = level;
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public PainLevels next() {
        if (ordinal() == values().length)
            return values()[ordinal()];
        else
            return values()[ordinal() + 1];
    }

    public PainLevels previous() {
        if (ordinal() == 0)
            return values()[ordinal()];
        else
            return values()[ordinal() - 1];
    }


    public static List<String> getAllPainLevels() {
        List<String> painLevels = new ArrayList<>();
        for (PainLevels p : PainLevels.values()) {
            painLevels.add(p.name);
        }
        return painLevels;
    }
}
