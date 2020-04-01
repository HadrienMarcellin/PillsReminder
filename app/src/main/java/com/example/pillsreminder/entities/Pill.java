package com.example.pillsreminder.entities;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import androidx.room.TypeConverters;

import com.example.pillsreminder.entities.converters.CalendarConverter;

import java.util.Calendar;
import java.util.List;

@Entity(tableName = "pillTable")
@TypeConverters({CalendarConverter.class})
public class Pill {

    @PrimaryKey(autoGenerate = true)
    private int pill_id;

    @ColumnInfo(name="drugType_id")
    private int drugType_id;

    @ColumnInfo(name = "date")
    private Calendar date;

    // Default Constructor
    public Pill(){
    }

    @Ignore
    public Pill(int pill_id, int drugType_id, Calendar date) {
        this.pill_id = pill_id;
        this.drugType_id = drugType_id;
        this.date = date;
    }

    @Ignore
    public Pill(int drugType_id, Calendar cal) {
        this.drugType_id = drugType_id;
        this.date = cal;
    }

    public int getPill_id() {
        return pill_id;
    }

    public void setPill_id(int pill_id) {
        this.pill_id = pill_id;
    }

    public int getDrugType_id() {
        return drugType_id;
    }

    public void setDrugType_id(int drugType_id) {
        this.drugType_id = drugType_id;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}