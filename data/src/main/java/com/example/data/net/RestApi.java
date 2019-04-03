package com.example.data.net;

import com.example.data.entity.MeiziList;

import io.reactivex.Observable;

/**
 * Created By zhongxianfeng on 19-4-3
 * github: https://github.com/xianfeng92
 */
public interface RestApi {
    /**
     * Retrieves an {@link Observable} which will emit a List of {@link MeiziList}.
     */
    Observable<MeiziList> meiziList(int page);
}
