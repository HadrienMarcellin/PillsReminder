package com.example.pillsreminder.room.drug;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DrugRepository {

    private DrugDao drugDao;
    private LiveData<List<Drug>> allDrugs;

    private Drug drugByName;

    public DrugRepository(Application application) {
        DrugDatabase drugDatabase = DrugDatabase.getInstance(application);
        this.drugDao = drugDatabase.drugDao();
        this.allDrugs = drugDao.getAllDrugs();
    }

    public LiveData<List<Drug>> getAllDrugs() {
        return allDrugs;
    }

    public Drug selectDrugFromName(String name) {
        return allDrugs.getValue().stream().filter(d -> name.equals(d.getName())).findAny().orElse(null);
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
