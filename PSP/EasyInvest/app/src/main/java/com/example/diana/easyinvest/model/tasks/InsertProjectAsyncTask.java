package com.example.diana.easyinvest.model.tasks;

import android.arch.persistence.room.Dao;
import android.os.AsyncTask;

import com.example.diana.easyinvest.model.Project;
import com.example.diana.easyinvest.model.ProjectDao;

public class InsertProjectAsyncTask extends AsyncTask<Project, Void, Void> {

    private ProjectDao mAsyncTaskDao;

    public InsertProjectAsyncTask(ProjectDao dao) {
        mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final Project... params) {
        mAsyncTaskDao.insert(params[0]);
        return null;
    }
}
