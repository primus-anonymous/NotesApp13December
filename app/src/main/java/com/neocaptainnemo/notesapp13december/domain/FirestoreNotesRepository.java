package com.neocaptainnemo.notesapp13december.domain;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreNotesRepository implements NotesRepository {

    public static final NotesRepository INSTANCE = new FirestoreNotesRepository();

    private static final String KEY_TITLE = "title";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_CREATED = "createdAt";

    private static final String NOTES = "notes";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void getAll(Callback<List<Note>> callback) {
        db.collection(NOTES)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

                        ArrayList<Note> result = new ArrayList<>();

                        for (DocumentSnapshot snapshot: documents) {
                            String id = snapshot.getId();

                            String title = snapshot.getString(KEY_TITLE);
                            String message = snapshot.getString(KEY_MESSAGE);
                            Date createdAt = snapshot.getDate(KEY_CREATED);

                            result.add(new Note(id, title, message, createdAt));
                        }

                        callback.onSuccess(result);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e);
                    }
                });
    }

    @Override
    public void save(String title, String message, Callback<Note> callback) {
        Map<String, Object> data = new HashMap<>();

        Date createdAt = new Date();

        data.put(KEY_TITLE, title);
        data.put(KEY_MESSAGE, message);
        data.put(KEY_CREATED, createdAt);

        db.collection(NOTES)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String id = documentReference.getId();

                        callback.onSuccess(new Note(id, title, message, createdAt));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e);
                    }
                });
    }

    @Override
    public void update(Note note, String title, String message, Callback<Note> callback) {
        Map<String, Object> data = new HashMap<>();

        data.put(KEY_TITLE, title);
        data.put(KEY_MESSAGE, message);
        data.put(KEY_CREATED, note.getCreatedAt());

        db.collection(NOTES)
                .document(note.getId())
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        note.setTitle(title);
                        note.setMessage(message);

                        callback.onSuccess(note);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e);
                    }
                });

    }

    @Override
    public void delete(Note note, Callback<Void> callback) {
        db.collection(NOTES)
                .document(note.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        callback.onSuccess(unused);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e);
                    }
                });

    }
}
