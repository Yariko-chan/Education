package com.example.diana.easyinvest.model.tasks;

import com.example.diana.easyinvest.model.CompaniesDao;
import com.example.diana.easyinvest.model.Company;

public class InsertCompanyAsyncTask extends DbAsyncTask {

    private CompaniesDao dao;
    private Company company;

    public InsertCompanyAsyncTask(CompaniesDao dao, Company company) {
        this.dao = dao;
        this.company = company;
    }

    @Override
    protected Void doAsync() {
        dao.insert(company);
        return null;
    }
}
