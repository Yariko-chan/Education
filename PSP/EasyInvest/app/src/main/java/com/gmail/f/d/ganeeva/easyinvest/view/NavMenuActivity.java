package com.gmail.f.d.ganeeva.easyinvest.view;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gmail.f.d.ganeeva.easyinvest.R;
import com.gmail.f.d.ganeeva.easyinvest.view.companies.CompaniesActivity;
import com.gmail.f.d.ganeeva.easyinvest.view.groups.GroupsActivity;
import com.gmail.f.d.ganeeva.easyinvest.view.projects.ProjectsActivity;

public abstract class NavMenuActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onStart() {
        super.onStart();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        int white = getResources().getColor(R.color.colorWhiteText);
        toolbar.setTitleTextColor(white);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_burger);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        mDrawerLayout.closeDrawers();
        switch ((item.getItemId())) {
            case R.id.projects:
                ProjectsActivity.startActivity(this);
                break;
            case R.id.companies:
                CompaniesActivity.startActivity(this);
                break;
            case R.id.groups:
                GroupsActivity.startActivity(this);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
