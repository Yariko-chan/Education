package com.gmail.f.d.ganeeva.easyinvest.model.groups;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface GroupDao {

    @Insert
    public long insert(Group group);

    @Query("SELECT * FROM groups")
    LiveData<List<Group>> getAll();

    @Query("SELECT * FROM groups WHERE id = :id")
    LiveData<Group> getGroup(long id);
}
