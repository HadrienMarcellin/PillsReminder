package com.example.pillsreminder.room.pill;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.pillsreminder.room.converters.CalendarConverter;
import com.example.pillsreminder.room.drug.Drug;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import static androidx.room.ForeignKey.NO_ACTION;

@Entity(tableName = "pillTable",
        foreignKeys = @ForeignKey(entity = Drug.class,
                                parentColumns = "id",
                                childColumns = "drugType_id",
                                onDelete = NO_ACTION))

@TypeConverters({CalendarConverter.class})
public class Pill {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="drugType_id", index = true)
    private int drugType_id;

    @NonNull
    @ColumnInfo(name = "date")
    private Calendar date;

    @ColumnInfo(name = "quantity")
    private int quantity;

    @ColumnInfo(name = "removed")
    private boolean removed;

    @ColumnInfo(name = "server_synched")
    private boolean server_synched;

    // Default Constructor
    public Pill(){
    }

    @Ignore
    public Pill(int drugType_id, @NotNull Calendar date, int quantity) {
        this.drugType_id = drugType_id;
        this.date = date;
        this.quantity = quantity;
        this.removed = false;
        this.server_synched = false;
    }

//    @Ignore
//    public Pill(int drugType_id, Calendar cal) {
//        this.drugType_id = drugType_id;
//        this.date = cal;
//    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrugType_id() {
        return drugType_id;
    }

    public void setDrugType_id(int drugType_id) {
        this.drugType_id = drugType_id;
    }

    @NotNull
    public Calendar getDate() {
        return date;
    }

    public void setDate(@NotNull Calendar date) {
        this.date = date;
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