package com.example.ganks;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.ganks.lifecycle.ActivityLifecycle;
import com.example.ganks.lifecycle.AppLifecycles;
import com.example.ganks.lifecycle.FragmentLifecycle;

/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class BaseApplication extends Application implements AppLifecycles {
    private Application mApplication;
    private static final String TAG = "BaseApplication";

    private ActivityLifecycle activityLifecycle;


    @Override
    public void attachBaseContext(Context base) {

    }

    @Override
    public void onCreate(Application application) {
        this.mApplication = application;
    }

    @Override
    public void onTerminate(Application application) {

    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
        activityLifecycle = new ActivityLifecycle();
        mApplication.registerActivityLifecycleCallbacks(activityLifecycle);
    }
}
