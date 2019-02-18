package com.example.autosize.config;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.autosize.AutoAdaptStrategy;
import com.example.autosize.DefaultAutoAdaptStrategy;
import com.example.autosize.callbacks.ActivityLifecycleCallbacksImpl;
import com.example.autosize.onAdaptListener;
import com.example.autosize.utils.ScreenUtils;

/**
 * Created By zhongxianfeng on 19-2-15
 * github: https://github.com/xianfeng92
 */
public final class AutoSizeConfig {

    private static final String TAG = "AutoSizeConfig";

    private static final String KEY_DESIGN_WIDTH_IN_DP = "design_width_in_dp";
    private static final String KEY_DESIGN_HEIGHT_IN_DP = "design_height_in_dp";
    private Application mApplication;

    /**
     * 最初的 {@link DisplayMetrics#density}
     */
    private float mInitDensity = -1;
    /**
     * 最初的 {@link DisplayMetrics#densityDpi}
     */
    private int mInitDensityDpi;
    /**
     * 最初的 {@link DisplayMetrics#scaledDensity}
     */
    private float mInitScaledDensity;
    /**
     * 最初的 {@link DisplayMetrics#xdpi}
     */
    private float mInitXdpi;
    /**
     * 设计图上的总宽度, 单位 dp
     */
    private int mDesignWidthInDp;
    /**
     * 设计图上的总高度, 单位 dp
     */
    private int mDesignHeightInDp;
    /**
     * 设备的屏幕总宽度, 单位 px
     */
    private int mScreenWidth;
    /**
     * 设备的屏幕总高度, 单位 px, 如果 {@link #isUseDeviceSize} 为 {@code false}, 屏幕总高度会减去状态栏的高度
     */
    private int mScreenHeight;

    /**
     * 状态栏高度, 当 {@link #isUseDeviceSize} 为 {@code false} 时, AndroidAutoSize 会将 {@link #mScreenHeight} 减去状态栏高度
     * AndroidAutoSize 默认使用 {@link ScreenUtils#getStatusBarHeight()} 方法获取状态栏高度
     */
    private int mStatusBarHeight;
    /**
     * 为了保证在不同高宽比的屏幕上显示效果也能完全一致, 所以本方案适配时是以设计图宽度与设备实际宽度的比例或设计图高度与设备实际高度的比例应用到
     * 每个 View 上 (只能在宽度和高度之中选一个作为基准), 从而使每个 View 的高和宽用同样的比例缩放, 避免在与设计图高宽比不一致的设备上出现适配的 View 高或宽变形的问题
     * {@link #isBaseOnWidth} 为 {@code true} 时代表以宽度等比例缩放, {@code false} 代表以高度等比例缩放
     * {@link #isBaseOnWidth} 为全局配置, 默认为 {@code true}, 每个 {@link Activity} 也可以单独选择使用高或者宽做等比例缩放
     */
    private boolean isBaseOnWidth = true;
    /**
     * 此字段表示是否使用设备的实际尺寸做适配
     * {@link #isUseDeviceSize} 为 {@code true} 表示屏幕高度 {@link #mScreenHeight} 包含状态栏的高度
     * {@link #isUseDeviceSize} 为 {@code false} 表示 {@link #mScreenHeight} 会减去状态栏的高度, 默认为 {@code true}
     */
    private boolean isUseDeviceSize = true;

    private boolean isCustomFragment;

    /**
     * {@link #mActivityLifecycleCallbacks} 可用来代替在 BaseActivity 中加入适配代码的传统方式
     * {@link #mActivityLifecycleCallbacks} 这种方案类似于 AOP, 面向接口, 侵入性低, 方便统一管理, 扩展性强, 并且也支持适配三方库的 {@link Activity}
     */
    private ActivityLifecycleCallbacksImpl mActivityLifecycleCallbacks;

    /**
     * 屏幕方向, {@code true} 为纵向, {@code false} 为横向
     */
    private boolean isVertical;

    /**
     * 是否屏蔽系统字体大小对 AndroidAutoSize 的影响, 如果为 {@code true}, App 内的字体的大小将不会跟随系统设置中字体大小的改变
     * 如果为 {@code false}, 则会跟随系统设置中字体大小的改变, 默认为 {@code false}
     */
    private boolean isExcludeFontScale;


    /**
     * 屏幕适配监听器，用于监听屏幕适配时的一些事件
     */
    private onAdaptListener mOnAdaptListener;

    private AutoSizeConfig() {
    }

    // 静态内部类实现的单例模式
    private static class AutoSizeHolder{
        private static final AutoSizeConfig INSTANCE = new AutoSizeConfig();
    }

    public static AutoSizeConfig getInstance(){
        return AutoSizeHolder.INSTANCE;
    }


    public Application getApplication() {
        return mApplication;
    }

    AutoSizeConfig init(Application application) {
        return init(application, true, null);
    }

    AutoSizeConfig init(Application application, boolean isBaseOnWidth){
        return init(application,isBaseOnWidth,null);
    }

