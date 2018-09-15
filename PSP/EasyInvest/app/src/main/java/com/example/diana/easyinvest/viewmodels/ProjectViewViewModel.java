package com.example.diana.easyinvest.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.diana.easyinvest.model.InvestmentsRepository;
import com.example.diana.easyinvest.model.analysis.Analysis;
import com.example.diana.easyinvest.model.companies.Company;
import com.example.diana.easyinvest.model.projects.Project;

import java.util.List;

public class ProjectViewViewModel extends AndroidViewModel {

    private InvestmentsRepository repository;
    private LiveData<List<Company>> companies;

    public ProjectViewViewModel(@NonNull Application application) {
        super(application);
        repository = new InvestmentsRepository(application);
    }

    public LiveData<Project> getProject(long id) {
        return repository.getProject(id);
    }

    public LiveData<Analysis> getAnalysis(long projectId) {
        return repository.getAnalysis(projectId);
    }
}
