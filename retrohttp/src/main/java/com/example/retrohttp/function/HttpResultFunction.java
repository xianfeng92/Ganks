package com.example.retrohttp.function;


import com.example.retrohttp.exception.ExceptionHanlder;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created By zhongxianfeng on 19-3-26
 * github: https://github.com/xianfeng92
 */
public class HttpResultFunction<T> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(Throwable throwable) throws Exception {
        return Observable.error(ExceptionHanlder.handleException(throwable));
    }
}
