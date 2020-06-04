package com.example.pillsreminder.room.pill;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.pillsreminder.room.TreatmentDatabase;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PillRepository {

    private PillDao pillDao;
    private LiveData<List<Pill>> pillEntities;
    private static final Logger LOGGER = Logger.getLogger(PillRepository.class.getName());

    public PillRepository(Application application) {
        TreatmentDatabase treatmentDatabase = TreatmentDatabase.getInstance(application);
        this.pillDao = treatmentDatabase.pillDao();
        this.pillEntities = this.pillDao.getAllPillEntities();
    }

    public LiveData<List<Pill>> getAllPillEntities() {
        return this.pillEntities;
    }

    // You must call these on a non UI thread or your app will crash

    public void insert(Pill pill){
        TreatmentDatabase.databaseWriterExecutor.execute(()-> {
            pillDao.insert(pill);
        });
    }


    public void delete(Pill pill) {
        TreatmentDatabase.databaseWriterExecutor.execute(()->{
            pillDao.delete(pill);
        });
//        new deleteAsyncTask(pillDao).execute(pill);
    }

    private static class deleteAsyncTask extends AsyncTask<Pill, Void, Void> {

        private PillDao asyncPillDao;
        deleteAsyncTask(PillDao pillDao) {
            asyncPillDao = pillDao;
        }

        @Override
        protected Void doInBackground(Pill... pillEntities) {
            asyncPillDao.delete(pillEntities[0]);
            return null;
        }
    };

    public void deleteAll() {
        TreatmentDatabase.databaseWriterExecutor.execute(()-> {
            pillDao.deleteAll();
        });
    }

    public void printDatabase() {

        LOGGER.log(Level.INFO, "Pill database is empty: " + this.pillEntities.getValue().isEmpty());
        if (! this.pillEntities.getValue().isEmpty()) {
            LOGGER.log(Level.INFO, "Pill database has " + this.pillEntities.getValue().size() + " items.");

            for (Pill pill : this.pillEntities.getValue()) {
                LOGGER.log(Level.INFO, pillToString(pill));
            }
        }
    }

    public String pillToString(Pill pill) {
        String str =  "Pill item : [id: " + pill.getId() +
                                    ", drugType_id: " + pill.getDrugType_id() +
                                    ", date: " + pill.getDate().getTime().toString() +
                                    ", quantity: " + pill.getQuantity() +
                                    ", removed: " + pill.isRemoved() +
                                    ", synched: " + pill.isServer_synched() +
                                    "]";

        return str;
    }
}
