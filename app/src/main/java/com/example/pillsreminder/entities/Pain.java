package com.example.pillsreminder.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.pillsreminder.entities.converters.CalendarConverter;
import com.example.pillsreminder.entities.converters.PainLevelsConverter;
import com.example.pillsreminder.helpers.CalendarHelpers;
import com.example.pillsreminder.helpers.PainLevels;

import java.util.Calendar;

@Entity(tableName = "painTable")
@TypeConverters({CalendarConverter.class, PainLevelsConverter.class})
public class Pain {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "level")
    private PainLevels painLevel;

    @ColumnInfo(name = "long_duration")
    private boolean long_duration;

    @ColumnInfo(name = "date")
    private Calendar date;

    public Pain(int id, PainLevels painLevel, boolean long_duration, Calendar date) {
        this.id = id;
        this.painLevel = painLevel;
        this.long_duration = long_duration;
        this.date = date;
    }

    @Ignore
    public Pain(PainLevels painLevel, boolean long_duration, Calendar calendar) {
        this.painLevel = painLevel;
        this.long_duration = long_duration;
        this.date = calendar;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PainLevels getPainLevel() {
        return painLevel;
    }

    public void setLevel(PainLevels painLevel) {
        this.painLevel = painLevel;
    }

    public boolean isLong_duration() {
        return long_duration;
    }

    public void setLong_duration(boolean long_duration) {
        this.long_duration = long_duration;
    }


}
