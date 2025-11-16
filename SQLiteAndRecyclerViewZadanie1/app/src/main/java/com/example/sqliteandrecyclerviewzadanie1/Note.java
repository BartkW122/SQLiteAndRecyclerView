package com.example.sqliteandrecyclerviewzadanie1;

public class Note {
    private long id;
    private String text;

    public Note(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public long getId() { return id; }
    public String getText() { return text; }
}
