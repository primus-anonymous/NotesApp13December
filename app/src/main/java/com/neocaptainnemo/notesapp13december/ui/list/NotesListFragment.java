package com.neocaptainnemo.notesapp13december.ui.list;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.neocaptainnemo.notesapp13december.R;
import com.neocaptainnemo.notesapp13december.domain.InMemoryNotesRepository;
import com.neocaptainnemo.notesapp13december.domain.Note;
import com.neocaptainnemo.notesapp13december.ui.adapter.AdapterItem;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {

    private SwipeRefreshLayout swipeRefreshLayout;
    private View empty;
    private CoordinatorLayout root;

    private RecyclerView notesList;

    private NotesAdapter adapter;

    private NotesPresenter presenter;

    public NotesListFragment() {
        super(R.layout.fragment_notes_list);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NotesPresenter(requireContext(), this, InMemoryNotesRepository.INSTANCE);
        adapter = new NotesAdapter();

        adapter.setOnClick(new NotesAdapter.OnClick() {
            @Override
            public void onClick(Note note) {
                Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
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

//        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
//        itemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.bg_divider));
//
//        notesList.addItemDecoration(itemDecoration);

        presenter.requestNotes();
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
}
