package com.example.diana.easyinvest.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.diana.easyinvest.App;
import com.example.diana.easyinvest.model.Group;
import com.example.diana.easyinvest.model.InvestmentsRepository;

import java.util.List;

public class GroupsViewModel extends AndroidViewModel{
    private InvestmentsRepository repository;
    private LiveData<List<Group>> groups;


    public GroupsViewModel(@NonNull Application application) {
        super(application);
        repository = new InvestmentsRepository(application);
    }

    public LiveData<List<Group>> getGroups() {
        if (groups == null) {
            groups = repository.getAllGroups();
        }
        return groups;
    }

    public void insert(Group g) {
        repository.insert(g);
    }
}
