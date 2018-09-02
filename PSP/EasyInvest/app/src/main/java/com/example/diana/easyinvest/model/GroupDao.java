package com.example.diana.easyinvest.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface GroupDao {

    @Insert
    public void insert(Group group);

    @Query("SELECT * FROM groups")
    LiveData<List<Group>> getAllGroups();
}
