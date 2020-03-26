package com.example.pillsreminder.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pillsreminder.entities.Drug;

import java.util.List;

@Dao
public interface DrugDao {

    @Query("SELECT * FROM drugTable")
    LiveData<List<Drug>> getAllMedecines();

    @Query("SELECT * FROM drugTable WHERE id = :id LIMIT 1")
    Drug getMedecineFromId(long id);

    @Query("DELETE FROM drugTable ")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Drug drug);

    @Update
    void updateMedecine(Drug drug);

    @Delete
    void deleteMedecine(Drug drug);
}
