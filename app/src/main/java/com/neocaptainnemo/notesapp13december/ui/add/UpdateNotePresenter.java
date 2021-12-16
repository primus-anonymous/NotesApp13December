package com.neocaptainnemo.notesapp13december.ui.add;

import android.os.Bundle;

import com.neocaptainnemo.notesapp13december.R;
import com.neocaptainnemo.notesapp13december.domain.Callback;
import com.neocaptainnemo.notesapp13december.domain.Note;
import com.neocaptainnemo.notesapp13december.domain.NotesRepository;

public class UpdateNotePresenter implements NotePresenter {
    public static final String KEY = "AddNoteBottomSheetDialogFragment_UPDATENOTE";
    public static final String ARG_NOTE = "ARG_NOTE";

    private AddNoteView view;
    private NotesRepository repository;
    private Note note;

    public UpdateNotePresenter(AddNoteView view, NotesRepository repository, Note note) {
        this.view = view;
        this.repository = repository;
        this.note = note;

        view.setActionButtonText(R.string.btn_update);

        view.setTitle(note.getTitle());
        view.setMessage(note.getMessage());
    }

    @Override
    public void onActionPressed(String title, String message) {
        view.showProgress();

        repository.update(note.getId(), title, message, new Callback<Note>() {
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
