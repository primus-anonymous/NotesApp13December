package com.neocaptainnemo.notesapp13december.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;

import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.neocaptainnemo.notesapp13december.App;
import com.neocaptainnemo.notesapp13december.R;
import com.neocaptainnemo.notesapp13december.db.DummyDao;
import com.neocaptainnemo.notesapp13december.domain.RoomNotesRepository;
import com.neocaptainnemo.notesapp13december.ui.auth.AuthFragment;
import com.neocaptainnemo.notesapp13december.ui.list.NotesListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

            if (account == null) {
                showAuth();
            } else {
                showNotesList();
            }
        }

        getSupportFragmentManager().setFragmentResultListener(AuthFragment.KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                showNotesList();
            }
        });

    }

    private void showNotesList() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, new NotesListFragment())
                .commit();
    }

    private void showAuth() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, new AuthFragment())
                .commit();
    }

}