    AutoSizeConfig init(final Application application, boolean isBaseOnWidth, AutoAdaptStrategy strategy){
        this.mApplication = application;
        this.isBaseOnWidth = isBaseOnWidth;
        final DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();

        getMetaData(application);

        isVertical = application.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        int[] screenSize = ScreenUtils.getScreenSize(application);
        mScreenWidth = screenSize[0];
        mScreenHeight = screenSize[1];
        mStatusBarHeight = ScreenUtils.getStatusBarHeight();

        mInitDensity = displayMetrics.density;
        mInitDensityDpi = displayMetrics.densityDpi;
        mInitScaledDensity = displayMetrics.scaledDensity;
        mInitXdpi = displayMetrics.xdpi;

        application.registerComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(Configuration newConfig) {
                if (newConfig != null){
                    if (newConfig.fontScale > 0){
                        mInitScaledDensity = Resources.getSystem().getDisplayMetrics().scaledDensity;
                        isVertical = newConfig.orientation == Configuration.ORIENTATION_PORTRAIT;
                        int[] screenSize = ScreenUtils.getScreenSize(application);
                        mScreenWidth = screenSize[0];
                        mScreenHeight = screenSize[1];
                    }
                }
            }

            @Override
            public void onLowMemory() {

            }
        });
        mActivityLifecycleCallbacks = new ActivityLifecycleCallbacksImpl(strategy == null ? new DefaultAutoAdaptStrategy() : strategy);
        application.registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
        return this;
    }

    public AutoSizeConfig setAutoAdaptStrategy(AutoAdaptStrategy strategy){
        mActivityLifecycleCallbacks.setAutoAdaptStrategy(strategy);
        return this;
    }

    public AutoSizeConfig setOnAdaptListener(onAdaptListener onAdaptListener){
        this.mOnAdaptListener = onAdaptListener;
        return this;
    }


    public AutoSizeConfig setBaseOnWidth(boolean baseOnWidth){
        isBaseOnWidth = baseOnWidth;
        return this;
    }

    public AutoSizeConfig setCustomFragment(boolean customFragment){
        isCustomFragment = customFragment;
        return this;
    }

    public AutoSizeConfig setUseDeviceSize(boolean useDeviceSize){
        isUseDeviceSize = useDeviceSize;
        return this;
    }

    public AutoSizeConfig setLog(boolean log){
        // not implement
        return this;
    }

    public boolean isBaseOnWidth(){
        return isBaseOnWidth;
    }

    public boolean isUseDeviceSize(){
        return isUseDeviceSize;
    }

    public int getScreenWidth(){
        return mScreenWidth;
    }

    public int getScreenHeight(){
        return mScreenHeight;
    }

    public int getDesignWidthInDp(){
        return mDesignWidthInDp;
    }

    public int getDesignHeightInDp(){
        return mDesignHeightInDp;
    }

    public float getInitDensity(){
        return mInitDensity;
    }

    public float getInitScaledDensity(){
        return mInitScaledDensity;
    }


    public boolean isVertical(){
        return isVertical;
    }

    public void setVertical(boolean vertical){
        this.isVertical = vertical;
    }

    /**
     * 是否屏蔽系统字体大小对 AndroidAutoSize 的影响, 如果为 {@code true}, App 内的字体的大小将不会跟随系统设置中字体大小的改变
     * 如果为 {@code false}, 则会跟随系统设置中字体大小的改变, 默认为 {@code false}
     *
     * @return {@link #isExcludeFontScale}
     */
    public boolean isExcludeFontScale() {
        return isExcludeFontScale;
    }

    public boolean isSupportCustomFragment(){
        return isCustomFragment;
    }

    public AutoSizeConfig setScrrenWidth(int scrrenWidth){
        this.mScreenWidth = scrrenWidth;
        return this;
    }

    public AutoSizeConfig setScreenHeight(int screenHeight){
        this.mScreenHeight = screenHeight;
        return this;
    }

    public AutoSizeConfig setDesignWidthInDp(int designWidthInDp){
        this.mDesignWidthInDp = designWidthInDp;
        return this;
    }

    public AutoSizeConfig setDesignHeightInDp(int designHeightInDp){
        this.mDesignHeightInDp = designHeightInDp;
        return this;
    }

    public AutoSizeConfig setStatusBarhHeight(int statusBarhHeight){
        this.mStatusBarHeight = statusBarhHeight;
        return this;
    }

    /**
     * 获取使用者在 AndroidManifest 中填写的 Meta 信息
     * <p>
     * Example usage:
     * <pre>
     * <meta-data android:name="design_width_in_dp"
     *            android:value="360"/>
     * <meta-data android:name="design_height_in_dp"
     *            android:value="640"/>
     * </pre>
     *
     * @param context {@link Context}
     */
    private void getMetaData(final Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                PackageManager packageManager = context.getPackageManager();
                ApplicationInfo applicationInfo;
                try {
                    applicationInfo = packageManager.getApplicationInfo(context.getPackageName(),packageManager.GET_META_DATA);
                    if(applicationInfo != null && applicationInfo.metaData != null){
                        if (applicationInfo.metaData.containsKey(KEY_DESIGN_WIDTH_IN_DP)){
                            mDesignWidthInDp = (int) applicationInfo.metaData.get(KEY_DESIGN_WIDTH_IN_DP);
                        }
                        if(applicationInfo.metaData.containsKey(KEY_DESIGN_HEIGHT_IN_DP)){
                            mDesignHeightInDp = (int) applicationInfo.metaData.get(KEY_DESIGN_HEIGHT_IN_DP);
                        }
                    }
                    Log.d(TAG, "run: mDesignWidthInDp:"+mDesignWidthInDp+" "+"mDesignHeightInDp"+mDesignHeightInDp);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
