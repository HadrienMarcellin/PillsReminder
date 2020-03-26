package com.example.pillsreminder.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pillsreminder.entities.Pill;
import com.example.pillsreminder.repositories.PillRepository;

import java.util.List;

public class PillViewModel extends AndroidViewModel {

    private PillRepository pillRepository;
    private LiveData<List<Pill>> pillEntities;

    public PillViewModel(@NonNull Application application) {
        super(application);
        pillRepository = new PillRepository(application);
        pillEntities = pillRepository.getAllPillEntities();
    }


    public LiveData<List<Pill>> getAllPillEntities() {
        return pillEntities;
    }

    public void insertItem(Pill pill) {
        pillRepository.insert(pill);
    }

    public void deleteItem(Pill pill) {
        pillRepository.delete(pill);
    }

    public void deleteAll() {pillRepository.deleteAll();}
}
