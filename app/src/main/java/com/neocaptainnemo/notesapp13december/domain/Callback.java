package com.neocaptainnemo.notesapp13december.domain;

public interface Callback<T> {

    void onSuccess(T result);

    void onError(Throwable error);
}
