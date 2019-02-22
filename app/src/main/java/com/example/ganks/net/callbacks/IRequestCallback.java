package com.example.ganks.net.callbacks;

import android.os.Handler;

import com.example.ganks.global.Gank;

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

    private static final Handler HANDLER = Gank.getHandler();


    public IRequestCallback(IRequest irequest, ISuccess isecess, IFailure ifailure, IError ierror) {
        IREQUEST = irequest;
        ISECESS = isecess;
        IFAILURE = ifailure;
        IERROR = ierror;
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

}
