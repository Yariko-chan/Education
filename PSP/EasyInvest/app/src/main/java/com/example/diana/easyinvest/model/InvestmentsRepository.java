package com.example.diana.easyinvest.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.diana.easyinvest.model.analysis.Analysis;
import com.example.diana.easyinvest.model.analysis.AnalysisDao;
import com.example.diana.easyinvest.model.companies.CompaniesDao;
import com.example.diana.easyinvest.model.companies.Company;
import com.example.diana.easyinvest.model.groups.Group;
import com.example.diana.easyinvest.model.groups.GroupDao;
import com.example.diana.easyinvest.model.projects.Project;
import com.example.diana.easyinvest.model.projects.ProjectDao;
import com.example.diana.easyinvest.model.tasks.InsertAnalysisAsyncTask;
import com.example.diana.easyinvest.model.tasks.InsertCompanyAsyncTask;
import com.example.diana.easyinvest.model.tasks.InsertGroupAsyncTask;
import com.example.diana.easyinvest.model.tasks.InsertProjectAsyncTask;
import com.example.diana.easyinvest.utils.CustomEventListener;

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

    public void insert(Project project, CustomEventListener<Long> l) {
        new InsertProjectAsyncTask(projectsDao, project, l).execute();
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

    public LiveData<Project> getProject(long id) {
        return projectsDao.getProject(id);
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

    public LiveData<Analysis> getAnalysis(long projectId) {
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
