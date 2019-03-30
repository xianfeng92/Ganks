package com.example.ganks.web.route;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.ganks.web.client.WebChromeClientImpl;
import com.example.ganks.web.client.WebViewClientImpl;

/**
 * Created By apple on 2019/3/3
 * github: https://github.com/xianfeng92
 */
public class WebDelegateImpl extends WebDelegate {
    private static final String TAG = "WebDelegateImpl";


    public static WebDelegateImpl create(String url){
        Log.d(TAG, "create: ");
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(),url);
        final WebDelegateImpl delegate = new WebDelegateImpl();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public IWebViewInitializer setInitializer() {
        return this;
    }

    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        Log.d(TAG, "onBindView: ");
        if(getUrl() != null){
            // 用原生的方式模拟Web跳转并进行页面加载
            Router.getInstance().loadPage(this,getUrl());
        }
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitializer().createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
       final WebViewClientImpl client = new WebViewClientImpl(this);
        return client;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        final WebChromeClientImpl chromeClient = new WebChromeClientImpl();
        return chromeClient;
    }
}
