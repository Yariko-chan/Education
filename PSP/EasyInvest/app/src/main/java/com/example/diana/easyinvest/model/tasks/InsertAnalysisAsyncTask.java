package com.example.diana.easyinvest.model.tasks;

import com.example.diana.easyinvest.model.Analysis;
import com.example.diana.easyinvest.model.AnalysisDao;

public class InsertAnalysisAsyncTask extends DbAsyncTask {

    private AnalysisDao dao;
    private Analysis a;

    public InsertAnalysisAsyncTask(AnalysisDao dao, Analysis a) {
        this.dao = dao;
        this.a = a;
    }

    @Override
    protected Void doAsync() {
        dao.insert(a);
        return null;
    }
}
