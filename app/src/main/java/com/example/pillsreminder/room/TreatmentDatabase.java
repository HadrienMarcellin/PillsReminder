package com.example.pillsreminder.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.pillsreminder.room.drug.Drug;
import com.example.pillsreminder.room.drug.DrugDao;
import com.example.pillsreminder.room.pain.Pain;
import com.example.pillsreminder.room.pain.PainDao;
import com.example.pillsreminder.room.pill.Pill;
import com.example.pillsreminder.room.pill.PillDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

@Database(entities = {Drug.class, Pill.class, Pain.class}, version = 1, exportSchema = true)
public abstract class TreatmentDatabase extends RoomDatabase {

    private static Logger LOGGER = Logger.getLogger(TreatmentDatabase.class.getName());
    private static volatile TreatmentDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TreatmentDatabase getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    TreatmentDatabase.class, "Treatment_db")
                    .addCallback(sRoomDatabseCallback)
                    .build();
        return INSTANCE;
    }

    public void destroyInstance() {
        INSTANCE = null;
    }

    public abstract PainDao painDao();
    public abstract PillDao pillDao();
    public abstract DrugDao drugDao();


    /**
     * Database callbacks functions override.
     */
    private static RoomDatabase.Callback sRoomDatabseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriterExecutor.execute(()-> {
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriterExecutor.execute(()-> {
                if (db.query("SELECT * FROM drugTable").getCount() == 0) {
                    LOGGER.log(Level.INFO, "Fill drug database at opening.");
                    INSTANCE.fillDrugTable(db);
                }
            });
        }
    };

    private void fillDrugTable(SupportSQLiteDatabase db) {
        DrugDao dao = INSTANCE.drugDao();
        Drug drug_undefined = new Drug("Undefined", "Undefined", "Undefined", 0, "Undefined", "Undefined");
        Drug drug = new Drug("Antalnox", "Anti-inflamatoire", "Type non-stéroïdien", 3, "Comprimé a avaler pendant les repas.", "Trouble de la digestion.");
        dao.insert(drug_undefined);
        dao.insert(drug);
    }


    /**
     * Database migration protocol.
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            INSTANCE.migrateDrugTable(1, database);
            INSTANCE.migratePainTable(1, database);
            INSTANCE.migratePillTable(1, database);
        }
    };

    private void migrateDrugTable(int table_version, SupportSQLiteDatabase db) {

        switch (table_version) {
           default:
                db.execSQL("CREATE TABLE drugTable_new (" +
                        "id INTEGER PRIMARY KEY NOT NULL, "+
                        "name TEXT, " +
                        "type TEXT, " +
                        "sub_type TEXT, " +
                        "times_per_day INTEGER NOT NULL, " +
                        "description TEXT, " +
                        "side_effects TEXT" +
                        ")");
                // COpy the data
                db.execSQL("INSERT INTO drugTable_new (id, name, type, sub_type, times_per_day, description, side_effects) SELECT id, name, type, sub_type, times_per_day, description, side_effects FROM drugTable");
                // Remove the old table
                db.execSQL("DROP TABLE drugTable");
                // Change the table name to the correct one
                db.execSQL("ALTER TABLE drugTable_new RENAME TO drugTable");
                break;
        }
    }

    private void migratePainTable(int table_version, SupportSQLiteDatabase db) {

        switch (table_version) {
            default:
                db.execSQL("CREATE TABLE painTable_new (" +
                            "id INTEGER PRIMARY KEY NOT NULL, " +
                            "level INTEGER NOT NULL, " +
                            "inflammation INTEGER NOT NULL, " +
                            "date INTEGER NOT NULL, " +
                            "removed INTEGER NOT NULL, " +
                            "server_synched INTEGER NOT NULL)");
                // COpy the data
                db.execSQL("INSERT INTO painTable_new (id, level, inflammation, date, removed, server_synched) SELECT id, level, inflammation, date, removed, server_synched FROM painTable");
                // Remove the old table
                db.execSQL("DROP TABLE painTable");
                // Change the table name to the correct one
                db.execSQL("ALTER TABLE painTable_new RENAME TO painTable");
                break;
        }
    }

    private void migratePillTable(int table_version, SupportSQLiteDatabase db) {

        switch (table_version) {
            default:
                db.execSQL("CREATE TABLE pillTable_new (" +
                        "id INTEGER PRIMARY KEY NOT NULL, "+
                        "drugType_id INTEGER NOT NULL, " +
                        "quantity INTEGER NOT NULL, " +
                        "date INTEGER NOT NULL, " +
                        "removed INTEGER NOT NULL, " +
                        "server_synched INTEGER NOT NULL, " +
                        "FOREIGN KEY(drugType_id) REFERENCES drugTable(id) ON DELETE NO ACTION)");
                // Create index on drugType_id
                db.execSQL("CREATE INDEX index_pillTable_drugType_id ON pillTable_new(drugType_id)");
                // COpy the data
                db.execSQL("INSERT INTO pillTable_new (id, drugType_id, quantity, date, removed, server_synched) SELECT id, drugType_id, quantity, date, removed, server_synched FROM pillTable");
                // Remove the old table
                db.execSQL("DROP TABLE pillTable");
                // Change the table name to the correct one
                db.execSQL("ALTER TABLE pillTable_new RENAME TO pillTable");
                break;
        }
    }

}
