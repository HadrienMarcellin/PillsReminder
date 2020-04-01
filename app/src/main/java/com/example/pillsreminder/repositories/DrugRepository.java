package com.example.pillsreminder.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.pillsreminder.dao.DrugDao;
import com.example.pillsreminder.database.DrugDatabase;
import com.example.pillsreminder.entities.Drug;

import java.util.List;

public class DrugRepository {

    private DrugDao drugDao;
    private LiveData<List<Drug>> allMedecines;

    public DrugRepository(Application application) {
        DrugDatabase drugDatabase = DrugDatabase.getInstance(application);
        this.drugDao = drugDatabase.drugDao();
        this.allMedecines = drugDao.getAllDrugs();
    }

    public LiveData<List<Drug>> getAllMedecines() {
        return allMedecines;
    }

    public void deleteAll() {
        DrugDatabase.databaseWriterExecutor.execute(()-> {
            drugDao.deleteAll();
        });
    }

    public void delete(Drug drug) {
        DrugDatabase.databaseWriterExecutor.execute(() -> {
            drugDao.deleteDrug(drug);
        });
    }

    public void insert(Drug drug) {
        DrugDatabase.databaseWriterExecutor.execute(()-> {
            drugDao.insert(drug);
        });
    }


}
