package com.example.diana.easyinvest.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "groups")
public class Group {
    @PrimaryKey(autoGenerate = true)
    long id;

    @NonNull
    public String name;

    Group() {}

    @Ignore
    public Group(String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Ignore
    public long getId() {
        return id;
    }
}
