package com.example.ganks.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.ganks.R;
import com.github.lzyzsd.circleprogress.CircleProgress;

public class ArticleContentFragment extends Fragment {

    private Bundle bundle;
    private WebView webview;
    private CircleProgress circleProgress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content,container,false);
        bundle = getArguments();
        webview = view.findViewById(R.id.webview);
        circleProgress = view.findViewById(R.id.circle_progress);
        initWebView(bundle.getString("URL"));
        return view;
    }


    public static ArticleContentFragment newInstance(){
        ArticleContentFragment articleContentFragment = new ArticleContentFragment();
        return articleContentFragment;
    }

    private void initWebView(String url) {
        WebSettings settings = webview.getSettings();
        // 将图片调整到适合webview的大小
        settings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        settings.setLoadWithOverviewMode(true);
        // 支持缩放，默认为true
        settings.setSupportZoom(true);
        // 设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setBuiltInZoomControls(true);
        // 隐藏原生的缩放控件
        settings.setDisplayZoomControls(false);
        // 打开网页时不调用系统浏览器，而是在本WebView中显示；在网页上的所有加载都经过这个方法,这个函数我们可以做很多操作
        webview.setWebViewClient(new WebViewClient(){

            //拦截url
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100){
                    String progress = newProgress + "%";
                    webview.setVisibility(View.INVISIBLE);
                    circleProgress.setVisibility(View.VISIBLE);
                    circleProgress.setProgress(newProgress);
                }else {
                    circleProgress.setVisibility(View.INVISIBLE);
                    webview.setVisibility(View.VISIBLE);
                }
            }
        });
        // 加载一个url
        webview.loadUrl(url);
    }
}
