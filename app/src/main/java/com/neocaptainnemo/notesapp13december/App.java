package com.neocaptainnemo.notesapp13december;

import android.app.Application;

import androidx.room.Room;

import com.neocaptainnemo.notesapp13december.db.DymmyBase;

public class App extends Application {

    private DymmyBase db;

    @Override
    public void onCreate() {
        super.onCreate();

         db = Room.databaseBuilder(getApplicationContext(),
                 DymmyBase.class, "database-name").build();

    }

    public DymmyBase getDb() {
        return db;
    }
}
