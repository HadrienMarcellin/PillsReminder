package com.example.pillsreminder.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.pillsreminder.dao.PainDao;
import com.example.pillsreminder.entities.Pain;
import com.example.pillsreminder.helpers.PainLevels;

import org.jetbrains.annotations.NonNls;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Pain.class}, version = 2, exportSchema = true)
public abstract class PainDatabase extends RoomDatabase {


    private static volatile PainDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static PainDatabase getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PainDatabase.class, "PainDatabase").addMigrations(MIGRATION_1_2).build();
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

                Pain pain = new Pain(PainLevels.LOW, false, Calendar.getInstance());
                dao.insert(pain);
            });

        }
    };

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Create new table
            database.execSQL("CREATE TABLE painTable_new (id INTEGER NOT NULL, level INTEGER, inflammation INTEGER NOT NULL, date INTEGER, PRIMARY KEY (id))");
            // COpy the data
            database.execSQL("INSERT INTO painTable_new (id, level, inflammation, date) SELECT id, level, long_duration, date FROM painTable");
            // Remove the old table
            database.execSQL("DROP TABLE painTable");
            // Change the table name to the correct one
            database.execSQL("ALTER TABLE painTable_new RENAME TO painTable");
        }
    };

}
