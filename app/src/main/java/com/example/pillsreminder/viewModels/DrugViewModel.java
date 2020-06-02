package com.example.pillsreminder.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pillsreminder.room.drug.Drug;
import com.example.pillsreminder.room.drug.DrugRepository;

import java.util.List;

public class DrugViewModel extends AndroidViewModel {

    private DrugRepository drugRepository;
    private LiveData<List<Drug>> allDrugs;


    public DrugViewModel(@NonNull Application application) {
        super(application);
        drugRepository = new DrugRepository(application);
        allDrugs = drugRepository.getAllDrugs();
    }

    public LiveData<List<Drug>> getAllDrugs() {
        return allDrugs;
    }

    public Drug selectDrugFromName(String name) {
        return drugRepository.selectDrugFromName(name);
    }

    public void insert(Drug drug) {
        drugRepository.insert(drug);
    }

    public void delete(Drug drug) {
        drugRepository.delete(drug);
    }


}
