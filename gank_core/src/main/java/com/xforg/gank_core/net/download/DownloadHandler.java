package com.xforg.gank_core.net.download;

import android.os.AsyncTask;
import android.util.Log;

import com.xforg.gank_core.net.RestCreator;
import com.xforg.gank_core.net.callbacks.IError;
import com.xforg.gank_core.net.callbacks.IFailure;
import com.xforg.gank_core.net.callbacks.IRequest;
import com.xforg.gank_core.net.callbacks.ISuccess;

import java.util.WeakHashMap;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created By zhongxianfeng on 19-2-26
 * github: https://github.com/xianfeng92
 */
public class DownloadHandler {
    private static final String TAG = "DownloadHandler";
    private final String URL;
    private final WeakHashMap<String,Object> PARAMS;
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;



    public DownloadHandler(String url,
                           WeakHashMap<String, Object> params,
                           IRequest request,
                           String download_dir,
                           String extension,
                           String name,
                           ISuccess success,
                           IFailure failure,
                           IError error) {
        URL = url;
        PARAMS = params;
        REQUEST = request;
        DOWNLOAD_DIR = download_dir;
        EXTENSION = extension;
        NAME = name;
        SUCCESS = success;
        FAILURE = failure;
        ERROR = error;
    }

    public static DownLoadHandlerBuilder builder(){
        return new DownLoadHandlerBuilder();
    }


    public final void handleDownLoad(){
        if(REQUEST != null){
            RestCreator.getRestServiceForDownLoad()
                    .downloadWithoutRxjava(URL).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        Log.d(TAG, "onResponse: ");
                        final ResponseBody body = response.body();
                        final SaveFileTask task = new SaveFileTask(REQUEST,SUCCESS);
                        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                                DOWNLOAD_DIR,EXTENSION,body,NAME);
                        //这里一定要注意判断，否则文件下载不全
                        if(task.isCancelled()){
                                REQUEST.onRequestEnd();
                        }
                    }else {
                        if (ERROR != null){
                            ERROR.onError(response.code(),response.message());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (FAILURE != null){
                        FAILURE.onFailure();
                    }
                }
            });
        }
    }

}
