package com.xforg.gank_core.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.xforg.gank_core.delegates.GankDelegate;
import com.xforg.gank_core.web.WebDelegate;
import com.xforg.gank_core.web.WebDelegateImpl;

/**
 * Created By apple on 2019/3/3
 * github: https://github.com/xianfeng92
 */
public class Router {

    private Router(){
    }

    private static class Holder{
        private static Router INSTANCE = new Router();
    }

    public static Router getInstance(){
        return Holder.INSTANCE;
    }

    public final boolean handleWebUrl(WebDelegate webDelegate, String url){

        //如果是电话协议
        if (url.contains("tel:")){
            callPhone(webDelegate.getContext(),url);
            return true;
        }

        final GankDelegate topDelegate = webDelegate.getTopDelegate();

        final WebDelegateImpl webDelegate1 = WebDelegateImpl.create(url);
        topDelegate.getSupportDelegate().start(webDelegate1);
        return true;
    }

    private void loadWebPage(WebView webView, String url){
        if(webView != null){
            webView.loadUrl(url);
        }else {
            throw new NullPointerException("webView Is null!");
        }
    }

    private void loadLocalPage(WebView webView,String url){
        if(URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)){
            loadWebPage(webView,url);
        }else {
            loadLocalPage(webView,url);
        }
    }
    private void loadPage(WebView webView, String url) {
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url);
        } else {
            loadLocalPage(webView, url);
        }
    }

    public final void loadPage(WebDelegate delegate,String url){
        loadPage(delegate.getWebView(),url);
    }


    private void callPhone(Context context, String uri) {
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri data = Uri.parse(uri);
        intent.setData(data);
        ContextCompat.startActivity(context, intent, null);
    }
}
