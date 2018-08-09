package com.example.diana.easyinvest.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "projects", indices = {@Index("id")})
public class Project {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    int id;

    @NonNull
    String name;

    String description;

    @NonNull
    float r;

    @NonNull
    int duration;

    public Project(@NonNull String name, String description, @NonNull float r, @NonNull int duration) {
        this.name = name;
        this.description = description;
        this.r = r;
        this.duration = duration;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @NonNull
    public float getR() {
        return r;
    }

    @NonNull
    public int getDuration() {
        return duration;
    }
}
