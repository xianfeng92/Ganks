package com.example.retrohttp.observer;

import android.text.TextUtils;
import com.example.retrohttp.cancel.RequestCancel;
import com.example.retrohttp.cancel.RequestManagerImpl;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created By zhongxianfeng on 19-3-26
 * github: https://github.com/xianfeng92
 */
public class HttpObserver<T> implements Observer<T>, RequestCancel {

    /*请求标识*/
    private String mTag;

    @Override
    public void onSubscribe(Disposable d) {
        if (!TextUtils.isEmpty(mTag)) {
            RequestManagerImpl.getInstance().add(mTag, d);
        }
    }

    @Override
    public void onNext(T t) {
        if (!TextUtils.isEmpty(mTag)) {
            RequestManagerImpl.getInstance().remove(mTag);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (!TextUtils.isEmpty(mTag)) {
            RequestManagerImpl.getInstance().remove(mTag);
        }

    }

    @Override
    public void onComplete() {
        /**
         * 由于LifecycleProvider取消监听直接截断事件发送，但是必定回调onComplete()
         * 因此在这里判断请求是否被取消，如果到这里还未被取消，说明是LifecycleProvider导致的取消请求，回调onCancel逻辑
         * 备注：
         * 1.子类重写此方法时需要调用super
         * 2.多个请求复用一个监听者HttpObserver时，tag会被覆盖，取消回调会有误
         */
        if (!RequestManagerImpl.getInstance().isDisposed(mTag)) {
            cancel();
        }
    }

    @Override
    public void cancel() {
        if (!TextUtils.isEmpty(mTag)) {
            RequestManagerImpl.getInstance().cancel(mTag);
        }
    }

    @Override
    public void onCanceled() {

    }

    /**
     * 是否已经处理
     *
     */
    public boolean isDisposed() {
        if (TextUtils.isEmpty(mTag)) {
            return true;
        }
        return RequestManagerImpl.getInstance().isDisposed(mTag);
    }


    /**
     * 设置标识请求的TAG
     *
     * @param tag
     */
    public void setTag(String tag) {
        this.mTag = tag;
    }
}
