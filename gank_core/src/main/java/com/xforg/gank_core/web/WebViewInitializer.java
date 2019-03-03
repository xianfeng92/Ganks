package com.xforg.gank_core.web;

import android.os.Build;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created By apple on 2019/3/3
 * github: https://github.com/xianfeng92
 */
public class WebViewInitializer {

    public WebView createWebView(WebView webView) {
        WebView.setWebContentsDebuggingEnabled(true);


        //cookie
        final CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }
        CookieManager.setAcceptFileSchemeCookies(true);

        //禁止横向滚动
        webView.setHorizontalScrollBarEnabled(false);

        //不能纵向滚动
        webView.setVerticalScrollBarEnabled(false);

        webView.setDrawingCacheEnabled(true);
        //屏蔽长按事件
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        //初始化WebSettings
        final WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        final String ua = webSettings.getUserAgentString();
        webSettings.setUserAgentString(ua + "Gank");
        //隐藏缩放控件
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        //禁止缩放
        webSettings.setSupportZoom(false);
        //文件权限
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowContentAccess(true);
        //缓存相关
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        return webView;
    }
}
