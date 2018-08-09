package com.example.diana.easyinvest.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diana.easyinvest.R;
import com.example.diana.easyinvest.model.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectHolder> {

    private List<Project> projects = new ArrayList<>();

    public ProjectsAdapter() {
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @NonNull
    @Override
    public ProjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_projects_list, parent, false);
        ProjectHolder holder = new ProjectHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectHolder holder, int position) {
        holder.bind(projects.get(position));
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public class ProjectHolder extends RecyclerView.ViewHolder {

        private final TextView nameTV;

        public ProjectHolder(View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.name_tv);
        }

        public void bind(Project project) {
            nameTV.setText(project.getName());
        }
    }
}
