package com.example.alarmtest;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static MyApplication singleton;

    public static Context getInstance() {
        if (null == singleton) {
            singleton = new MyApplication();
        }
        return singleton;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }
}
