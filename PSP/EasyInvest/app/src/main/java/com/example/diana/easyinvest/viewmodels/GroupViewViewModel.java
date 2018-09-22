package com.example.diana.easyinvest.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.diana.easyinvest.model.InvestmentsRepository;
import com.example.diana.easyinvest.model.companies.Company;
import com.example.diana.easyinvest.model.groups.Group;

import java.util.List;

public class GroupViewViewModel extends AndroidViewModel {
    private InvestmentsRepository repository;
    private LiveData<List<Group>> groups;


    public GroupViewViewModel(@NonNull Application application) {
        super(application);
        repository = new InvestmentsRepository(application);
    }

    public LiveData<List<Company>> getGroupProjects(long id) {
        return repository.getGroupCompanies(id);
    }

    public LiveData<Group> getGroup(long id) {
        return repository.getGroup(id);
    }
}
