package com.neocaptainnemo.notesapp13december.domain;

import com.neocaptainnemo.notesapp13december.db.DummyDao;

import java.util.List;

public class RoomNotesRepository implements NotesRepository{

    private DummyDao dummyDao;

    public RoomNotesRepository(DummyDao dummyDao) {
        this.dummyDao = dummyDao;
    }

    @Override
    public void getAll(Callback<List<Note>> callback) {

    }

    @Override
    public void save(String title, String message, Callback<Note> callback) {

    }

    @Override
    public void update(String noteId, String title, String message, Callback<Note> callback) {

    }

    @Override
    public void delete(Note note, Callback<Void> callback) {

    }
}
