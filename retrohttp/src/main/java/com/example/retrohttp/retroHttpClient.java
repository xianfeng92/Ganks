package com.example.retrohttp;

import android.text.TextUtils;
import com.example.retrohttp.api.Api;
import com.example.retrohttp.callback.HttpCallback;
import com.example.retrohttp.callback.UploadCallback;
import com.example.retrohttp.cancel.RequestManagerImpl;
import com.example.retrohttp.observer.HttpObserver;
import com.example.retrohttp.retrofit.Method;
import com.example.retrohttp.retrofit.RetrofitUtils;
import com.example.retrohttp.utils.RequestUtils;
import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * Created By zhongxianfeng on 19-3-26
 * github: https://github.com/xianfeng92
 */
public class retroHttpClient {

    /*请求方式*/
    private Method method;
    /*请求参数*/
    private Map<String, Object> parameter;
    /*header*/
    private Map<String, Object> header;
    /*HttpCallback*/
    private HttpCallback httpCallback;
    /*标识请求的TAG*/
    private String tag;
    /*文件map*/
    private Map<String, File> fileMap;
    /*上传文件回调*/
    private UploadCallback uploadCallback;
    /*基础URL*/
    private String baseUrl;
    /*apiUrl*/
    private String apiUrl;
    /*String参数*/
    private String bodyString;
    /*是否强制JSON格式*/
    boolean isJson;

    /*构造函数*/
    public retroHttpClient(retroHttpClientBuilder builder) {
        this.parameter = builder.parameter;
        this.header = builder.header;
        this.tag = builder.tag;
        this.fileMap = builder.fileMap;
        this.baseUrl = builder.baseUrl;
        this.apiUrl = builder.apiUrl;
        this.isJson = builder.isJson;
        this.bodyString = builder.bodyString;
        this.method = builder.method;
    }

    /*普通Http请求*/
    public void request(HttpCallback httpCallback) {
        this.httpCallback = httpCallback;
        if (httpCallback == null) {
            throw new NullPointerException("HttpObserver must not null!");
        } else {
            doRequest();
        }
    }

    /*取消网络请求*/
    public void cancel() {
        if (httpCallback != null) {
            httpCallback.cancel();
        }
        if (uploadCallback != null) {
            uploadCallback.cancel();
        }
    }

    /*请求是否已经取消*/
    public boolean isCanceled() {
        boolean isCanceled = true;
        if (httpCallback != null) {
            isCanceled = httpCallback.isDisposed();
        }
        if (uploadCallback != null) {
            isCanceled = uploadCallback.isDisposed();
        }
        return isCanceled;
    }

    /**
     * 根据tag取消请求
     *
     * @param tag
     */
    public static void cancel(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return;
        }
        RequestManagerImpl.getInstance().cancel(tag);
    }

    /**
     * 取消全部请求
     */
    public static void cancelAll() {
        RequestManagerImpl.getInstance().cancelAll();
    }

    /*执行请求*/
    private void doRequest() {
        /*设置请求唯一标识*/
        httpCallback.setTag(TextUtils.isEmpty(tag) ? String.valueOf(System.currentTimeMillis()) : tag);

        /*header处理*/
        disposeHeader();

        /*Parameter处理*/
        disposeParameter();

        /*请求方式处理*/
        Observable apiObservable = disposeApiObservable();

    }

    /*处理ApiObservable*/
    private Observable disposeApiObservable() {

        Observable apiObservable = null;

        /*是否JSON格式提交参数*/
        boolean hasBodyString = !TextUtils.isEmpty(bodyString);
        RequestBody requestBody = null;
        if (hasBodyString) {
            String mediaType = isJson ? "application/json; charset=utf-8" : "text/plain;charset=utf-8";
            requestBody = RequestBody.create(okhttp3.MediaType.parse(mediaType), bodyString);
        }
        /*Api接口*/
        Api apiService = RetrofitUtils.getInstance().getRetrofit(getBaseUrl(), header, httpCallback).create(Api.class);
        /*未指定默认GET*/
        if (method == null) method = Method.GET;
        switch (method) {
            case GET:
                apiObservable = apiService.get(disposeApiUrl(), parameter, header);
                break;
            case POST:
                if (hasBodyString)
                    apiObservable = apiService.post(disposeApiUrl(), requestBody, header);
                else
                    apiObservable = apiService.post(disposeApiUrl(), parameter, header);
                break;
            case DELETE:
                apiObservable = apiService.delete(disposeApiUrl(), parameter, header);
                break;
            case PUT:
                apiObservable = apiService.put(disposeApiUrl(), parameter, header);
                break;
        }
        return apiObservable;
    }

    /*ApiUrl处理*/
    private String disposeApiUrl() {
        return TextUtils.isEmpty(apiUrl) ? "" : apiUrl;
    }

    /*获取基础URL*/
    private String getBaseUrl() {
        //如果没有重新指定URL则是用默认配置
        return TextUtils.isEmpty(baseUrl) ? retroHttpConfig.getInstance().getBaseUrl() : baseUrl;
    }

    /*处理Header*/
    private void disposeHeader() {
        /*header空处理*/
        if (header == null) {
            header = new TreeMap<>();
        }
        //添加基础 Header
        Map<String, Object> baseHeader = retroHttpConfig.getInstance().getBaseHeader();
        if (baseHeader != null && baseHeader.size() > 0) {
            header.putAll(baseHeader);
        }

        if (!header.isEmpty()) {
            //处理header中文或者换行符出错问题
            for (String key : header.keySet()) {
                header.put(key, RequestUtils.getHeaderValueEncoded(header.get(key)));
            }
        }
    }

    /*处理 Parameter*/
    private void disposeParameter() {
        /*空处理*/
        if (parameter == null) {
            parameter = new TreeMap<>();
        }
        //添加基础 Parameter
        Map<String, Object> baseParameter = retroHttpConfig.getInstance().getBaseParameter();
        if (baseParameter != null && baseParameter.size() > 0) {
            parameter.putAll(baseParameter);
        }
    }
}
