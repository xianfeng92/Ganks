package com.example.ganks.base;

import android.app.Application;
import android.content.Context;
import com.example.ganks.MessageEvent;
import com.example.ganks.internal.di.components.ApplicationComponent;
import com.example.ganks.internal.di.components.DaggerApplicationComponent;
import com.example.ganks.internal.di.modules.ApplicationModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.xforg.gank_core.net.Api;
import com.xforg.gank_core.app.Gank;
import com.example.data.GreenDaoHelper;
import com.xforg.gank_core.utils.Utils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import ImageLoader.ImageLoader;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class BaseApplication extends Application {

    private  static ApplicationComponent applicationComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
        Fragmentation.builder()
                // 设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(true) // 实际场景建议.debug(BuildConfig.DEBUG)
                /**
                 * 可以获取到{@link me.yokeyword.fragmentation.exception.AfterSaveStateTransactionWarning}
                 * 在遇到After onSaveInstanceState时，不会抛出异常，会回调到下面的ExceptionHandler
                 */
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 以Bugtags为例子: 把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                })
                .install();
        EventBus.getDefault().register(this);
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

    //当进入欢迎画面时，再去初期化第三方框架
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void lazyInit(MessageEvent messageEvent) {
        if (110 == (Integer) messageEvent.getMessage()){
            Gank.init(this).withBaseUrl(Api.APP_DOMAIN).configure();
            //初始化GreenDao
            GreenDaoHelper.initDataBase(this);
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
    }

    private void initializeInjector() {
        this.applicationComponent= DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public  static ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }
}
