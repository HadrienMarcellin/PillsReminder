package com.example.pillsreminder.room.converters;

import androidx.room.TypeConverter;

import com.example.pillsreminder.helpers.PainLevels;

import java.util.Arrays;

public class PainLevelsConverter {

    @TypeConverter
    public static PainLevels intToPainLevel(int level) {
        return Arrays.stream(PainLevels.values()).filter(d -> d.getLevel() == level).findAny().orElse(PainLevels.UNDIFINED);
    }

    @TypeConverter
    public static int painLevelsToInt(PainLevels painLevel) {
        return painLevel.getLevel();
    }
}
