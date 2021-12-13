package com.neocaptainnemo.notesapp13december.domain;

import java.util.Date;

public class Note {

    private String id;

    private String title;

    private String message;

    private Date createdAt;

    public Note(String id, String title, String message, Date createdAt) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
