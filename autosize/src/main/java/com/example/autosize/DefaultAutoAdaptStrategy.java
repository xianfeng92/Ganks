package com.example.autosize;

import android.app.Activity;
import android.util.Log;

/**
 * Created By zhongxianfeng on 19-2-15
 * github: https://github.com/xianfeng92
 */
public class DefaultAutoAdaptStrategy implements AutoAdaptStrategy {
    private static final String TAG = "DefaultAutoAdaptStrateg";
    @Override
    public void appleyAdapt(Object target, Activity activity) {
        Log.d(TAG, "appleyAdapt: ");
        //开始适配
        AutoSize.autoConvertDensityOfGlobal(activity);
    }
}
