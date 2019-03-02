package com.xforg.gank_core.net.callbacks;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.xforg.gank_core.app.Gank;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created By apple on 2019/2/23
 * github: https://github.com/xianfeng92
 */
public class RequestCallbacks implements Callback<String> {

    private static final String TAG = "RequestCallbacks";

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private static final Handler HANDLER = Gank.getHandler();

    public RequestCallbacks(IRequest request, ISuccess success, IFailure failure, IError error) {
        Log.d(TAG, "RequestCallbacks: ");
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
    }

    @Override
    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
        Log.d(TAG, "onResponse: ");
        if (response.isSuccessful()) {
            Log.d(TAG, "onResponse: isSuccessful");
            if (call.isExecuted()) {
                if (SUCCESS != null) {
                    SUCCESS.onSuccess(response.body());
                }
            }
        } else {
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());
            }
        }

    }

    @Override
    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
        Log.d(TAG, "onFailure: "+t.getMessage());
        if (FAILURE != null) {
            FAILURE.onFailure();
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
    }
}
