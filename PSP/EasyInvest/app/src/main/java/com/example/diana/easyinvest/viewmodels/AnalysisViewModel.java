package com.example.diana.easyinvest.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.diana.easyinvest.model.Analysis;
import com.example.diana.easyinvest.model.InvestmentsRepository;

import java.util.List;

public class AnalysisViewModel extends AndroidViewModel {

    private final InvestmentsRepository repository;
//    private final LiveData<List<Analysis>> projects;

    public AnalysisViewModel(@NonNull Application application) {
        super(application);
        repository = new InvestmentsRepository(application);
//        projects = repository.getAll();
    }
}
