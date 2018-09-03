package com.example.diana.easyinvest.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.diana.easyinvest.model.tasks.InsertAnalysisAsyncTask;
import com.example.diana.easyinvest.model.tasks.InsertCompanyAsyncTask;
import com.example.diana.easyinvest.model.tasks.InsertGroupAsyncTask;
import com.example.diana.easyinvest.model.tasks.InsertProjectAsyncTask;

import java.util.List;

public class InvestmentsRepository {

    private final InvestmentsRoomDatabase db;

    private final ProjectDao projectsDao;
    private final AnalysisDao analysisDao;
    private final GroupDao groupDao;
    private final CompaniesDao companiesDao;

    private LiveData<List<Project>> projects;
    private LiveData<List<Group>> groups;
    private LiveData<List<Analysis>> analysis;
    private LiveData<List<Company>> companies;

    public InvestmentsRepository(Application app) {
        db = InvestmentsRoomDatabase.getDatabase(app);
        projectsDao = db.projectDao();
        analysisDao = db.analysisDao();
        companiesDao = db.companiesDao();
        groupDao = db.groupDao();
    }

    /* Projects */

    public void insert(Project project) {
        new InsertProjectAsyncTask(projectsDao, project).execute();
    }

    public LiveData<List<Project>> getAllProjects() {
        if (projects == null) {
            projects = projectsDao.getAll();
        }
        return projects;
    }

    public LiveData<List<Project>> getCompanyProjects(int id) {
        return projectsDao.getCompanyProjects(id);
    }

    /* Groups */

    public void insert(Group g) {
        new InsertGroupAsyncTask(groupDao, g).execute();
    }

    public LiveData<List<Group>> getAllGroups() {
        if (groups == null) {
            groups = groupDao.getAll();
        }
        return groups;
    }

    /* Analysis */

    public void insert(Analysis a) {
        new InsertAnalysisAsyncTask(analysisDao, a).execute();
    }

    public LiveData<List<Analysis>> getAllAnalysis() {
        if (analysis == null) {
            analysis = analysisDao.getAll();
        }
        return analysis;
    }

    public LiveData<Analysis> getProjectAnalysis(int projectId) {
        return analysisDao.getAnalysis(projectId);
    }

    /* Companies */

    public void insert(Company company) {
        new InsertCompanyAsyncTask(companiesDao, company).execute();
    }

    public LiveData<List<Company>> getAllCompanies() {
        if (companies == null) {
            companies = companiesDao.getAll();
        }
        return companies;
    }

    public LiveData<List<Company>> getGroupCompanies(int groupId) {
        return companiesDao.getGroupCompanies(groupId);
    }
}
