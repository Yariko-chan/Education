package com.example.diana.easyinvest.view.projects;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.example.diana.easyinvest.R;
import com.example.diana.easyinvest.view.BaseActivity;
import com.example.diana.easyinvest.view.NavMenuActivity;
import com.example.diana.easyinvest.view.ViewActivity;
import com.example.diana.easyinvest.view.companies.AddCompanyActivity;

public class ProjectViewActivity extends ViewActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_project_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void startActivity(Context context, int projectId) {
        Intent intent = new Intent(context, ProjectViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected String getScreenTitle() {
        return "";
    }
}
