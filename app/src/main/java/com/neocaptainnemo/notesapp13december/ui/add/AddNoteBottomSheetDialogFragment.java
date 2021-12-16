package com.neocaptainnemo.notesapp13december.ui.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.neocaptainnemo.notesapp13december.R;
import com.neocaptainnemo.notesapp13december.domain.InMemoryNotesRepository;
import com.neocaptainnemo.notesapp13december.domain.Note;

public class AddNoteBottomSheetDialogFragment extends BottomSheetDialogFragment implements AddNoteView {

    public static final String TAG = "AddNoteBottomSheetDialogFragment";
    private static final String ARG_NOTE = "ARG_NOTE";

    private Button btnSave;
    private ProgressBar progressBar;
    private EditText editTitle;
    private EditText editMessage;
    private NotePresenter presenter;

    public static AddNoteBottomSheetDialogFragment addInstance() {
        return new AddNoteBottomSheetDialogFragment();
    }

    public static AddNoteBottomSheetDialogFragment updateInstance(Note note) {
        AddNoteBottomSheetDialogFragment fragment = new AddNoteBottomSheetDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_not_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress);
        btnSave = view.findViewById(R.id.btn_save);

        editTitle = view.findViewById(R.id.title);
        editMessage = view.findViewById(R.id.message);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onActionPressed(editTitle.getText().toString(), editMessage.getText().toString());
            }
        });

//        DatePicker picker = view.findViewById(R.id.picker);
//
//        picker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
//
//            }
//        });

        if (getArguments() == null) {
            presenter = new AddNotePresenter(this, InMemoryNotesRepository.INSTANCE);
        } else {
            Note note = getArguments().getParcelable(ARG_NOTE);
            presenter = new UpdateNotePresenter(this, InMemoryNotesRepository.INSTANCE, note);
        }
    }

    @Override
    public void showProgress() {
        btnSave.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        btnSave.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setActionButtonText(int title) {
        btnSave.setText(title);
    }

    @Override
    public void setTitle(String title) {
        editTitle.setText(title);
    }

    @Override
    public void setMessage(String message) {
        editMessage.setText(message);
    }

    @Override
    public void actionCompleted(String key, Bundle bundle) {

        getParentFragmentManager()
                .setFragmentResult(key, bundle);

        dismiss();
    }
}
