package com.example.autosize;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created By zhongxianfeng on 19-2-15
 * github: https://github.com/xianfeng92
 */
public interface onAdaptListener {

    /**
     * 在屏幕适配前调用
     *
     * @param target   需要屏幕适配的对象 (可能是 {@link Activity} 或者 {@link Fragment})
     * @param activity 当前 {@link Activity}
     */
    void onAdaptBefore(Object target, Activity activity);

    /**
     * 在屏幕适配后调用
     *
     * @param target   需要屏幕适配的对象 (可能是 {@link Activity} 或者 {@link Fragment})
     * @param activity 当前 {@link Activity}
     */
    void onAdaptAfter(Object target, Activity activity);

}
