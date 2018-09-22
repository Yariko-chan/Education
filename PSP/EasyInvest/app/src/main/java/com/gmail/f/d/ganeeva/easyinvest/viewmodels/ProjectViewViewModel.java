package com.gmail.f.d.ganeeva.easyinvest.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.gmail.f.d.ganeeva.easyinvest.model.InvestmentsRepository;
import com.gmail.f.d.ganeeva.easyinvest.model.analysis.Analysis;
import com.gmail.f.d.ganeeva.easyinvest.model.companies.Company;
import com.gmail.f.d.ganeeva.easyinvest.model.projects.Project;

import java.util.List;

public class ProjectViewViewModel extends AndroidViewModel {

    private InvestmentsRepository repository;

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
