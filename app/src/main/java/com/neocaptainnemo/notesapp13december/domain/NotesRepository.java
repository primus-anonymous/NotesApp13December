package com.neocaptainnemo.notesapp13december.domain;

import java.util.List;

public interface NotesRepository {

    void getAll(Callback<List<Note>> callback);
}
