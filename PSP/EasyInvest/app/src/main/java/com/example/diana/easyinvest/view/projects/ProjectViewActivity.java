package com.example.diana.easyinvest.view.projects;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.diana.easyinvest.R;
import com.example.diana.easyinvest.model.analysis.Analysis;
import com.example.diana.easyinvest.model.projects.Project;
import com.example.diana.easyinvest.view.ViewActivity;
import com.example.diana.easyinvest.viewmodels.ProjectViewViewModel;

import butterknife.BindView;

public class ProjectViewActivity extends ViewActivity {

    @BindView(R.id.name_tv)          TextView nameTv;
    @BindView(R.id.r_tv)             TextView rTv;
    @BindView(R.id.investments_tv)   TextView investmentsTv;
    @BindView(R.id.duration_tv)      TextView durationTv;
    @BindView(R.id.description_tv)   TextView descriptionTv;
    @BindView(R.id.money_flows_list) RecyclerView moneyFlowsRv;
    @BindView(R.id.pp_value)         TextView ppTv;
    @BindView(R.id.dpp_value)        TextView dppTv;
    @BindView(R.id.arr_value)        TextView arrTv;
    @BindView(R.id.npv_value)        TextView npvTv;
    @BindView(R.id.irr_value)        TextView irrTv;
    @BindView(R.id.mirr_value)       TextView mirrTv;
    @BindView(R.id.pi_value)         TextView piTv;

    private static final String PROJECT_ID = "PROJECT_ID";
    private long projectId = -1;

    private ProjectViewViewModel viewModel;

    @Override
    protected int getContentView() {
        return R.layout.activity_project_view;
    }

    public static void startActivity(Context context, long projectId) {
        Intent intent = new Intent(context, ProjectViewActivity.class);
        intent.putExtra(PROJECT_ID, projectId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        if (i != null && i.hasExtra(PROJECT_ID)) {
            projectId = i.getLongExtra(PROJECT_ID, -1);
        }

        // receive project from db
        if (projectId > 0) {
            viewModel = ViewModelProviders.of(this).get(ProjectViewViewModel.class);
            LiveData<Project> project = viewModel.getProject(projectId);
            project.observe(this, project1 -> updateProjectData(project1));
            LiveData<Analysis> analysis = viewModel.getAnalysis(projectId);
            analysis.observe(this, analysis1 -> updateAnalysisData(analysis1));
        }


        // todo divide repository to several classes
        // todo fill data
    }

    private void updateProjectData(Project p) {
        nameTv.setText(p.getName());
        durationTv.setText(String.valueOf(p.getDuration()));
        rTv.setText(String.valueOf(p.getR()));
        investmentsTv.setText(String.valueOf(p.getFlows()[0]));
        descriptionTv.setText(p.getDescription());
        // todo moneyflows
    }

    private void updateAnalysisData(Analysis a) {
        ppTv.setText(String.valueOf(a.getPp()));
        dppTv.setText(String.valueOf(a.getDpp()));
        arrTv.setText(String.valueOf(a.getArr()));
        npvTv.setText(String.valueOf(a.getNpv()));
        irrTv.setText(String.valueOf(a.getIrr()));
        mirrTv.setText(String.valueOf(a.getMirr()));
        piTv.setText(String.valueOf(a.getPi()));
    }

    @Override
    protected String getScreenTitle() {
        return "";
    }
}
