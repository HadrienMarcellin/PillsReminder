package com.example.pillsreminder.room.pain;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.pillsreminder.room.TreatmentDatabase;

import java.util.List;
import java.util.logging.Logger;

public class PainRepository {

    private PainDao painDao;
    private LiveData<List<Pain>> allPains;
    private static final Logger LOGGER = Logger.getLogger(PainRepository.class.getName());

    public PainRepository(Application application) {
        TreatmentDatabase treatmentDatabase = TreatmentDatabase.getInstance(application);
        this.painDao = treatmentDatabase.painDao();
        this.allPains = painDao.getAllPains();
    }

    public LiveData<List<Pain>> getAllPains() {
        return allPains;
    }

    // You must call these on a non UI Thread.
    public void deleteAll() {
        TreatmentDatabase.databaseWriterExecutor.execute(()-> {
            painDao.deleteAll();
        });
    }

    public void delete(Pain pain) {
        TreatmentDatabase.databaseWriterExecutor.execute(()-> {
            painDao.delete(pain);
        });
    }

    public void insert(Pain pain) {

        TreatmentDatabase.databaseWriterExecutor.execute(()-> {
            painDao.insert(pain);
        });
    }
}
