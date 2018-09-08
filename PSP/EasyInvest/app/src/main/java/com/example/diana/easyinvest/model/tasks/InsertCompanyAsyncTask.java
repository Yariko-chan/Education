package com.example.diana.easyinvest.model.tasks;

import com.example.diana.easyinvest.model.companies.CompaniesDao;
import com.example.diana.easyinvest.model.companies.Company;

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
