package com.example.diana.easyinvest.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.diana.easyinvest.model.Analysis;
import com.example.diana.easyinvest.model.Company;
import com.example.diana.easyinvest.model.InvestmentsRepository;
import com.example.diana.easyinvest.model.Project;
import com.example.diana.easyinvest.utils.CustomEventListener;

import java.util.List;

public class AddProjectViewModel extends AndroidViewModel {

    private InvestmentsRepository repository;
    private LiveData<List<Company>> companies;

    public AddProjectViewModel(@NonNull Application application) {
        super(application);
        repository = new InvestmentsRepository(application);
    }

    public LiveData<List<Company>> getCompanies() {
        if (companies == null) {
            companies = repository.getAllCompanies();
        }
        return companies;
    }

    public void insert(Project p, CustomEventListener<Long> l) {
        repository.insert(p, l);
    }

    public void insert(Analysis a) {
        repository.insert(a);
    }
}
