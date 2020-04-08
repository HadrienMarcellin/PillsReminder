package com.exemple.pillsreminder.activityType;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.exemple.pillsreminder.foodType.ActivityType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ActivityType.class}, version = 1, exportSchema = false)
public abstract class ActivityTypeDatabase extends RoomDatabase {

    private static volatile ActivityTypeDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ActivityTypeDatabase getInstance(Application application) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(application, ActivityTypeDatabase.class, "activityDatabse").build();
        return INSTANCE;
    }

    public void destroyInstance() {
        INSTANCE = null;
    }

    public abstract ActivityTypeDao activityDao();

}
