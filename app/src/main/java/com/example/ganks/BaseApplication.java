package com.example.ganks;

import android.app.Application;
import android.content.Context;

import com.example.ganks.api.Api;
import com.example.ganks.delegate.AppDelegate;
import com.example.ganks.global.Gank;
import com.example.ganks.lifecycle.AppLifecycles;

/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class BaseApplication extends Application {
    private AppLifecycles mAppDelegate;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        this.mAppDelegate = new AppDelegate(base);
        this.mAppDelegate.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mAppDelegate.onCreate(this);
        Gank.init(this).withBaseUrl(Api.APP_DOMAIN).configure();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null)
            this.mAppDelegate.onTerminate(this);
    }
}
