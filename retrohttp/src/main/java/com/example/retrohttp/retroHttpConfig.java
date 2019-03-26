package com.example.retrohttp;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created By zhongxianfeng on 19-3-26
 * github: https://github.com/xianfeng92
 */
public class retroHttpConfig {

    /*请求基础路径*/
    String baseUrl;
    /*超时时长*/
    long timeout;
    /*时间单位*/
    TimeUnit timeUnit;
    /*全局上下文*/
    Context context;
    /*全局Handler*/
    Handler handler;
    /*请求参数*/
    Map<String, Object> parameter;
    /*header*/
    Map<String, Object> header;
    /*是否显示Log*/
    boolean showLog;

    public static retroHttpConfig getInstance() {
        return retroHttpConfig.Holder.INSTANCE;
    }

    private static class Holder {
        private static retroHttpConfig INSTANCE = new retroHttpConfig();
    }

    private retroHttpConfig() {
        timeout = 60;//默认60秒
        timeUnit = TimeUnit.SECONDS;//默认秒
        showLog = true;//默认打印LOG
    }

    /*请求基础路径*/
    public retroHttpConfig baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    /*基础参数*/
    public retroHttpConfig baseParameter(Map<String, Object> parameter) {
        this.parameter = parameter;
        return this;
    }

    public Map<String, Object> getBaseParameter() {
        return parameter;
    }

    /*基础Header*/
    public retroHttpConfig baseHeader(Map<String, Object> header) {
        this.header = header;
        return this;
    }

    public Map<String, Object> getBaseHeader() {
        return header;
    }

    /*超时时长*/
    public retroHttpConfig timeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    public long getTimeout() {
        return timeout;
    }

    /*是否显示LOG*/
    public retroHttpConfig showLog(boolean showLog) {
        this.showLog = showLog;
        return this;
    }

    public boolean isShowLog() {
        return showLog;
    }

    /*时间单位*/
    public retroHttpConfig timeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    /*Handler*/
    public Handler getHandler() {
        return handler;
    }

    /*Context*/
    public Context getContext() {
        return context;
    }

    /*初始化全局上下文*/
    public retroHttpConfig init(Application app) {
        this.context = app.getApplicationContext();
        this.handler = new Handler(Looper.getMainLooper());
        return this;
    }
}
