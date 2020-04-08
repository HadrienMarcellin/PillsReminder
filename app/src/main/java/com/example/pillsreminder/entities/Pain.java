package com.example.pillsreminder.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

    @ColumnInfo(name = "inflammation")
    private boolean inflammation;

    @ColumnInfo(name = "date")
    private Calendar date;

    public Pain(int id, PainLevels painLevel, boolean inflammation, Calendar date) {
        this.id = id;
        this.painLevel = painLevel;
        this.inflammation = inflammation;
        this.date = date;
    }

    @Ignore
    public Pain(PainLevels painLevel, boolean inflammation, Calendar calendar) {
        this.painLevel = painLevel;
        this.inflammation = inflammation;
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

    public boolean isInflammation() {
        return inflammation;
    }

    public void setInflammation(boolean inflammation) {
        this.inflammation = inflammation;
    }


}

