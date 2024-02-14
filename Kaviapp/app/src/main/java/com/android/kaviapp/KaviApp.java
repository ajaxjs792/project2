package com.android.kaviapp;

import android.app.Application;
import android.content.Context;

public class KaviApp extends Application {
    private static KaviApp mInstance;

    public static Context getStaticContext() {
        return KaviApp.getInstance().getApplicationContext();
    }

    public static KaviApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
