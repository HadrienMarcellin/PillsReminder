package com.example.pillsreminder.room.pain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.pillsreminder.room.converters.CalendarConverter;
import com.example.pillsreminder.room.converters.PainLevelsConverter;


import com.example.pillsreminder.helpers.PainLevels;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

@Entity(tableName = "painTable")
@TypeConverters({CalendarConverter.class, PainLevelsConverter.class})
public class Pain {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "level")
    private PainLevels painLevel;

    @NonNull
    @ColumnInfo(name = "inflammation")
    private boolean inflammation;

    @NonNull
    @ColumnInfo(name = "date")
    private Calendar date;

    @NonNull
    @ColumnInfo(name = "removed")
    private boolean removed;

    @NonNull
    @ColumnInfo(name = "server_synched")
    private boolean server_synched;

    public Pain(int id, @NotNull PainLevels painLevel, boolean inflammation, @NotNull Calendar date) {
        this.id = id;
        this.painLevel = painLevel;
        this.inflammation = inflammation;
        this.date = date;
        this.removed = false;
        this.server_synched = false;
    }

    @Ignore
    public Pain(PainLevels painLevel, boolean inflammation, Calendar calendar) {
        this.painLevel = painLevel;
        this.inflammation = inflammation;
        this.date = calendar;
        this.removed = false;
        this.server_synched = false;
    }

    @NotNull
    public Calendar getDate() {
        return date;
    }

    public void setDate(@NotNull Calendar date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public PainLevels getPainLevel() {
        return painLevel;
    }

    public void setLevel(@NotNull PainLevels painLevel) {
        this.painLevel = painLevel;
    }

    public boolean isInflammation() {
        return inflammation;
    }

    public void setInflammation(boolean inflammation) {
        this.inflammation = inflammation;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public boolean isServer_synched() {
        return server_synched;
    }

    public void setServer_synched(boolean server_synched) {
        this.server_synched = server_synched;
    }
}

