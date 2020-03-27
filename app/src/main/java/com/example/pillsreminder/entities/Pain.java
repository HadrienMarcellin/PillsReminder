package com.example.pillsreminder.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.pillsreminder.entities.converters.CalendarConverter;

import java.util.Calendar;

@Entity(tableName = "painTable")
@TypeConverters({CalendarConverter.class})
public class Pain {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "level")
    private int level;

    @ColumnInfo(name = "long_duration")
    private boolean long_duration;

    @ColumnInfo(name = "date")
    private Calendar date;

    public Pain(int id, int level, boolean long_duration, Calendar date) {
        this.id = id;
        this.level = level;
        this.long_duration = long_duration;
        this.date = date;
    }

    @Ignore
    public Pain(int level) {
        this.level = level;
        this.long_duration = false;
        this.date = Calendar.getInstance();
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isLong_duration() {
        return long_duration;
    }

    public void setLong_duration(boolean long_duration) {
        this.long_duration = long_duration;
    }
}
