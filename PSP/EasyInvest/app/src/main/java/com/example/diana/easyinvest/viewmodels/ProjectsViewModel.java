package com.example.diana.easyinvest.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.diana.easyinvest.model.InvestmentsRepository;
import com.example.diana.easyinvest.model.Project;

import java.util.List;

public class ProjectsViewModel extends AndroidViewModel{

    private final InvestmentsRepository repository;
    private final LiveData<List<Project>> projects;

    public ProjectsViewModel(@NonNull Application application) {
        super(application);
        repository = new InvestmentsRepository(application);
        projects = repository.getAllProjects();
    }

    public LiveData<List<Project>> getProjects() {
        return projects;
    }

    public void insert(Project p) {
        repository.insert(p);
    }
}
