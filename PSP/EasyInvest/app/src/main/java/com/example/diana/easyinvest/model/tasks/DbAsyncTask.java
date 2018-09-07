package com.example.diana.easyinvest.model.tasks;

import android.os.AsyncTask;

public abstract class DbAsyncTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        return doAsync();
    }

    protected abstract Void doAsync();
}
