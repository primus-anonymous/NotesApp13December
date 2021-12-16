package com.neocaptainnemo.notesapp13december.db;


import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DummyDao {
    @Query("SELECT * FROM Dummy")
    List<Dummy> getAll();

}