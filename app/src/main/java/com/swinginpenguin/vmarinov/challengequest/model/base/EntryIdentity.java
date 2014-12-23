package com.swinginpenguin.vmarinov.challengequest.model.base;

import java.io.Serializable;

/**
 * Created by vmarinov on 10/23/2014.
 */
public class EntryIdentity implements Serializable{
    private int id;
    private long type;
    private String title;
    private String description;

    public EntryIdentity(int id, long type, String title, String description) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public long getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "EntryIdentity{" +
                "id=" + id +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}