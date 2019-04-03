package com.example.domain.repository;

import com.example.domain.Meizi;
import java.util.List;
import io.reactivex.Observable;

/**
 * Created By zhongxianfeng on 19-4-3
 * github: https://github.com/xianfeng92
 */
public interface MeiziRepository {
    /**
     * Get an {@link Observable} which will emit a List of {@link Meizi}.
     */
    Observable<List<Meizi>> meizis(int page);
}
