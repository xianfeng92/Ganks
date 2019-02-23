package com.example.ganks.base;

import android.app.Application;
import android.content.Context;

import com.xforg.gank_core.net.Api;
import com.xforg.gank_core.app.Gank;

/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class BaseApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Gank.init(this).withBaseUrl(Api.APP_DOMAIN).configure();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}