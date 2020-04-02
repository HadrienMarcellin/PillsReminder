package com.example.pillsreminder.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pillsreminder.entities.Pill;

import java.util.List;

@Dao
public interface PillDao {

    @Query("SELECT * FROM  pillTable ORDER BY date DESC")
    LiveData<List<Pill>> getAllPillEntities();

    @Query("SELECt * FROM pillTable WHERE pill_id = :id LIMIT 1")
    LiveData<List<Pill>> findPillEntityById(int id);

    @Query("SELECT * FROM pillTable WHERE drugType_id = :drug LIMIT 1")
    LiveData<List<Pill>> findPillEntityByTitle(int drug);

    @Query("DELETE FROM pillTable")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Pill pill);

    @Update
    void updatePillEntities(Pill pill);

    @Delete
    void delete(Pill pill);
}
