package com.example.ganks.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created By apple on 2019/2/17
 * github: https://github.com/xianfeng92
 */
public final class Gank {

    public final Configurator init(Context context){
        Configurator.getInstance().getGankConfigs().
                put(ConfigKeys.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }


    public static <T> T getConfiguration(String key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Application getApplicationContext(){
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT.name());
    }

    public static Handler getHandler(){
        return getConfiguration(ConfigKeys.HANDLER.name());
    }

}
