package com.neocaptainnemo.notesapp13december.ui.list;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.neocaptainnemo.notesapp13december.R;
import com.neocaptainnemo.notesapp13december.domain.FirestoreNotesRepository;
import com.neocaptainnemo.notesapp13december.domain.InMemoryNotesRepository;
import com.neocaptainnemo.notesapp13december.domain.Note;
import com.neocaptainnemo.notesapp13december.domain.SharedPreferencesNotesRepository;
import com.neocaptainnemo.notesapp13december.ui.adapter.AdapterItem;
import com.neocaptainnemo.notesapp13december.ui.adapter.NoteAdapterItem;
import com.neocaptainnemo.notesapp13december.ui.add.AddNoteBottomSheetDialogFragment;
import com.neocaptainnemo.notesapp13december.ui.add.AddNotePresenter;
import com.neocaptainnemo.notesapp13december.ui.add.UpdateNotePresenter;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

    private SwipeRefreshLayout swipeRefreshLayout;
    private View empty;
    private CoordinatorLayout root;

    private RecyclerView notesList;

    private NotesAdapter adapter;

    private NotesPresenter presenter;

    private Note selectedNote;

    public NotesListFragment() {
        super(R.layout.fragment_notes_list);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NotesPresenter(requireContext(), this, FirestoreNotesRepository.INSTANCE);
        adapter = new NotesAdapter(this);

        adapter.setOnClick(new NotesAdapter.OnClick() {
            @Override
            public void onClick(Note note) {
                Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(Note note) {
                selectedNote = note;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        root = view.findViewById(R.id.coordinator);

        swipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestNotes();
            }
        });

        notesList = view.findViewById(R.id.notes_list);
        empty = view.findViewById(R.id.empty);

        notesList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
//        notesList.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        notesList.setAdapter(adapter);

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNoteBottomSheetDialogFragment.addInstance()
                        .show(getParentFragmentManager(), AddNoteBottomSheetDialogFragment.TAG);
            }
        });

//        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
//        itemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.bg_divider));
//
//        notesList.addItemDecoration(itemDecoration);

//        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
//        defaultItemAnimator.setRemoveDuration(5000L);
//        defaultItemAnimator.setAddDuration(3000L);
//
//        notesList.setItemAnimator(defaultItemAnimator);

        presenter.requestNotes();

        getParentFragmentManager()
                .setFragmentResultListener(AddNotePresenter.KEY, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = result.getParcelable(AddNotePresenter.ARG_NOTE);

                        presenter.onNoteAdded(note);

                    }
                });

        getParentFragmentManager()
                .setFragmentResultListener(UpdateNotePresenter.KEY, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = result.getParcelable(UpdateNotePresenter.ARG_NOTE);

                        presenter.onNoteUpdate(note);
                    }
                });

    }

    @Override
    public void showNotes(List<AdapterItem> notes) {
        adapter.setData(notes);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showEmpty() {
        empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty() {
        empty.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        Snackbar.make(root, error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.requestNotes();
                    }
                })
                .show();
    }

    @Override
    public void onNoteAdded(NoteAdapterItem adapterItem) {
        int index = adapter.addItem(adapterItem);

        adapter.notifyItemInserted(index - 1);

        notesList.smoothScrollToPosition(index - 1);
    }

    @Override
    public void onNoteRemoved(Note selectedNote) {

        int index = adapter.removeItem(selectedNote);

        adapter.notifyItemRemoved(index);
    }

    @Override
    public void onNoteUpdated(NoteAdapterItem adapterItem) {
        int index = adapter.updateItem(adapterItem);

        adapter.notifyItemChanged(index);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.notes_list_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            presenter.removeNote(selectedNote);
            return true;
        }

        if (item.getItemId() == R.id.action_update) {
            AddNoteBottomSheetDialogFragment.updateInstance(selectedNote)
                    .show(getParentFragmentManager(), AddNoteBottomSheetDialogFragment.TAG);

            return true;
        }

        return super.onContextItemSelected(item);
    }
}
