package com.example.retrohttp.cancel;

import android.support.v4.util.ArrayMap;

import java.util.Set;

import io.reactivex.disposables.Disposable;

/**
 * Created By zhongxianfeng on 19-3-26
 * github: https://github.com/xianfeng92
 */
public class RequestManagerImpl implements RequestManager<Object> {

    private ArrayMap<Object, Disposable> mMaps;//处理,请求列表

    private RequestManagerImpl(){
    }

    private static class Holder{
        private static final RequestManagerImpl INSTANCE = new RequestManagerImpl();
    }

    public static RequestManagerImpl getInstance(){
        return Holder.INSTANCE;
    }


    @Override
    public void add(Object tag, Disposable disposable) {
        if (mMaps == null){
            mMaps = new ArrayMap<>();
        }
        mMaps.put(tag, disposable);
    }

    @Override
    public void remove(Object tag) {
        if (!mMaps.isEmpty()) {
            mMaps.remove(tag);
        }
    }

    @Override
    public void cancel(Object tag) {
        if (mMaps.isEmpty()) {
            return;
        }
        if (mMaps.get(tag) == null) {
            return;
        }
        if (!mMaps.get(tag).isDisposed()) {
            mMaps.get(tag).dispose();
        }
        mMaps.remove(tag);
    }

    @Override
    public void cancelAll() {
        if (mMaps.isEmpty()) {
            return;
        }
        //遍历取消请求
        Disposable disposable;
        Set<Object> keySet = mMaps.keySet();
        for (Object key : keySet) {
            disposable = mMaps.get(key);
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
        mMaps.clear();
    }


    /**
     * 判断是否取消了请求
     *
     * @param tag
     * @return
     */
    public boolean isDisposed(Object tag) {
        if (mMaps.isEmpty() || mMaps.get(tag) == null) return true;
        return mMaps.get(tag).isDisposed();
    }
}
