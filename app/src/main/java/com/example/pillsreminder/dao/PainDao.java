package com.example.pillsreminder.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pillsreminder.entities.Pain;

import java.util.List;

@Dao
public interface PainDao {
    @Query("SELECT * FROM painTable ORDER BY date DESC")
    LiveData<List<Pain>> getAllPains();

    @Query("SELECt * FROM painTable WHERE id = :id LIMIT 1")
    LiveData<List<Pain>> findPainById(long id);

    @Query("DELETE FROM painTable")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Pain pain);

    @Update
    void updatePain(Pain pain);

    @Delete
    void delete(Pain pain);
}
