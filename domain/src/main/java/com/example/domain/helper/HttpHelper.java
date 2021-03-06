package com.example.domain.helper;

import com.example.domain.MeiziList;
import io.reactivex.Observable;

/**
 * Created By zhongxianfeng on 19-4-3
 * github: https://github.com/xianfeng92
 */
public interface HttpHelper {
    /**
     * Retrieves an {@link Observable} which will emit a List of {@link MeiziList}.
     */
    Observable<MeiziList> meiziList(int page);

    Observable<MeiziList> getRandomMeizi(int num);
}
