package com.example.pillsreminder.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pillsreminder.dao.ActivityDao;
import com.example.pillsreminder.entities.Activity;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Activity.class}, version = 1, exportSchema = false)
public abstract class ActivityDatabase extends RoomDatabase {

    private static volatile ActivityDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ActivityDatabase getInstance(Application application) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(application, ActivityDatabase.class, "activityDatabse").build();
        return INSTANCE;
    }

    public void destroyInstance() {
        INSTANCE = null;
    }

    public abstract ActivityDao activityDao();

}
