package com.example.pillsreminder.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pillsreminder.entities.Activity;

import java.util.List;

@Dao
public interface ActivityDao {

    @Query("SELECT * FROM  pillTable")
    LiveData<List<Activity>> getAllPillEntities();

    @Query("SELECt * FROM pillTable WHERE id = :id LIMIT 1")
    LiveData<List<Activity>> findPillEntityById(long id);

    @Query("SELECT * FROM pillTable WHERE drug = :drug LIMIT 1")
    LiveData<List<Activity>> findPillEntityByTitle(String drug);

    @Query("DELETE FROM pillTable")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Activity activity);

    @Update
    void updatePillEntities(Activity activity);

    @Delete
    void delete(Activity activity);
}
