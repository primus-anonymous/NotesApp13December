package com.neocaptainnemo.notesapp13december.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Dummy.class}, version = 1)
public abstract class DymmyBase extends RoomDatabase {

    public abstract DummyDao userDao();

}
