package com.example.pillsreminder.room.drug;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.pillsreminder.room.relations.DrugWithPills;

import java.util.List;

@Dao
public interface DrugDao {

    @Transaction
    @Query("SELECT * FROM drugTable")
    LiveData<List<DrugWithPills>> getDrugsWithPills();

    @Transaction
    @Query("SELECT * FROM drugTable WHERE drug_id= :drug_id")
    DrugWithPills getAllPillsFromDrugID(int drug_id);

    @Query("SELECT * FROM drugTable ORDER BY name DESC")
    LiveData<List<Drug>> getAllDrugs();

    @Query("SELECT * FROM drugTable WHERE drug_id = :id LIMIT 1")
    Drug selectDrugFromId(int id);

    @Query("SELECT * FROM drugTable WHERE name=:name LIMIT 1")
    Drug selectDrugFromName(String name);

    @Query("DELETE FROM drugTable ")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Drug drug);

    @Update
    void updateDrug(Drug drug);

    @Delete
    void deleteDrug(Drug drug);
}
