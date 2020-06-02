package com.example.pillsreminder.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pillsreminder.room.pill.Pill;
import com.example.pillsreminder.room.pill.PillRepository;

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

    public void syncDatabaseToServer(List<Pill> server_data) {
//        TODO: - (Download the list of pills saved on the server and create a list of it.)
//              - keep track of pills removed by the user and delete them on the server side.
//                  - Add a boolean column "removed" to signal the entry was removed by the user.
//                  - Add a boolean column "sync" to signal synchronisation with the server.
//              - keep track of pills added by the user and add them to teh server side.
//
//
    }




}
