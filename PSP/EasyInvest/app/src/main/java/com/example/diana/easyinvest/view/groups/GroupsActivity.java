package com.example.diana.easyinvest.view.groups;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.diana.easyinvest.R;
import com.example.diana.easyinvest.model.groups.Group;
import com.example.diana.easyinvest.view.NavMenuActivity;
import com.example.diana.easyinvest.viewmodels.GroupsViewModel;

import java.util.List;

import butterknife.BindView;

public class GroupsActivity extends NavMenuActivity implements View.OnClickListener {

    @BindView(R.id.list) RecyclerView list;
    @BindView(R.id.add_btn) FloatingActionButton addBtn;

    private GroupsAdapter adapter;
    private GroupsViewModel viewModel;

    public static void startActivity(Activity activity) {
        Intent i = new Intent(activity, GroupsActivity.class);
        activity.startActivity(i);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.groups);

        adapter = new GroupsAdapter();
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(lm);
        list.setAdapter(adapter);
        addBtn.setOnClickListener(this);

        viewModel = ViewModelProviders.of(this)
                .get(GroupsViewModel.class);
        LiveData<List<Group>> groups = viewModel.getGroups();
        if (groups != null) {
            groups.observe(this, groupList -> {
                adapter.setGroups(groupList);
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn:
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_add_group, null);
                final EditText et = dialogView.findViewById(R.id.editText);

                new AlertDialog.Builder(this)
                        .setTitle(R.string.group_name)
                        .setView(dialogView)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewModel.insert(new Group(et.getText().toString()));
                            }
                        })
                        .show();
                break;
        }
    }
}
