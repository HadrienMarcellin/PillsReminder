package com.example.pillsreminder.room.drug;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DrugDao {

    @Query("SELECT * FROM drugTable ORDER BY name DESC")
    LiveData<List<Drug>> getAllDrugs();

    @Query("SELECT * FROM drugTable WHERE id = :id LIMIT 1")
    Drug selectDrugFromId(int id);

    @Query("SELECT * FROM drugTable WHERE name=:name LIMIT 1")
    Drug selectDrugFromName(String name);

    @Query("DELETE FROM drugTable ")
    void deleteAll();

    @Insert
    void insert(Drug... drug);

    @Update
    void updateDrug(Drug... drug);

    @Delete
    void deleteDrug(Drug... drug);
}
