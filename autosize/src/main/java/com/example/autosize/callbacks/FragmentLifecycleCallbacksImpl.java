package com.example.autosize.callbacks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.autosize.AutoAdaptStrategy;

/**
 * Created By zhongxianfeng on 19-2-15
 * github: https://github.com/xianfeng92
 */
public class FragmentLifecycleCallbacksImpl extends FragmentManager.FragmentLifecycleCallbacks {

    private AutoAdaptStrategy autoAdaptStrategy;

    public FragmentLifecycleCallbacksImpl(AutoAdaptStrategy strategy){
        this.autoAdaptStrategy = strategy;
    }

    @Override
    public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);
        if(autoAdaptStrategy != null){
            autoAdaptStrategy.appleyAdapt(f,f.getActivity());
        }
    }

    public void setAutoAdaptStrategy(AutoAdaptStrategy strategy){
        this.autoAdaptStrategy = strategy;
    }
}
