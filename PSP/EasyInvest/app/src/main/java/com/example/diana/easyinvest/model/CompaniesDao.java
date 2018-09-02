package com.example.diana.easyinvest.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CompaniesDao {

    @Insert
    void insert(Company company);

    @Query("SELECT * FROM companies")
    LiveData<List<Company>> getAllCompanies();

    @Query("SELECT * FROM companies WHERE group_id = :groupId")
    LiveData<List<Company>> getGroupCompanies(int groupId);
}
