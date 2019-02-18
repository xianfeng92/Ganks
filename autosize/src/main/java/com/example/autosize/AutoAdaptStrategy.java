package com.example.autosize;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;

/**
 * Created By zhongxianfeng on 19-2-15
 * github: https://github.com/xianfeng92
 */
public interface AutoAdaptStrategy {
    /**
     * 开始执行屏幕适配逻辑
     *
     * @param target   需要屏幕适配的对象 (可能是 {@link Activity} 或者 {@link Fragment})
     * @param activity 需要拿到当前的 {@link Activity} 才能修改 {@link DisplayMetrics#density}
     */
    void appleyAdapt(Object target, Activity activity);

}
