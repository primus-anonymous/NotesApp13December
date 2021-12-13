package com.neocaptainnemo.notesapp13december.ui.list;

import android.content.Context;

import com.neocaptainnemo.notesapp13december.R;
import com.neocaptainnemo.notesapp13december.domain.Callback;
import com.neocaptainnemo.notesapp13december.domain.Note;
import com.neocaptainnemo.notesapp13december.domain.NotesRepository;
import com.neocaptainnemo.notesapp13december.ui.adapter.AdapterItem;
import com.neocaptainnemo.notesapp13december.ui.adapter.HeaderAdapterItem;
import com.neocaptainnemo.notesapp13december.ui.adapter.NoteAdapterItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotesPresenter {

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

    private Context context;

    private NotesListView view;

    private NotesRepository repository;

    public NotesPresenter(Context context, NotesListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
        this.context = context;
    }

    public void requestNotes() {

        view.showProgress();

        repository.getAll(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> result) {

                ArrayList<AdapterItem> adapterItems = new ArrayList<>();

                Date lastDate = null;

                for (Note note: result) {

                    Date created = note.getCreatedAt();

                    if (!created.equals(lastDate)) {
                        lastDate = note.getCreatedAt();

                        adapterItems.add(new HeaderAdapterItem(dateFormat.format(lastDate)));
                    }

                    adapterItems.add(new NoteAdapterItem(note, note.getTitle(), note.getMessage(), timeFormat.format(note.getCreatedAt())));
                }

                view.showNotes(adapterItems);

                if (adapterItems.isEmpty()) {
                    view.showEmpty();
                } else {
                    view.hideEmpty();
                }
                view.hideProgress();
            }

            @Override
            public void onError(Throwable error) {
                view.showError(context.getString(R.string.try_again_please));
                view.hideProgress();
            }
        });
    }
}
