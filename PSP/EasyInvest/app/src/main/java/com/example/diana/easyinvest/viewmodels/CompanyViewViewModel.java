package com.example.diana.easyinvest.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.diana.easyinvest.model.InvestmentsRepository;
import com.example.diana.easyinvest.model.companies.Company;
import com.example.diana.easyinvest.model.projects.Project;

import java.util.List;

public class CompanyViewViewModel extends AndroidViewModel {

    private final InvestmentsRepository repository;

    public CompanyViewViewModel(@NonNull Application application) {
        super(application);
        repository = new InvestmentsRepository(application);
    }

    public LiveData<Company> getCompany(long id) {
        return repository.getCompany(id);
    }

    public LiveData<List<Project>> getCompanyProjects(long id) {
        return repository.getCompanyProjects(id);
    }
}
