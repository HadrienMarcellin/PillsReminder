package com.example.pillsreminder.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.RoomDatabase;


@Entity(tableName = "painTable")
public class Pain {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "level")
    private String level;

    @ColumnInfo(name = "long_duration")
    private boolean long_duration;

    public Pain(int id, String level, boolean long_duration) {
        this.id = id;
        this.level = level;
        this.long_duration = long_duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean isLong_duration() {
        return long_duration;
    }

    public void setLong_duration(boolean long_duration) {
        this.long_duration = long_duration;
    }
}
