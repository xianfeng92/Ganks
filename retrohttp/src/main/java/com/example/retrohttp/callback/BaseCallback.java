package com.example.retrohttp.callback;

import com.example.retrohttp.retroHttpConfig;
import com.example.retrohttp.exception.ApiException;
import com.example.retrohttp.exception.ExceptionHanlder;
import com.example.retrohttp.observer.HttpObserver;
import com.example.retrohttp.utils.ThreadUtils;

import io.reactivex.annotations.NonNull;

/**
 * Created By zhongxianfeng on 19-3-26
 * github: https://github.com/xianfeng92
 */
public abstract class BaseCallback<T> extends HttpObserver<T> {

    @Override
    public void onNext(@NonNull T value) {
        super.onNext(value);
        inSuccess(value);
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (e instanceof ApiException) {
            ApiException exception = (ApiException) e;
            inError(exception.getCode(), exception.getMsg());
        } else {
            inError(ExceptionHanlder.UN_KNOWN_ERROR, "未知错误");
        }
    }

    @Override
    public void onCanceled() {
        onCanceledLogic();
    }


    /**
     * 请求成功
     *
     * @param t
     */
    public abstract void inSuccess(T t);

    /**
     * 请求出错
     *
     * @param code
     * @param desc
     */
    public abstract void inError(int code, String desc);

    /**
     * 请求取消
     */
    public abstract void inCancel();

    /**
     * Http被取消回调处理逻辑
     */
    private void onCanceledLogic() {
        if (!ThreadUtils.isMainThread()) {
            retroHttpConfig.getInstance().getHandler().post(new Runnable() {
                @Override
                public void run() {
                    inCancel();
                }
            });
        } else {
            inCancel();
        }
    }

}
