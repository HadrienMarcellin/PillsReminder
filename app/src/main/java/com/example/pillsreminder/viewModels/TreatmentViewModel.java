package com.example.pillsreminder.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pillsreminder.room.drug.Drug;
import com.example.pillsreminder.room.drug.DrugRepository;
import com.example.pillsreminder.room.pain.Pain;
import com.example.pillsreminder.room.pain.PainRepository;
import com.example.pillsreminder.room.pill.Pill;
import com.example.pillsreminder.room.pill.PillRepository;

import java.util.List;
import java.util.logging.Logger;


/**
 * TODO : Explain the purpose of this class and the importance of AndroidViewModel when dealing with multiple tables.
 */
public class TreatmentViewModel extends AndroidViewModel {

    private static final Logger LOGGER = Logger.getLogger(TreatmentViewModel.class.getName());
    private PillRepository pillRepository;
    private DrugRepository drugRepository;
    private PainRepository painRepository;

    private LiveData<List<Pill>> allPills;
    private LiveData<List<Pain>> allPains;
    private LiveData<List<Drug>> allDrugs;

    public TreatmentViewModel(@NonNull Application application) {
        super(application);
        pillRepository = new PillRepository(application);
        painRepository = new PainRepository(application);
        drugRepository = new DrugRepository(application);

        allPills = pillRepository.getAllPillEntities();
        allDrugs = drugRepository.getAllDrugs();
        allPains = painRepository.getAllPains();
    }


    /*
    Getters
     */

    public LiveData<List<Pill>> getAllPills() {
        return allPills;
    }

    public LiveData<List<Pain>> getAllPains() {
        return allPains;
    }

    public LiveData<List<Drug>> getAllDrugs() {
        return allDrugs;
    }


    /*
    Inserters
     */


    // TODO: implement method to insert the full arrays, not just the first element.
    /**
     * Insert items into pill table
     * @param pills Array of Pill
     */
    public void insertPill(Pill... pills) {
        pillRepository.insert(pills[0]);
    }

    /**
     * Insert items into drug table
     * @param drugs Array of Drug
     */
    public void insertDrug(Drug... drugs) {
        drugRepository.insert(drugs[0]);
    }

    /**
     * Insert items into pain table
     * @param pains Array of Pain
     */
    public void insertPain(Pain... pains) {
        painRepository.insert(pains[0]);
    }


    /*
    Deleters
     */

    /**
     * Delete items from pill table
     * @param pill Item to delete
     */
    public void deletePill(Pill pill) {
        pillRepository.delete(pill);
    }

    public void deleteAllPills() {
        pillRepository.deleteAll();
    }

    public void deletePain(Pain pain) {
        painRepository.delete(pain);
    }

    public void deleteAllPains() {
        painRepository.deleteAll();
    }

    public void deleteDrug(Drug drug) {
        drugRepository.delete(drug);
    }

    public void deleteAllDrugs() {
        drugRepository.deleteAll();
    }


    /*
    Displays
    TODO: Override Entity.toString() instead of using entityToString if possible, so one could use Pill.toString().
     */

    public void printAllPills() {
        pillRepository.printDatabase();
    }

    public String pillToString(Pill pill) {
        return pillRepository.pillToString(pill);
    }

    public void printAllDrugs() {
        drugRepository.printDatabase();
    }

    public String drugToString(Drug drug) {
        return drugRepository.drugToString(drug);
    }



    /*
    Extra operation on table
     */
    public Drug selectDrugFromName(String name) {
        return drugRepository.selectDrugFromName(name);
    }

}
