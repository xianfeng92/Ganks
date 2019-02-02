package com.example.ganks;

import android.app.Application;
import android.content.Context;

import com.example.ganks.lifecycle.AppLifecycles;

/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class BaseApplication extends Application implements AppLifecycles {
    private AppLifecycles mAppDelegate;
    private static final String TAG = "BaseApplication";


    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        this.mAppDelegate = new AppDelegate();
        this.mAppDelegate.attachBaseContext(base);
    }

    @Override
    public void onCreate(Application application) {
        this.mAppDelegate.onCreate(this);
    }

    @Override
    public void onTerminate(Application application) {
        super.onTerminate();
        if (mAppDelegate != null){
            this.mAppDelegate.onTerminate(application);
        }

    }
}
