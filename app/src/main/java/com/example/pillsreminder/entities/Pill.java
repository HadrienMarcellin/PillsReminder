package com.example.pillsreminder.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "pillTable")
public class Pill {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="drug")
    private String drug;

    // Default Constructor
    public Pill(){
    }

    @Ignore
    public Pill(String drug) {
        this.drug = drug;
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
}
