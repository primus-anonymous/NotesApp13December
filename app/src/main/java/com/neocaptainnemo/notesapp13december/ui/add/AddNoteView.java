package com.neocaptainnemo.notesapp13december.ui.add;

import android.os.Bundle;

import androidx.annotation.StringRes;

import com.neocaptainnemo.notesapp13december.domain.Note;

public interface AddNoteView {

    void showProgress();

    void hideProgress();

    void setActionButtonText(@StringRes int title);

    void setTitle(String title);

    void setMessage(String message);

    void actionCompleted(String key, Bundle bundle);
}
