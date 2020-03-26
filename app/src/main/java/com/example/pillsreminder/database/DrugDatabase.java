package com.example.pillsreminder.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pillsreminder.dao.DrugDao;
import com.example.pillsreminder.entities.Drug;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Drug.class}, version = 1, exportSchema = false)
public abstract class DrugDatabase extends RoomDatabase {

    private static volatile DrugDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static DrugDatabase getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DrugDatabase.class, "MedecineDatabse").build();
        return INSTANCE;
    }

    public void destroyInstance() {
        INSTANCE = null;
    }

    public abstract DrugDao medecineDao();
}
