package com.example.pillsreminder.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pillsreminder.room.pain.Pain;
import com.example.pillsreminder.room.pain.PainRepository;

import java.util.List;

public class PainViewModel extends AndroidViewModel {


    private PainRepository painRepository;
    private LiveData<List<Pain>> allPains;


    public PainViewModel(@NonNull Application application) {
        super(application);
        this.painRepository = new PainRepository(application);
        this.allPains = painRepository.getAllPains();
    }

    public LiveData<List<Pain>> getAllPains() {
        return allPains;
    }

    public void insertItem(Pain pain) {
        painRepository.insert(pain);
    }

    public void deleteItem(Pain pain) {
        painRepository.delete(pain);
    }

    public void deleteAllItems() {
        painRepository.deleteAll();
    }
}
