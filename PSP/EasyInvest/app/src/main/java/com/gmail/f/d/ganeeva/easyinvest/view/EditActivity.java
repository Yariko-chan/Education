package com.gmail.f.d.ganeeva.easyinvest.view;

import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gmail.f.d.ganeeva.easyinvest.R;

public abstract class EditActivity extends BaseActivity {

    @Override
    protected void onStart() {
        super.onStart();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(this::onOptionsItemSelected);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_back);
        actionbar.setTitle(getScreenTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackClicked();
                return true;
            case R.id.action_save:
                onSaveClicked();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onBackClicked() {
        finish();
    }

    protected void onSaveClicked() {
        if (checkCorrectness()) {
            if (save()) {
                finish();
            }
        }
    }

    @StringRes
    protected abstract int getScreenTitle();

    protected abstract boolean checkCorrectness();
    protected abstract boolean save();
}
