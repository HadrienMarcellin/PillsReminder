package com.example.pillsreminder.helpers;

import android.support.v4.media.session.ParcelableVolumeInfo;

import java.util.ArrayList;
import java.util.List;

public enum PainLevels {

    UNDIFINED(-1, "Undefined", "Indefini"),
    NONE(0, "None", "Aucune"),
    LOW(1, "Low", "Faible"),
    MEDIUM(2, "Medium", "Intermédiaire"),
    HIGH(3, "High", "Haute"),
    INTENSE(4, "Intense", "Intense"),
    FATAL(5, "Fatal", "Critique");

    private int level;
    private String englishName;
    private String frenchName;

    //Constructor


    PainLevels(int level, String englishName, String frenchName) {
        this.level = level;
        this.englishName = englishName;
        this.frenchName = frenchName;
    }
    public int getLevel() {
        return level;
    }

    public String getFrenchName() {
        return frenchName;
    }

    public String getEnglishName() {
        return englishName;
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
            painLevels.add(p.englishName);
        }
        return painLevels;
    }
}
