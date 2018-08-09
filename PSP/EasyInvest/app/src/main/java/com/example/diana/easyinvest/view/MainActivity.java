package com.example.diana.easyinvest.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.diana.easyinvest.R;
import com.example.diana.easyinvest.model.Project;
import com.example.diana.easyinvest.viewmodels.ProjectsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends NavMenuActivity implements View.OnClickListener {

    private EditText addEditText;
    private ProjectsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = findViewById(R.id.list);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(lm);
        final ProjectsAdapter adapter = new ProjectsAdapter();
        rv.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(ProjectsViewModel.class);
        LiveData<List<Project>> projects = viewModel.getProjects();
        if (projects != null) {
            projects.observe(this, new Observer<List<Project>>() {
                @Override
                public void onChanged(@Nullable List<Project> projects) {
                    adapter.setProjects(projects);
                    adapter.notifyDataSetChanged();
                }
            });
        }

        FloatingActionButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_button :
                AddProjectActivity.startActivity(MainActivity.this);
                break;
            default:
                break;
        }
    }
}
