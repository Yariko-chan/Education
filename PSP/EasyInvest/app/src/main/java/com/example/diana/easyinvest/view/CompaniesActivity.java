package com.example.diana.easyinvest.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.diana.easyinvest.R;
import com.example.diana.easyinvest.model.Company;
import com.example.diana.easyinvest.view.adapters.CompaniesAdapter;
import com.example.diana.easyinvest.viewmodels.CompaniesViewModel;

import java.util.List;

import butterknife.BindView;

public class CompaniesActivity extends NavMenuActivity implements View.OnClickListener {

    @BindView(R.id.list)    RecyclerView list;
    @BindView(R.id.add_btn) FloatingActionButton addBtn;

    private CompaniesAdapter adapter;
    private CompaniesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.companies);

        adapter = new CompaniesAdapter();
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(lm);
        list.setAdapter(adapter);
        addBtn.setOnClickListener(this);

        viewModel = ViewModelProviders.of(this)
                .get(CompaniesViewModel.class);
        LiveData<List<Company>> companies = viewModel.getCompanies();
        if (companies != null) {
            companies.observe(this, companyList -> {
                adapter.setCompanies(companyList);
                adapter.notifyDataSetChanged();
            });
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn:
                AddCompanyActivity.startActivity(this);
                break;
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CompaniesActivity.class);
        context.startActivity(intent);
    }
}
