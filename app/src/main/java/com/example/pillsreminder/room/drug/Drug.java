package com.example.pillsreminder.room.drug;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "drugTable")
public class Drug {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "sub_type")
    private String sub_type;

    @ColumnInfo(name = "times_per_day")
    private int times_per_day;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "side_effects")
    private String side_effects;

    public Drug(int id, String name, String type, String sub_type, int times_per_day, String description, String side_effects) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.sub_type = sub_type;
        this.times_per_day = times_per_day;
        this.description = description;
        this.side_effects = side_effects;
    }

    @Ignore
    public Drug(String name, String type, String sub_type, int times_per_day, String description, String side_effects) {
        this.name = name;
        this.type = type;
        this.sub_type = sub_type;
        this.times_per_day = times_per_day;
        this.description = description;
        this.side_effects = side_effects;
    }

    /// Getters


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSub_type() {
        return sub_type;
    }

    public int getTimes_per_day() {
        return times_per_day;
    }

    public String getDescription() {
        return description;
    }

    public String getSide_effects() {
        return side_effects;
    }

    /// Setters
    public void setSide_effects(String side_effects) {
        this.side_effects = side_effects;
    }

    public void setId(int drug_id) {
        this.id = drug_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public void setTimes_per_day(int times_per_day) {
        this.times_per_day = times_per_day;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
