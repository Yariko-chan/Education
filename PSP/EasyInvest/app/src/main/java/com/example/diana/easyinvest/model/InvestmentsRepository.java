package com.example.diana.easyinvest.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class InvestmentsRepository {

    private final InvestmentsRoomDatabase db;
    private final ProjectDao projectsDao;
    private LiveData<List<Project>> projects;

    public InvestmentsRepository(Application app) {
        db = InvestmentsRoomDatabase.getDatabase(app);
        projectsDao = db.projectDao();
        projects = projectsDao.getAllProjects();
    }

    public LiveData<List<Project>> getAllProjects() {
        return projects;
    }

    public void insert(Project project) {
        new insertAsyncTask(projectsDao).execute(project);
    }

    private static class insertAsyncTask extends AsyncTask<Project, Void, Void> {

        private ProjectDao mAsyncTaskDao;

        insertAsyncTask(ProjectDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Project... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
