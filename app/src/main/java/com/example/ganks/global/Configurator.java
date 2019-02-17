package com.example.ganks.global;
import android.os.Handler;

import java.util.HashMap;

/**
 * Created By apple on 2019/2/17
 * github: https://github.com/xianfeng92
 */
public class Configurator {

    private static final HashMap<String,Object> GANK_CONFIGS = new HashMap<>();
    private static final Handler HANDLER = new Handler();

    private Configurator(){
        GANK_CONFIGS.put(ConfigKeys.CONFIG_READY.name(),false);
        GANK_CONFIGS.put(ConfigKeys.HANDLER.name(),HANDLER);
    }

    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder{
        private static final Configurator INSTANCE = new Configurator();
    }

    public final HashMap<String,Object> getGankConfigs(){
        return GANK_CONFIGS;
    }

    public final void configure(){
        GANK_CONFIGS.put(ConfigKeys.CONFIG_READY.name(),true);
    }

    public final Configurator withWebApiHost(String host){
        //只留下域名，否则无法同步cookie，不能带http://或末尾的/
        String hostName = host
                .replace("http://", "")
                .replace("https://", "");
        hostName = hostName.substring(0,hostName.lastIndexOf("/"));
        GANK_CONFIGS.put(ConfigKeys.WEB_API_HOST.name(),hostName);
        return this;
    }

    public final Configurator withBaseUrl(String url){
        GANK_CONFIGS.put(ConfigKeys.BASE_URL.name(),url);
        return this;
    }

    public final Configurator withLoaderDelayed(int delay){
        GANK_CONFIGS.put(ConfigKeys.LOADER_DELAYED.name(),delay);
        return this;
    }

    private void checkConfiguration(){
        boolean isReady = (boolean) GANK_CONFIGS.get(ConfigKeys.CONFIG_READY.name());
        if (!isReady){
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(String key) {
        checkConfiguration();
        final Object value = GANK_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) GANK_CONFIGS.get(key);
    }

}
