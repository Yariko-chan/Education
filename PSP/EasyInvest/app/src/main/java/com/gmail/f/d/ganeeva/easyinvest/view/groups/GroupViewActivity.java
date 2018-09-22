package com.gmail.f.d.ganeeva.easyinvest.view.groups;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gmail.f.d.ganeeva.easyinvest.R;
import com.gmail.f.d.ganeeva.easyinvest.model.companies.Company;
import com.gmail.f.d.ganeeva.easyinvest.model.groups.Group;
import com.gmail.f.d.ganeeva.easyinvest.view.ViewActivity;
import com.gmail.f.d.ganeeva.easyinvest.view.companies.CompaniesAdapter;
import com.gmail.f.d.ganeeva.easyinvest.viewmodels.GroupViewViewModel;

import java.util.List;

import butterknife.BindView;

public class GroupViewActivity extends ViewActivity {

    @BindView(R.id.list) RecyclerView list;
    @BindView(R.id.add_btn) FloatingActionButton addBtn;

    private static final String GROUP_ID = "GROUP_ID";
    private CompaniesAdapter adapter;
    private long groupId;
    private GroupViewViewModel viewModel;

    @Override
    protected String getScreenTitle() {
        return "";
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_list;
    }

    public static void startActivity(Context context, long companyId) {
        Intent intent = new Intent(context, GroupViewActivity.class);
        intent.putExtra(GROUP_ID, companyId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new CompaniesAdapter();

        Intent i = getIntent();
        if (i != null && i.hasExtra(GROUP_ID)) {
            groupId = i.getLongExtra(GROUP_ID, -1);
        }

        // receive project from db
        if (groupId > 0) {
            viewModel = ViewModelProviders.of(this).get(GroupViewViewModel.class);
            LiveData<List<Company>> project = viewModel.getGroupProjects(groupId);
            project.observe(this, c -> {
                if (c != null) {
                    list.setAdapter(adapter);
                    adapter.setCompanies(c);
                    adapter.notifyDataSetChanged();
                }
            });
            LiveData<Group> group = viewModel.getGroup(groupId);
            group.observe(this, g -> {
                if (g != null) {
                    setTitle(g.getName());
                }
            });
        }
    }
}
