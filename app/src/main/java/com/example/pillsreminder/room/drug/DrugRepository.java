package com.example.pillsreminder.room.drug;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.pillsreminder.room.TreatmentDatabase;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DrugRepository {

    private DrugDao drugDao;
    private LiveData<List<Drug>> allDrugs;
    private static final Logger LOGGER = Logger.getLogger(DrugRepository.class.getName());

    private Drug drugByName;

    public DrugRepository(Application application) {
        TreatmentDatabase treatmentDatabase = TreatmentDatabase.getInstance(application);
        this.drugDao = treatmentDatabase.drugDao();
        this.allDrugs = drugDao.getAllDrugs();
    }

    public LiveData<List<Drug>> getAllDrugs() {
        return allDrugs;
    }

    public Drug selectDrugFromName(String name) {
        return allDrugs.getValue().stream().filter(d -> name.equals(d.getName())).findAny().orElse(null);
    }

    public void deleteAll() {
        TreatmentDatabase.databaseWriterExecutor.execute(()-> {
            drugDao.deleteAll();
        });
    }

    public void delete(Drug drug) {
        TreatmentDatabase.databaseWriterExecutor.execute(() -> {
            drugDao.deleteDrug(drug);
        });
    }

    public void insert(Drug drug) {
        TreatmentDatabase.databaseWriterExecutor.execute(()-> {
            drugDao.insert(drug);
        });
    }


    public void printDatabase() {
        LOGGER.log(Level.INFO, "Pill database is empty: " + this.allDrugs.getValue().isEmpty());
        if (! this.allDrugs.getValue().isEmpty()) {
            LOGGER.log(Level.INFO, "Pill database has " + this.allDrugs.getValue().size() + " items.");

            for (Drug drug : this.allDrugs.getValue()) {
                LOGGER.log(Level.INFO, drugToString(drug));
            }
        }
    }

    public String drugToString(Drug drug) {
        String str = "Drug item : [id: " + drug.getId() +
                        ", name: " + drug.getName() +
                        ", type: " + drug.getType() +
                        ", sub_type: " + drug.getSub_type() +
                        ", times_per_day: " + drug.getTimes_per_day() +
                        ", description: " + drug.getDescription() +
                        ", side_effects: " + drug.getSide_effects() +
                        "]" ;
        return str;
    }


}
