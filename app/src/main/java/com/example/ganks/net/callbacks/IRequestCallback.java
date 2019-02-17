package com.example.ganks.net.callbacks;

import android.os.Handler;

import com.example.ganks.global.ConfigKeys;
import com.example.ganks.global.Gank;
import com.example.ganks.loader.GankLoader;
import com.example.ganks.loader.LoadStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created By apple on 2019/2/17
 * github: https://github.com/xianfeng92
 */
public final class IRequestCallback implements Callback<String> {

    private final IRequest IREQUEST;
    private final ISuccess ISECESS;
    private final IFailure IFAILURE;
    private final IError IERROR;

    private final LoadStyle LOAD_STYLE;
    private static final Handler HANDLER = Gank.getHandler();


    public IRequestCallback(IRequest irequest, ISuccess isecess, IFailure ifailure, IError ierror, LoadStyle load_style) {
        IREQUEST = irequest;
        ISECESS = isecess;
        IFAILURE = ifailure;
        IERROR = ierror;
        LOAD_STYLE = load_style;
    }


    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()){
            if (call.isExecuted()){
                if (ISECESS != null){
                    ISECESS.onSuccess(response.body());
                }
            }
        }else {
            if (IERROR != null){
                IERROR.onError(response.code(),response.message());
            }
        }

    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if(IFAILURE != null){
            IFAILURE.onFailure();
        }
        if (IREQUEST != null){
            IREQUEST.onRequestEnd();
        }
    }

    private void onRequestFinish(){
        final long delayed = Gank.getConfiguration(ConfigKeys.LOADER_DELAYED.name());
        if (LOAD_STYLE != null){
            if (HANDLER != null){
                HANDLER.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GankLoader.stopLoading();
                    }
                }, delayed);
            }
        }
    }

}
