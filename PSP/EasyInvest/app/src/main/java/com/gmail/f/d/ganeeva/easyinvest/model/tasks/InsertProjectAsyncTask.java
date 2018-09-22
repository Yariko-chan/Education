package com.gmail.f.d.ganeeva.easyinvest.model.tasks;

import com.gmail.f.d.ganeeva.easyinvest.model.projects.Project;
import com.gmail.f.d.ganeeva.easyinvest.model.projects.ProjectDao;
import com.gmail.f.d.ganeeva.easyinvest.utils.CustomEventListener;

public class InsertProjectAsyncTask extends DbResultedAsyncTask {

    private ProjectDao mAsyncTaskDao;
    private Project p;
    private CustomEventListener<Long> l;

    public InsertProjectAsyncTask(ProjectDao dao, Project p, CustomEventListener<Long> l) {
        mAsyncTaskDao = dao;
        this.p = p;
        this.l = l;
    }

    @Override
    protected Long doAsync() {
        return mAsyncTaskDao.insert(p);
    }

    @Override
    protected void onResult(Long result) {
        l.onCustomEvent(result);
    }
}
