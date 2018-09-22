package com.gmail.f.d.ganeeva.easyinvest;

import android.app.Application;
import android.content.Context;

public class App extends Application{

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getContext();
    }

    public static Context getContext() {
        return context;
    }
}
