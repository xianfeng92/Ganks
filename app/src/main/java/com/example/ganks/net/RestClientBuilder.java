package com.example.ganks.net;

import android.content.Context;

import com.example.ganks.loader.LoadStyle;
import com.example.ganks.net.callbacks.IError;
import com.example.ganks.net.callbacks.IFailure;
import com.example.ganks.net.callbacks.IRequest;
import com.example.ganks.net.callbacks.ISuccess;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.RequestBody;

/**
 * Created By apple on 2019/2/17
 * github: https://github.com/xianfeng92
 */
public class RestClientBuilder {

    private WeakHashMap<String,Object> PARAMS = new WeakHashMap<>();
    private String mUrl = null;
    private IRequest mIRequest = null;
    private ISuccess mISuccess = null;
    private IFailure mIFailure = null;
    private IError mIError = null;
    private RequestBody mBody = null;
    private Context mContext = null;
    private LoadStyle mLoaderStyle = null;
    private File mFile = null;
    private String mDownloadDir = null;
    private String mExtension = null;
    private String mName = null;

    RestClientBuilder(){}

    public final RestClientBuilder url(String url){
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String,Object> params){
        this.PARAMS = params;
        return this;
    }

    public final RestClientBuilder params(String key, Object value){
        if (PARAMS != null){
            PARAMS.put(key,value);
        }
        return this;
    }

    public final RestClientBuilder file(File file){
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder file(String file){
        File file1 = new File(file);
        this.mFile = file1;
        return this;
    }

    public final RestClientBuilder name(String name){
        this.mName = name;
        return this;
    }

    public final RestClientBuilder dir(String dir){
        this.mDownloadDir = dir;
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess){
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure){
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError){
        this.mIError = iError;
        return this;
    }

    public final RestClientBuilder request(IRequest iRequest){
        this.mIRequest = iRequest;
        return this;
    }

    public final RestClientBuilder loader(Context context, LoadStyle loadStyle){
        this.mContext = context;
        this.mLoaderStyle = loadStyle;
        return this;
    }

    public final RestClientBuilder loader(Context context){
        this.mContext = context;
        this.mLoaderStyle = LoadStyle.BallSpinFadeLoaderIndicator;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS,
                mDownloadDir, mExtension, mName,
                mIRequest, mISuccess, mIFailure,
                mIError, mBody, mFile, mContext,
                mLoaderStyle);
    }

}
