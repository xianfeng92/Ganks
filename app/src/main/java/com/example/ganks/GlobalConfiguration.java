package com.example.ganks;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.example.ganks.callbacks.MyActivityLifecycleCallbacks;
import com.example.ganks.callbacks.MyAppLifecyclesCallbacks;
import com.example.ganks.callbacks.MyFragmentLifeCycleCallbacks;
import com.example.ganks.lifecycle.AppLifecycles;

import java.util.List;

/**
 * Created By zhongxianfeng on 19-2-11
 * github: https://github.com/xianfeng92
 */
public class GlobalConfiguration implements ConfigModule {

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        //向App的生命周期中注入一些自定义逻辑
        lifecycles.add(new MyAppLifecyclesCallbacks());
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        //向Activity的生命周期中注入一些自定义逻辑
        lifecycles.add(new MyActivityLifecycleCallbacks());
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
       //向Fragment的生命周期中注入一些自定义逻辑
        lifecycles.add(new MyFragmentLifeCycleCallbacks());
    }

}
