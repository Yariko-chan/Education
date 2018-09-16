package com.example.diana.easyinvest.view.projects;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.diana.easyinvest.R;
import com.example.diana.easyinvest.model.analysis.Analysis;
import com.example.diana.easyinvest.model.projects.Project;
import com.example.diana.easyinvest.utils.Numbers;
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
    @BindView(R.id.pp_description)   TextView ppDescription;
    @BindView(R.id.dpp_description)  TextView dppDescription;
    @BindView(R.id.arr_description)  TextView arrDescription;
    @BindView(R.id.npv_description)  TextView npvDescription;
    @BindView(R.id.irr_description)  TextView irrDescription;
    @BindView(R.id.mirr_description) TextView mirrDescription;
    @BindView(R.id.pi_description)   TextView piDescription;
    @BindView(R.id.pp_row)           TableRow ppRow;
    @BindView(R.id.dpp_row)          TableRow dppRow;
    @BindView(R.id.arr_row)          TableRow arrRow;
    @BindView(R.id.npv_row)          TableRow npvRow;
    @BindView(R.id.irr_row)          TableRow irrRow;
    @BindView(R.id.mirr_row)         TableRow mirrRow;
    @BindView(R.id.pi_row)           TableRow piRow;

    private static final String PROJECT_ID = "PROJECT_ID";
    private long projectId = -1;

    private ProjectViewViewModel viewModel;
    private Project p;

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
            project.observe(this, p -> {
                if (p != null) {
                    updateProjectData(p);
                    LiveData<Analysis> analysis = viewModel.getAnalysis(projectId);
                    analysis.observe(this, a -> {
                        if (a != null) {
                            updateAnalysisData(a);
                        }
                    });
                }
            });
        }


        // todo divide repository to several classes
        // todo fill data
    }

    private void updateProjectData(Project p) {
        this.p = p;
        nameTv.setText(p.getName());
        durationTv.setText(Numbers.unifiedDouble(p.getDuration()));
        rTv.setText(Numbers.unifiedDouble(p.getR()));
        investmentsTv.setText(Numbers.unifiedDouble(p.getFlows()[0]));
        descriptionTv.setText(p.getDescription());

        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        MoneyFlowAdapter adapter = new MoneyFlowAdapter(p.getFlows());
        moneyFlowsRv.setLayoutManager(lm);
        moneyFlowsRv.setAdapter(adapter);
    }

    private void updateAnalysisData(Analysis a) {
        int pp = a.getPp();
        int color = (pp >= 0)
                ? R.color.green
                : R.color.red;
        String explanation = (pp >= 0)
                ? getString(R.string.pp_profitable, String.valueOf(pp))
                : getString(R.string.pp_unprofitable);
        ppTv.setText(Numbers.unifiedDouble(pp));
        ppDescription.setText(explanation);
        ppRow.setBackgroundColor(ContextCompat.getColor(this, color));

        int dpp = a.getDpp();
        color = (dpp >= 0)
                ? R.color.green
                : R.color.red;
        explanation = (dpp >= 0)
                ? getString(R.string.pp_profitable, String.valueOf(pp))
                : getString(R.string.pp_unprofitable);
        dppTv.setText(Numbers.unifiedDouble(dpp));
        dppDescription.setText(explanation);
        dppRow.setBackgroundColor(ContextCompat.getColor(this, color));

        double arr = a.getArr();
        String arrStr = Numbers.unifiedDouble(arr);
        color = (arr >= 0)
                ? R.color.green
                : R.color.red;
        explanation = getString(R.string.arr_investments_return, arrStr);
        arrTv.setText(arrStr);
        arrDescription.setText(explanation);
        arrRow.setBackgroundColor(ContextCompat.getColor(this, color));

        double npv = a.getNpv();
        if      (npv == 0) color = R.color.orange;
        else if (npv > 0)  color = R.color.green;
        else if (npv < 0)  color = R.color.red;
        if      (npv == 0) explanation = getString(R.string.npv_pays_off);
        else if (npv > 0)  explanation = getString(R.string.npv_profitable);
        else if (npv < 0)  explanation = getString(R.string.npv_unprofitable);
        npvTv.setText(Numbers.unifiedDouble(npv));
        npvDescription.setText(explanation);
        npvRow.setBackgroundColor(ContextCompat.getColor(this, color));

        double irr = a.getIrr();
        double r = p.getR();
        String rStr = Numbers.unifiedDouble(r);
        if      (irr == r) color = R.color.orange;
        else if (irr > r)  color = R.color.green;
        else if (irr < r)  color = R.color.red;
        if      (irr == r) explanation = getString(R.string.irr_pays_off, rStr);
        else if (irr > r)  explanation = getString(R.string.irr_profitable, rStr);
        else if (irr < r)  explanation = getString(R.string.irr_unprofitable, rStr);
        irrTv.setText(Numbers.unifiedDouble(irr));
        irrDescription.setText(explanation);
        irrRow.setBackgroundColor(ContextCompat.getColor(this, color));

        double mirr = a.getMirr();
        if      (mirr == r) color = R.color.orange;
        else if (mirr > r)  color = R.color.green;
        else if (mirr < r)  color = R.color.red;
        if      (mirr == r) explanation = getString(R.string.irr_pays_off, rStr);
        else if (mirr > r)  explanation = getString(R.string.irr_profitable, rStr);
        else if (mirr < r)  explanation = getString(R.string.irr_unprofitable, rStr);
        mirrTv.setText(Numbers.unifiedDouble(mirr));
        mirrDescription.setText(explanation);
        mirrRow.setBackgroundColor(ContextCompat.getColor(this, color));

        double pi = a.getPi();
        String piStr = Numbers.unifiedDouble(pi);
        explanation = getString(R.string.pi_invest_return, piStr);
        if      (pi == 1) color = R.color.orange;
        else if (pi > 1)  color = R.color.green;
        else if (pi < 1)  color = R.color.red;
        piTv.setText(piStr);
        piDescription.setText(explanation);
        piRow.setBackgroundColor(ContextCompat.getColor(this, color));
    }

    @Override
    protected String getScreenTitle() {
        return "";
    }
}
