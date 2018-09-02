package com.example.diana.easyinvest.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.diana.easyinvest.model.tasks.InsertProjectAsyncTask;

import java.util.List;

public class InvestmentsRepository {

    private final InvestmentsRoomDatabase db;

    private final ProjectDao projectsDao;
    private final AnalysisDao analysisDao;
    private final GroupDao groupDao;
    private final CompaniesDao companiesDao;

    private LiveData<List<Project>> projects;

    public InvestmentsRepository(Application app) {
        db = InvestmentsRoomDatabase.getDatabase(app);
        projectsDao = db.projectDao();
        analysisDao = db.analysisDao();
        companiesDao = db.companiesDao();
        groupDao = db.groupDao();
    }

    /* Projects */

    public void insert(Project project) {
        new InsertProjectAsyncTask(projectsDao).execute(project);
    }

    public LiveData<List<Project>> getAllProjects() {
        if (projects == null) {
            projects = projectsDao.getAllProjects();
        }
        return projects;
    }

    public LiveData<List<Project>> getCompanyProjects(int id) {
        return projects;
    }
}
