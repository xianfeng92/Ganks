package com.example.ganks;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.example.ganks.fragment.FragmentLifecycle;

/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class BaseApplication extends Application {

    private FragmentManager.FragmentLifecycleCallbacks mFragmentLifecycle;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mFragmentLifecycle = new FragmentLifecycle();
    }
}
