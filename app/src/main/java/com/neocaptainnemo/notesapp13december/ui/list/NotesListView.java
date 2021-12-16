package com.neocaptainnemo.notesapp13december.ui.list;

import com.neocaptainnemo.notesapp13december.domain.Note;
import com.neocaptainnemo.notesapp13december.ui.adapter.AdapterItem;
import com.neocaptainnemo.notesapp13december.ui.adapter.NoteAdapterItem;

import java.util.List;

public interface NotesListView {

    void showNotes(List<AdapterItem> notes);

    void showProgress();

    void hideProgress();

    void showEmpty();

    void hideEmpty();

    void showError(String error);

    void onNoteAdded(NoteAdapterItem adapterItem);

    void onNoteRemoved(Note selectedNote);

    void onNoteUpdated(NoteAdapterItem adapterItem);
}
