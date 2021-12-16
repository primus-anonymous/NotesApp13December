package com.neocaptainnemo.notesapp13december.ui.add;

import android.os.Bundle;

import com.neocaptainnemo.notesapp13december.R;
import com.neocaptainnemo.notesapp13december.domain.Callback;
import com.neocaptainnemo.notesapp13december.domain.Note;
import com.neocaptainnemo.notesapp13december.domain.NotesRepository;

public class AddNotePresenter implements NotePresenter {

    public static final String KEY = "AddNoteBottomSheetDialogFragment_ADDNOTE";
    public static final String ARG_NOTE = "ARG_NOTE";

    private AddNoteView view;
    private NotesRepository repository;

    public AddNotePresenter(AddNoteView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;

        view.setActionButtonText(R.string.btn_save);
    }

    @Override
    public void onActionPressed(String title, String message) {
        view.showProgress();

        repository.save(title, message, new Callback<Note>() {
            @Override
            public void onSuccess(Note result) {
                view.hideProgress();
                Bundle bundle = new Bundle();
                bundle.putParcelable(ARG_NOTE, result);
                view.actionCompleted(KEY, bundle);
            }

            @Override
            public void onError(Throwable error) {
                view.hideProgress();
            }
        });
    }
}
