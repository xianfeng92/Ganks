package com.xforg.gank_core.net.download;

import com.xforg.gank_core.net.callbacks.IError;
import com.xforg.gank_core.net.callbacks.IFailure;
import com.xforg.gank_core.net.callbacks.IRequest;
import com.xforg.gank_core.net.callbacks.ISuccess;
import java.io.File;
import java.util.WeakHashMap;

/**
 * Created By zhongxianfeng on 19-2-26
 * github: https://github.com/xianfeng92
 */
public class DownLoadHandlerBuilder {

    private  String URL = null;
    private  WeakHashMap<String,Object> PARAMS = new WeakHashMap<>();
    private  IRequest REQUEST = null;
    private  String DOWNLOAD_DIR = null;
    private  String EXTENSION = null;
    private  String NAME = null;
    private  ISuccess SUCCESS = null;
    private  IFailure FAILURE = null;
    private  IError ERROR = null;
    private File mFile = null;

    DownLoadHandlerBuilder(){
    }

    public final DownLoadHandlerBuilder url(String url){
        URL = url;
        return this;
    }

    public final DownLoadHandlerBuilder params(WeakHashMap<String,Object> params){
        PARAMS = params;
        return this;
    }

    public final DownLoadHandlerBuilder params(String key, Object value){
        if (PARAMS != null){
            PARAMS.put(key,value);
        }
        return this;
    }

    public final DownLoadHandlerBuilder file(File file){
        mFile = file;
        return this;
    }

    public final DownLoadHandlerBuilder file(String file){
        File file1 = new File(file);
        mFile = file1;
        return this;
    }

    public final DownLoadHandlerBuilder name(String name){
        NAME = name;
        return this;
    }

    public final DownLoadHandlerBuilder dir(String dir){
        DOWNLOAD_DIR = dir;
        return this;
    }

    public final DownLoadHandlerBuilder extension(String extension){
        EXTENSION = extension;
        return this;
    }

    public final DownLoadHandlerBuilder success(ISuccess success){
        SUCCESS = success;
        return this;
    }

    public final DownLoadHandlerBuilder error(IError error){
        ERROR = error;
        return this;
    }

    public final DownLoadHandlerBuilder request(IRequest request){
        REQUEST = request;
        return this;
    }

    public DownloadHandler build(){
        return new DownloadHandler(URL,PARAMS,REQUEST,DOWNLOAD_DIR,EXTENSION,NAME,SUCCESS,FAILURE,ERROR);
    }
}
