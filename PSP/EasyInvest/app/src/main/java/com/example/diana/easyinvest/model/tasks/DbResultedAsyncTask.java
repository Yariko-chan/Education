package com.example.diana.easyinvest.model.tasks;

import android.os.AsyncTask;

public abstract class DbResultedAsyncTask extends AsyncTask<Void, Void, Long> {


    @Override
    protected Long doInBackground(Void... voids) {
        return doAsync();
    }

    @Override
    protected void onPostExecute(Long result) {
        super.onPostExecute(result);
        onResult(result);
    }

    protected abstract void onResult(Long result);

    protected abstract Long doAsync();
}
