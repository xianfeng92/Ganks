package com.example.retrohttp.callback;

import com.example.retrohttp.exception.ExceptionHanlder;
import com.example.retrohttp.helper.ParseHelper;

/**
 * Created By zhongxianfeng on 19-3-26
 * github: https://github.com/xianfeng92
 */
public abstract class HttpCallback<T> extends BaseCallback<T> implements ParseHelper<T> {

    /**
     * 是否回调成功函数
     */
    private boolean callSuccess = true;

    @Override
    public void inSuccess(T t) {
        T result = parse((String) t);
        if (callSuccess && isBusinessOk()) {
            onSuccess(result);
        }
    }

    @Override
    public void inError(int code, String desc) {
        onError(code, desc);
    }

    @Override
    public void inCancel() {
        onCancel();
    }
    /**
     * 成功回调
     *
     * @param value
     */
    public abstract void onSuccess(T value);

    /**
     * 失败回调
     *
     * @param code
     * @param desc
     */
    public abstract void onError(int code, String desc);

    /**
     * 取消回调
     */
    public abstract void onCancel();

    /**
     * 业务逻辑是否成功
     *
     * @return
     */
    public abstract boolean isBusinessOk();

    /**
     * 数据转换/解析数据
     *
     * @param data
     * @return
     */
    public abstract T onConvert(String data) throws Exception;


    @Override
    public T parse(String data) {
        T t = null;
        try {
            t = onConvert(data);
            callSuccess = true;
        } catch (Exception e) {
            callSuccess = false;
            e.printStackTrace();
            onError(ExceptionHanlder.ANALYTIC_CLIENT_DATA_ERROR, "解析数据出错");
        }
        return t;
    }
}
