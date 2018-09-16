package com.example.diana.easyinvest.view.projects;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diana.easyinvest.R;
import com.example.diana.easyinvest.model.projects.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        return new ProjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectHolder holder, int position) {
        holder.bind(projects.get(position));
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public class ProjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.name_tv)        TextView nameTV;
        @BindView(R.id.description_tv) TextView descriptionTV;
        @BindView(R.id.investments_tv) TextView investmentTV;
        @BindView(R.id.duration_tv)    TextView durationTV;

        private Context c;
        private long id;

        public ProjectHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            c = view.getContext();
            view.setOnClickListener(this);
        }

        public void bind(Project project) {
            id = project.getId();
            nameTV.setText(project.getName());
            descriptionTV.setText(project.getDescription());
            investmentTV.setText(String.format(Locale.US, c.getString(R.string.money_format), project.getFlows()[0]));
            durationTV.setText(String.format(Locale.US, c.getString(R.string.duration_format), project.getDuration()));
        }

        @Override
        public void onClick(View v) {
            ProjectViewActivity.startActivity(c, id);
        }
    }
}
