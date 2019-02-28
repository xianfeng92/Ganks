package com.example.ganks.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.example.ganks.MessageEvent;
import com.xforg.gank_core.net.Api;
import com.xforg.gank_core.app.Gank;
import com.xforg.gank_core.utils.GreenDaoHelper;
import com.xforg.gank_core.utils.Utils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class BaseApplication extends Application {

    private static final String TAG = "EnterActivity";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
        // 必须在欢迎界面出来前初始化，所以不能lazyInit
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


    //当进入欢迎画面时，再去初期化第三方框架
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void lazyInit(MessageEvent messageEvent) {
        if (110 == (Integer) messageEvent.getMessage()){
            Log.d(TAG, "lazyInit: LOAD");
            Gank.init(this).withBaseUrl(Api.APP_DOMAIN).configure();
            //初始化GreenDao
            GreenDaoHelper.initDataBase(this);
            Utils.init(this);
        }
    }
}
