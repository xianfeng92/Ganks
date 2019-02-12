package com.example.ganks.callbacks;

import android.app.Application;
import android.content.Context;
import com.example.ganks.BuildConfig;
import com.example.ganks.lifecycle.AppLifecycles;
import timber.log.Timber;


/**
 * Created By zhongxianfeng on 19-2-11
 * github: https://github.com/xianfeng92
 */
public class MyAppLifecyclesCallbacks implements AppLifecycles {
    @Override
    public void attachBaseContext(Context base) {

    }

    @Override
    public void onCreate(Application application) {
        if (BuildConfig.DEBUG){
            //开启Timber日志打印
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public void onTerminate(Application application) {
        Timber.d((application+"onTerminate"));
    }
}
