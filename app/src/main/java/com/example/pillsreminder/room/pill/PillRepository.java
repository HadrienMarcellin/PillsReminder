package com.example.pillsreminder.room.pill;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PillRepository {

    private PillDao pillDao;
    private LiveData<List<Pill>> pillEntities;

    public PillRepository(Application application) {
        PillDatabase pillDataBase = PillDatabase.getInstance(application);
        this.pillDao = pillDataBase.pillDao();
        this.pillEntities = this.pillDao.getAllPillEntities();
    }

    public LiveData<List<Pill>> getAllPillEntities() {
        return this.pillEntities;
    }

    // You must call these on a non UI thread or your app will crash

    public void insert(Pill pill){
        new insertAsyncTask(pillDao).execute(pill);
    }

    private static class insertAsyncTask extends AsyncTask<Pill, Void, Void> {

        private PillDao asyncPillDao;

        insertAsyncTask(PillDao pillDao) {
            asyncPillDao = pillDao;
        }

        @Override
        protected Void doInBackground(Pill... pillEntities) {
            asyncPillDao.insert(pillEntities[0]);
            return null;
        }
    };

    public void delete(Pill pill) {
        new deleteAsyncTask(pillDao).execute(pill);
    }

    private static class deleteAsyncTask extends AsyncTask<Pill, Void, Void> {

        private PillDao asyncPillDao;
        deleteAsyncTask(PillDao pillDao) {
            asyncPillDao = pillDao;
        }

        @Override
        protected Void doInBackground(Pill... pillEntities) {
            asyncPillDao.delete(pillEntities[0]);
            return null;
        }
    };

    public void deleteAll() {
        PillDatabase.databaseWriteExecutor.execute(()-> {
            pillDao.deleteAll();
        });
    }
}
