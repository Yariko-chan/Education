package com.example.diana.easyinvest.model.analysis;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AnalysisDao {

    @Insert
    public void insert(Analysis analysis);

    @Query("SELECT * FROM analysis")
    LiveData<List<Analysis>> getAll();


    @Query("SELECT * FROM analysis WHERE project_id = :id LIMIT 1 OFFSET 0")
    public LiveData<Analysis> getAnalysis(long id);
}
