package com.neocaptainnemo.notesapp13december.domain;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InMemoryNotesRepository implements NotesRepository {

    public static final NotesRepository INSTANCE = new InMemoryNotesRepository();


    private final Executor executor = Executors.newSingleThreadExecutor();

    private final ArrayList<Note> result = new ArrayList<>();

    private final Handler handler = new Handler(Looper.getMainLooper());

    private InMemoryNotesRepository() {

        Calendar calendar = Calendar.getInstance();

        result.add(new Note(UUID.randomUUID().toString(), "Title One", "Message One", calendar.getTime()));
        result.add(new Note(UUID.randomUUID().toString(), "Title Two", "Message Two", calendar.getTime()));

        calendar.add(Calendar.DAY_OF_YEAR, -3);
        result.add(new Note(UUID.randomUUID().toString(), "Title Three", "Message Three", calendar.getTime()));
        result.add(new Note(UUID.randomUUID().toString(), "Title Four", "Message Four", calendar.getTime()));
        result.add(new Note(UUID.randomUUID().toString(), "Title Five", "Message Five", calendar.getTime()));

        calendar.add(Calendar.MONTH, -2);
        result.add(new Note(UUID.randomUUID().toString(), "Title Six", "Message Six", calendar.getTime()));
//
//        calendar.add(Calendar.DAY_OF_YEAR, -3);
//        result.add(new Note(UUID.randomUUID().toString(), "Title Seven", "Message Seven", calendar.getTime()));
//        result.add(new Note(UUID.randomUUID().toString(), "Title Eight", "Message Eight", calendar.getTime()));
//        result.add(new Note(UUID.randomUUID().toString(), "Title Nine", "Message Nine", calendar.getTime()));
//        result.add(new Note(UUID.randomUUID().toString(), "Title Ten", "Message Ten", calendar.getTime()));

//        for (int i  = 0; i < 10000; i++) {
//            result.add(new Note(UUID.randomUUID().toString(), "Title Ten", "Message Ten", new Date()));
//
//        }
    }

    @Override
    public void getAll(Callback<List<Note>> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        if (new Random().nextBoolean()) {
//                            if (new Random().nextBoolean()) {
                                callback.onSuccess(result);
//                            } else {
//                                callback.onSuccess(new ArrayList<>());
//                            }
//                        } else {
//                            callback.onError(new IOException());
//                        }
                    }
                });
            }
        });
    }

    @Override
    public void save(String title, String message, Callback<Note> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Note note = new Note(UUID.randomUUID().toString(), title, message, new Date());

                        result.add(note);

                        callback.onSuccess(note);
                    }
                });
            }
        });

    }

    @Override
    public void update(Note note, String title, String message, Callback<Note> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        int index = 0;

                        for (int i = 0; i < result.size(); i++) {
                            if (result.get(i).getId().equals(note.getId())) {
                                index = i;
                                break;
                            }
                        }

                        Note editableNote = result.get(index);

                        editableNote.setTitle(title);
                        editableNote.setMessage(message);

                        callback.onSuccess(editableNote);
                    }
                });
            }
        });

    }

    @Override
    public void delete(Note note, Callback<Void> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        result.remove(note);

                        callback.onSuccess(null);
                    }
                });
            }
        });

    }
}
