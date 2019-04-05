package com.example.data.repository;

import android.util.Log;
import com.example.data.entity.DaoMeiziEntity;
import com.example.data.entity.MeiziList;
import com.example.data.entity.mapper.MeiziEntityDataMapper;
import com.example.data.net.RestApiImpl;
import com.example.domain.Meizi;
import com.example.domain.repository.MeiziRepository;
import com.example.data.GreenDaoHelper;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created By zhongxianfeng on 19-4-3
 * github: https://github.com/xianfeng92
 */
public class MeiziDataRepository implements MeiziRepository {
    private static final String TAG = "MeiziDataRepository";

    @Inject
    MeiziDataRepository(){}

    @Override
    public Observable<List<Meizi>> meizis(int page) {
        return RestApiImpl.getInstance().meiziList(page).map(new Function<MeiziList, List<Meizi>>() {
            @Override
            public List<Meizi> apply(MeiziList meiziList) throws Exception {
                return MeiziEntityDataMapper.getInstance().transform(meiziList);
            }
        });
    }

    @Override
    public void addToFavorite(Meizi meizi) {
        DaoMeiziEntity daoMeiziEntity = MeiziEntityDataMapper.getInstance().transformMeizi2Dao(meizi);
        Log.d(TAG, "addToFavorite: "+daoMeiziEntity.url);
        GreenDaoHelper.insert(daoMeiziEntity);
//        if (!GreenDaoHelper.isDaoContainMeizi(daoMeiziEntity._id)){
//            GreenDaoHelper.insert(daoMeiziEntity);
//        }else {
//            Log.d(TAG, "you already love It!!");
//        }
    }

    @Override
    public List<Meizi> getMeiziFromDao() {
        List<Meizi> meizis = new ArrayList<>();
        List<DaoMeiziEntity> daoMeiziEntities = GreenDaoHelper.getAllMeiziEntity();
        for(DaoMeiziEntity daoMeiziEntity:daoMeiziEntities){
            meizis.add(MeiziEntityDataMapper.getInstance().transformDao2Meizi(daoMeiziEntity));
        }
        for (Meizi meizi : meizis){
            Log.d(TAG, "getMeiziFromDao: "+meizi.getUrl());
        }
        return meizis;
    }

}
