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
