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
        this.drugDao = drugDatabase.medecineDao();
        this.allMedecines = drugDao.getAllMedecines();
    }

    public LiveData<List<Drug>> getAllMedecines() {
        return allMedecines;
    }

    public void deleteAll() {
        DrugDatabase.databseWriteExecutor.execute(()-> {
            drugDao.deleteAll();
        });
    }

    public void delete(Drug drug) {
        DrugDatabase.databseWriteExecutor.execute(() -> {
            drugDao.deleteMedecine(drug);
        });
    }

    public void insert(Drug drug) {
        DrugDatabase.databseWriteExecutor.execute(()-> {
            drugDao.insert(drug);
        });
    }


}
