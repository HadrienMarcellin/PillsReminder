package com.exemple.pillsreminder.pill;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Pill.class}, version = 1, exportSchema = false)
public abstract class PillDatabase extends RoomDatabase {

    private static volatile PillDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static PillDatabase getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PillDatabase.class, "PillDataBase").build();
        return INSTANCE;
    }

    public void destroyInstance() {
        INSTANCE = null;
    }

    public abstract PillDao pillDao();


    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(()-> {
                PillDao dao = INSTANCE.pillDao();
                dao.deleteAll();

                Pill pill1 = new Pill(1, Calendar.getInstance(), 0);
                Pill pill2 = new Pill(2, Calendar.getInstance(), 0);
                Pill pill3 = new Pill(3, Calendar.getInstance(), 0);
                Pill pill4 = new Pill(4, Calendar.getInstance(), 0);
                dao.insert(pill1);
                dao.insert(pill2);
                dao.insert(pill3);
                dao.insert(pill4);
            });
        }
    };
}
