package com.example.diana.easyinvest.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "companies",
        indices = {@Index("group_id")},
        foreignKeys = @ForeignKey(
                entity = Group.class,
                parentColumns = "id",
                childColumns = "group_id"))
public class Company {
    @PrimaryKey(autoGenerate = true)
    long id;

    @NonNull
    public String name;

    public String description;

    @NonNull
    public String ownerName;

    @NonNull
    public String phone;

    @ColumnInfo(name = "group_id")
    public long groupId;

    Company() {
    }

    @Ignore
    public Company(@NonNull String name, @NonNull String ownerName, @NonNull String phone, long groupId) {
        this.name = name;
        this.ownerName = ownerName;
        this.phone = phone;
        this.groupId = groupId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @NonNull
    public String getOwnerName() {
        return ownerName;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public long getGroupId() {
        return groupId;
    }

    @Ignore
    public long getId() {
        return id;
    }
}
