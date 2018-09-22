package com.gmail.f.d.ganeeva.easyinvest.model.tasks;

import com.gmail.f.d.ganeeva.easyinvest.model.groups.Group;
import com.gmail.f.d.ganeeva.easyinvest.model.groups.GroupDao;

public class InsertGroupAsyncTask extends DbAsyncTask {

    private GroupDao dao;
    private Group g;

    public InsertGroupAsyncTask(GroupDao dao, Group g) {
        this.dao = dao;
        this.g = g;
    }

    @Override
    protected Void doAsync() {
        dao.insert(g);
        return null;
    }
}
