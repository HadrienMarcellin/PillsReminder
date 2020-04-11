package com.example.pillsreminder.room.pain;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PainRepository {

    private PainDao painDao;
    private LiveData<List<Pain>> allPains;

    public PainRepository(Application application) {
        PainDatabase painDatabase = PainDatabase.getInstance(application);
        this.painDao = painDatabase.painDao();
        this.allPains = painDao.getAllPains();
    }

    public LiveData<List<Pain>> getAllPains() {
        return allPains;
    }

    // You must call these on a non UI Thread.
    public void deleteAll() {
        PainDatabase.databaseWriterExecutor.execute(()-> {
            painDao.deleteAll();
        });
    }

    public void delete(Pain pain) {
        PainDatabase.databaseWriterExecutor.execute(()-> {
            painDao.delete(pain);
        });
    }

    public void insert(Pain pain) {
        PainDatabase.databaseWriterExecutor.execute(()-> {
            painDao.insert(pain);
        });
    }
}