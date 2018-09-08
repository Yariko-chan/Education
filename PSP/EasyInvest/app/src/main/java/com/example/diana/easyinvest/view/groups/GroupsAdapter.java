package com.example.diana.easyinvest.view.groups;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diana.easyinvest.R;
import com.example.diana.easyinvest.model.groups.Group;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.Holder> {

    private List<Group> groups = new ArrayList<>();

    public GroupsAdapter() {
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group_list, parent, false);
        return new Holder(v);
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(groups.get(position));
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_tv) TextView nameTv;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Group group) {
            nameTv.setText(group.getName());
        }
    }
}
