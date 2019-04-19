package com.example.ganks.base;

import android.app.Application;
import android.content.Context;
import com.example.ganks.di.components.ApplicationComponent;
import com.example.ganks.di.components.DaggerApplicationComponent;
import com.example.ganks.di.modules.ApplicationModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.xforg.easyimage.ImageLoader;
import com.example.data.HelperImpl.GreenDaoHelperImpl;
import com.xforg.gank_core.utils.Utils;
import org.greenrobot.eventbus.EventBus;
import me.yokeyword.fragmentation.Fragmentation;

/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class GanksApplication extends Application {

    private  static ApplicationComponent applicationComponent;
    private static  GanksApplication instance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initializeInjector();
        Fragmentation.builder()
                // 设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(true) // 实际场景建议.debug(BuildConfig.DEBUG)
                .install();
        //初始化GreenDao
        GreenDaoHelperImpl.initDataBase(this);
        Utils.init(this);
        ImageLoader.init(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ImageLoader.clearAllMemoryCaches();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        ImageLoader.trimMemory(level);
    }

    private void initializeInjector() {
        this.applicationComponent= DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public  static ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }

    public static synchronized GanksApplication getInstance() {
        return instance;
    }
}
