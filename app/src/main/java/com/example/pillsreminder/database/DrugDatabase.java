package com.example.pillsreminder.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.pillsreminder.dao.DrugDao;
import com.example.pillsreminder.entities.Drug;
import com.example.pillsreminder.entities.Pill;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Drug.class, Pill.class}, version = 1, exportSchema = false)
public abstract class DrugDatabase extends RoomDatabase {

    private static volatile DrugDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static DrugDatabase getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DrugDatabase.class, "DrugDatabase").addCallback(sRoomDatabseCallback).build();
        return INSTANCE;
    }

    public void destroyInstance() {
        INSTANCE = null;
    }

    public abstract DrugDao drugDao();


    private static RoomDatabase.Callback sRoomDatabseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriterExecutor.execute(()-> {
                DrugDao dao = INSTANCE.drugDao();
                dao.deleteAll();

                Drug pain = new Drug("Antalnox", "Anti-inflamatoire", "Type non-stéroïdien", 3, "Comprimé a avaler pendant les repas.", "Trouble de la digestion.");
                dao.insert(pain);
            });

        }
    };
}
