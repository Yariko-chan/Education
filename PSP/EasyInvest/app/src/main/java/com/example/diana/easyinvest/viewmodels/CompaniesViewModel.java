package com.example.diana.easyinvest.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.diana.easyinvest.model.Company;
import com.example.diana.easyinvest.model.InvestmentsRepository;

import java.util.List;

public class CompaniesViewModel extends AndroidViewModel {
    private InvestmentsRepository repository;
    private LiveData<List<Company>> companies;

    public CompaniesViewModel(@NonNull Application application) {
        super(application);
        repository = new InvestmentsRepository(application);
    }

    public LiveData<List<Company>> getCompanies() {
        if (companies == null) {
            companies = repository.getAllCompanies();
        }
        return companies;
    }

    public void insert(Company c) {
        repository.insert(c);
    }
}
