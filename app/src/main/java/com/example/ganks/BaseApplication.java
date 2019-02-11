package com.example.ganks;

import android.app.Application;

import com.example.ganks.lifecycle.ActivityLifecycle;

/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class BaseApplication extends Application {
    private ActivityLifecycle activityLifecycle;
    private static final String TAG = "BaseApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        activityLifecycle = new ActivityLifecycle();
        registerActivityLifecycleCallbacks(activityLifecycle);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (activityLifecycle != null)
        unregisterActivityLifecycleCallbacks(activityLifecycle);
    }
}
