package com.example.ganks.callbacks;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import timber.log.Timber;


/**
 * Created By zhongxianfeng on 19-2-11
 * github: https://github.com/xianfeng92
 */

/*
 * 全局监听整个 App 所有 Activity 的生命周期 (包括三方库), 并可向其生命周期内插入任意代码
 * */

public class MyActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Timber.d((activity.getLocalClassName()+" -onActivityCreated"));
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        Timber.d((activity.getLocalClassName()+" -onActivityResumed"));
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Timber.d((activity.getLocalClassName()+" -onActivitySaveInstanceState"));
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Timber.d((activity.getLocalClassName()+" -onActivityDestroyed"));
    }
}
