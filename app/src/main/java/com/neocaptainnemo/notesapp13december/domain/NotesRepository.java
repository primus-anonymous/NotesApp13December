package com.neocaptainnemo.notesapp13december.domain;

import java.util.List;

public interface NotesRepository {

    void getAll(Callback<List<Note>> callback);

    void save(String title, String message, Callback<Note> callback);

    void update(String noteId, String title, String message, Callback<Note> callback);

    void delete(Note note, Callback<Void> callback);

}
