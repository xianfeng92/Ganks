package com.example.ganks.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.example.ganks.ActivityDelegate;
import com.example.ganks.ActivityDelegateImpl;
import com.example.ganks.callbacks.MyFragmentLifeCycleCallbacks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    private FragmentLifecycle fragmentLifecycle;
    private List<FragmentManager.FragmentLifecycleCallbacks> mFragmentLifecycles = new ArrayList<>();


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activity.getIntent() != null) {
            ActivityDelegate activityDelegate = this.fetchActivityDelegate(activity);
            if (activityDelegate == null) {
                activityDelegate = new ActivityDelegateImpl(activity);
                activity.getIntent().putExtra("activity_delegate", (Parcelable)activityDelegate);
            }

            ((ActivityDelegate)activityDelegate).onCreate(savedInstanceState);
        }
        if (fragmentLifecycle == null) {
            fragmentLifecycle = new FragmentLifecycle();
        }
        ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(fragmentLifecycle, true);
        //添加Activity中相关fragment的监听
        mFragmentLifecycles.add(new MyFragmentLifeCycleCallbacks());
        for(FragmentManager.FragmentLifecycleCallbacks lifecycle : mFragmentLifecycles){
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(lifecycle, true);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ActivityDelegate activityDelegate = this.fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onStart();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ActivityDelegate activityDelegate = this.fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onResume();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ActivityDelegate activityDelegate = this.fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onPause();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ActivityDelegate activityDelegate = this.fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onStop();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        ActivityDelegate activityDelegate = this.fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityDelegate activityDelegate = this.fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onDestroy();
            activity.getIntent().removeExtra("activity_delegate");
        }
    }

    private ActivityDelegate fetchActivityDelegate(Activity activity) {
        ActivityDelegate activityDelegate = null;
        if (activity.getIntent() != null) {
            activityDelegate = (ActivityDelegate)activity.getIntent().getParcelableExtra("activity_delegate");
        }

        return activityDelegate;
    }
}
