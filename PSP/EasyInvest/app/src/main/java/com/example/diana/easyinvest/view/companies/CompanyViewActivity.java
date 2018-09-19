package com.example.diana.easyinvest.view.companies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.diana.easyinvest.R;
import com.example.diana.easyinvest.model.analysis.Analysis;
import com.example.diana.easyinvest.model.companies.Company;
import com.example.diana.easyinvest.model.projects.Project;
import com.example.diana.easyinvest.view.ViewActivity;
import com.example.diana.easyinvest.view.projects.ProjectViewActivity;
import com.example.diana.easyinvest.view.projects.ProjectsAdapter;
import com.example.diana.easyinvest.viewmodels.CompanyViewViewModel;
import com.example.diana.easyinvest.viewmodels.ProjectViewViewModel;

import java.util.List;

import butterknife.BindView;

public class CompanyViewActivity extends ViewActivity {

    private static final String COMPANY_ID = "COMPANY_ID";

    @BindView(R.id.name_tv)          TextView nameTv;
    @BindView(R.id.owner_name_tv)    TextView ownerNameTv;
    @BindView(R.id.phone_tv)         TextView phoneTv;
    @BindView(R.id.description_tv)   TextView descriptionTv;
    @BindView(R.id.projects_list)    RecyclerView projectsRv;

    private long companyId;
    private CompanyViewViewModel viewModel;
    private ProjectsAdapter adapter;

    @Override
    protected String getScreenTitle() {
        return "";
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_company_view;
    }

    public static void startActivity(Context context, long companyId) {
        Intent intent = new Intent(context, CompanyViewActivity.class);
        intent.putExtra(COMPANY_ID, companyId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        projectsRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ProjectsAdapter();

        Intent i = getIntent();
        if (i != null && i.hasExtra(COMPANY_ID)) {
            companyId = i.getLongExtra(COMPANY_ID, -1);
        }

        // receive project from db
        if (companyId > 0) {
            viewModel = ViewModelProviders.of(this).get(CompanyViewViewModel.class);
            LiveData<Company> project = viewModel.getCompany(companyId);
            project.observe(this, c -> {
                if (c != null) {
                    updateCompanyData(c);
                    LiveData<List<Project>> analysis = viewModel.getCompanyProjects(companyId);
                    analysis.observe(this, l -> {
                        if (l != null) {
                            updatePojectsList(l);
                        }
                    });
                }
            });
        }
    }

    private void updateCompanyData(Company c) {
        nameTv.setText(c.getName());
        ownerNameTv.setText(c.getOwnerName());
        phoneTv.setText(c.getPhone());
        descriptionTv.setText(c.getDescription());
    }

    private void updatePojectsList(List<Project> l) {
        projectsRv.setAdapter(adapter);
        adapter.setProjects(l);
        adapter.notifyDataSetChanged();
    }
}
