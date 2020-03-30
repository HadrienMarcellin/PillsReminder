package com.example.pillsreminder.entities.converters;

import androidx.room.TypeConverter;

import com.example.pillsreminder.helpers.PainLevels;

public class PainLevelsConverter {

    @TypeConverter
    public static PainLevels intToPainLevel(int level) {
        return PainLevels.values()[level];
    }

    @TypeConverter
    public static int painLevelsToInt(PainLevels painLevel) {
        return painLevel.getLevel();
    }
}
