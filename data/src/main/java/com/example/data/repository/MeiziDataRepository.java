package com.example.data.repository;

import com.example.data.entity.MeiziList;
import com.example.data.entity.mapper.MeiziEntityDataMapper;
import com.example.data.net.RestApiImpl;
import com.example.domain.Meizi;
import com.example.domain.repository.MeiziRepository;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created By zhongxianfeng on 19-4-3
 * github: https://github.com/xianfeng92
 */
public class MeiziDataRepository implements MeiziRepository {

    @Override
    public Observable<List<Meizi>> meizis(int page) {
        return RestApiImpl.getInstance().meiziList(page).map(new Function<MeiziList, List<Meizi>>() {
            @Override
            public List<Meizi> apply(MeiziList meiziList) throws Exception {
                return MeiziEntityDataMapper.getInstance().transform(meiziList);
            }
        });
    }
}
