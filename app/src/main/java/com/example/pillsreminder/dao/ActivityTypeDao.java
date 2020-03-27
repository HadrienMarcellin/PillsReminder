package com.example.pillsreminder.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pillsreminder.entities.ActivityType;

import java.util.List;

@Dao
public interface ActivityTypeDao {

    @Query("SELECT * FROM activityTypeTable")
    LiveData<List<ActivityType>> getAllActivityTypes();

    @Query("SELECt * FROM activityTypeTable WHERE id = :id LIMIT 1")
    LiveData<List<ActivityType>> findPillEntityById(long id);

    @Query("SELECT * FROM activityTypeTable WHERE name = :name LIMIT 1")
    LiveData<List<ActivityType>> findActivityTypeByTitle(String name);

    @Query("DELETE FROM activityTypeTable")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ActivityType activityType);

    @Update
    void updatePillEntities(ActivityType activityType);

    @Delete
    void delete(ActivityType activityType);
}
