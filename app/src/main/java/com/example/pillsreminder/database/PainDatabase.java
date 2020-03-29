package com.example.pillsreminder.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.pillsreminder.dao.PainDao;
import com.example.pillsreminder.entities.Pain;

import org.jetbrains.annotations.NonNls;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Pain.class}, version = 1, exportSchema = false)
public abstract class PainDatabase extends RoomDatabase {


    private static volatile PainDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static PainDatabase getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PainDatabase.class, "PainDatabase").addCallback(sRoomDatabseCallback).build();
        return INSTANCE;
    }

    public void destroyInstance() { INSTANCE = null; }

    public abstract PainDao painDao();

    private static RoomDatabase.Callback sRoomDatabseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriterExecutor.execute(()-> {
                PainDao dao = INSTANCE.painDao();
                dao.deleteAll();

                Pain pain = new Pain(1);
                dao.insert(pain);
            });

        }
    };

}
