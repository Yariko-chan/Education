package com.example.diana.easyinvest.view;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.diana.easyinvest.R;
import com.example.diana.easyinvest.model.Project;
import com.example.diana.easyinvest.view.adapters.ProjectsAdapter;
import com.example.diana.easyinvest.viewmodels.ProjectsViewModel;

import java.util.List;

public class ProjectsActivity extends NavMenuActivity implements View.OnClickListener {

    private ProjectsViewModel viewModel;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView rv = findViewById(R.id.list);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(lm);
        final ProjectsAdapter adapter = new ProjectsAdapter();
        rv.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(ProjectsViewModel.class);
        LiveData<List<Project>> projects = viewModel.getProjects();
        if (projects != null) {
            projects.observe(this, projects1 -> {
                adapter.setProjects(projects1);
                adapter.notifyDataSetChanged();
            });
        }

        FloatingActionButton addButton = findViewById(R.id.add_btn);
        addButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_btn:
                AddProjectActivity.startActivity(ProjectsActivity.this);
                break;
            default:
                break;
        }
    }

    public static void startActivity(Activity activity) {
        Intent i = new Intent(activity, ProjectsActivity.class);
        activity.startActivity(i);
    }
}
