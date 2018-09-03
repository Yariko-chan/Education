package com.example.diana.easyinvest.model.tasks;

import com.example.diana.easyinvest.model.Project;
import com.example.diana.easyinvest.model.ProjectDao;

public class InsertProjectAsyncTask extends DbAsyncTask {

    private ProjectDao mAsyncTaskDao;
    private Project p;

    public InsertProjectAsyncTask(ProjectDao dao, Project p) {
        mAsyncTaskDao = dao;
        this.p = p;
    }

    @Override
    protected Void doAsync() {
        mAsyncTaskDao.insert(p);
        return null;
    }
}
