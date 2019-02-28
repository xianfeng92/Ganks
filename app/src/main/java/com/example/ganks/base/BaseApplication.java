package com.example.ganks.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.xforg.gank_core.net.Api;
import com.xforg.gank_core.app.Gank;
import com.xforg.gank_core.utils.GreenDaoHelper;
import com.xforg.gank_core.utils.Utils;

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
        Gank.init(this).withBaseUrl(Api.APP_DOMAIN).configure();
        //初始化GreenDao
        GreenDaoHelper.initDataBase(this);
        Utils.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
