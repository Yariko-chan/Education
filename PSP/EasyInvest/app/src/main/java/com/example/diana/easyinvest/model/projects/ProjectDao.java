package com.example.diana.easyinvest.model.projects;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert
    long insert(Project project);

    @Query("SELECT * FROM projects")
    LiveData<List<Project>> getAll();

    @Query("SELECT * FROM projects WHERE company_id = :companyId")
    LiveData<List<Project>> getCompanyProjects(int companyId);

    @Query("SELECT * FROM projects WHERE id = :id")
    LiveData<Project> getProject(long id);
}
