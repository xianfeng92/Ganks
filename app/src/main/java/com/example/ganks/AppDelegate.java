package com.example.ganks;

import android.app.Application;
import android.content.Context;

import com.example.ganks.lifecycle.ActivityLifecycle;
import com.example.ganks.lifecycle.AppLifecycles;

/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class AppDelegate implements AppLifecycles {

    private Application application;
    protected ActivityLifecycle mActivityLifecycle = new ActivityLifecycle();

    @Override
    public void attachBaseContext(Context base) {
    }

    @Override
    public void onCreate(Application application) {
        this.application = application;
        application.registerActivityLifecycleCallbacks(mActivityLifecycle);
    }

    @Override
    public void onTerminate(Application application) {
        if (mActivityLifecycle != null) {
            application.unregisterActivityLifecycleCallbacks(mActivityLifecycle);
        }
    }
}
