package com.example.pillsreminder.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.pillsreminder.entities.converters.CalendarConverter;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "pillTable")
@TypeConverters({CalendarConverter.class})
public class Pill {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="drug")
    private String drug;

    @ColumnInfo(name = "date")
    private Calendar date;

    // Default Constructor
    public Pill(){
    }

    @Ignore
    public Pill(int id, String drug, Calendar date) {
        this.id = id;
        this.drug = drug;
        this.date = date;
    }

    @Ignore
    public Pill(String drug) {
        this.drug = drug;
        this.date = Calendar.getInstance();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
