package com.example.autosize.callbacks;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.autosize.AutoAdaptStrategy;
import com.example.autosize.config.AutoSizeConfig;

/**
 * Created By zhongxianfeng on 19-2-15
 * github: https://github.com/xianfeng92
 */
public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "ActivityLifecycleCallba";

    private AutoAdaptStrategy autoAdaptStrategy;
    private FragmentLifecycleCallbacksImpl fragmentLifecycleCallbacks;

    public ActivityLifecycleCallbacksImpl(AutoAdaptStrategy strategy){
        this.autoAdaptStrategy = strategy;
        fragmentLifecycleCallbacks = new FragmentLifecycleCallbacksImpl(strategy);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (AutoSizeConfig.getInstance().isSupportCustomFragment()){
            if (activity instanceof FragmentActivity){
                ((FragmentActivity)activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks,true);
            }
        }
        if(autoAdaptStrategy != null){
            autoAdaptStrategy.appleyAdapt(activity,activity);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.d(TAG, "onActivityStarted: ");
        if(autoAdaptStrategy != null){
            autoAdaptStrategy.appleyAdapt(activity,activity);
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public void setAutoAdaptStrategy(AutoAdaptStrategy strategy){
        this.autoAdaptStrategy = strategy;
    }
}
