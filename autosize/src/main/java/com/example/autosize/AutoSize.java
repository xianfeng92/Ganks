package com.example.autosize;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.autosize.config.AutoSizeConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created By zhongxianfeng on 19-2-15
 * github: https://github.com/xianfeng92
 */
public class AutoSize {
    private static final String TAG = "AutoSize";

    private static Map<String, DisplayMetricsInfo> mCache = new ConcurrentHashMap<>();

    private AutoSize(){
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static void autoConvertDensityOfGlobal(Activity activity){
        if(AutoSizeConfig.getInstance().isBaseOnWidth()){
            AutoConvertDensityBaseOnWidth(activity, AutoSizeConfig.getInstance().getDesignWidthInDp());
        }else {
            AutoConvertDensityBaseOnHeight(activity, AutoSizeConfig.getInstance().getDesignHeightInDp());
        }
    }

    public static void AutoConvertDensityBaseOnWidth(Activity activity, int designWidthInDp){
        autoConvertDensity(activity,designWidthInDp,true);
    }

    public static void AutoConvertDensityBaseOnHeight(Activity activity, int designHeightInDp){
        autoConvertDensity(activity,designHeightInDp,false);
    }

    /**
     * 这里是今日头条适配方案的核心代码, 核心在于根据当前设备的实际情况做自动计算并转换 {@link DisplayMetrics#density}、
     * {@link DisplayMetrics#scaledDensity}、{@link DisplayMetrics#densityDpi} 这三个值, 额外增加 {@link DisplayMetrics#xdpi}
     * 以支持单位 {@code pt}、{@code in}、{@code mm}
     *
     * @param activity      {@link Activity}
     * @param sizeInDp      设计图上的设计尺寸, 单位 dp, 如果 {@param isBaseOnWidth} 设置为 {@code true},
     *                      {@param sizeInDp} 则应该填写设计图的总宽度, 如果 {@param isBaseOnWidth} 设置为 {@code false},
     *                      {@param sizeInDp} 则应该填写设计图的总高度
     * @param isBaseOnWidth 是否按照宽度进行等比例适配, {@code true} 为以宽度进行等比例适配, {@code false} 为以高度进行等比例适配
     * @see <a href="https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA">今日头条官方适配方案</a>
     */
    public static void autoConvertDensity(Activity activity,float sizeInDp, boolean isBaseOnWidth){
        int screenSize = isBaseOnWidth ? AutoSizeConfig.getInstance().getScreenWidth()
                : AutoSizeConfig.getInstance().getScreenHeight();
        Log.d(TAG, "autoConvertDensity: screenSize:"+screenSize);
        String key = sizeInDp  + "|" + isBaseOnWidth + "|"
                + AutoSizeConfig.getInstance().isUseDeviceSize() + "|"
                + AutoSizeConfig.getInstance().getInitScaledDensity() + "|"
                + screenSize;
        Log.d(TAG, "autoConvertDensity: key:"+key);
        DisplayMetricsInfo displayMetricsInfo = mCache.get(key);

        float targetDensity = 0;
        int targetDensityDpi = 0;
        float targetScaleDensity = 0;

        if(displayMetricsInfo == null){
            if(isBaseOnWidth){
                targetDensity = AutoSizeConfig.getInstance().getScreenWidth()*1.0f / sizeInDp;
            }else {
                targetDensity = AutoSizeConfig.getInstance().getScreenHeight()*1.0f / sizeInDp;
            }
            float scale = AutoSizeConfig.getInstance().isExcludeFontScale() ? 1
                    : AutoSizeConfig.getInstance().getInitScaledDensity()*1.0f / AutoSizeConfig.getInstance().getInitDensity();
            targetScaleDensity = targetDensity * scale;
            targetDensityDpi = (int) (targetDensity*160);
            mCache.put(key,new DisplayMetricsInfo(targetDensity,targetDensityDpi,targetScaleDensity));
        }else {
            targetDensity = displayMetricsInfo.getDensity();
            targetDensityDpi = displayMetricsInfo.getDensityDpi();
            targetScaleDensity = displayMetricsInfo.getScaledDensity();
        }
        setDensity(activity,targetDensity,targetDensityDpi,targetScaleDensity);
    }

    private static void setDensity(Activity activity,float density, int densityDpi,float scaleDensity){
        DisplayMetrics appdisplayMetrics = AutoSizeConfig.getInstance().getApplication().getResources().getDisplayMetrics();
        appdisplayMetrics.density = density;
        appdisplayMetrics.densityDpi = densityDpi;
        appdisplayMetrics.scaledDensity = scaleDensity;

        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = density;
        activityDisplayMetrics.densityDpi = densityDpi;
        activityDisplayMetrics.scaledDensity = scaleDensity;
    }
}
