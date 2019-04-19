package com.example.data.repository;

import com.example.data.entity.DaoMeiziEntity;
import com.example.data.entity.mapper.MeiziEntityDataMapper;
import com.example.data.HelperImpl.GreenDaoHelperImpl;
import com.example.domain.MeiziList;
import com.orhanobut.logger.Logger;
/**
 * Created By zhongxianfeng on 19-4-3
 * github: https://github.com/xianfeng92
 */
public class MeiziDataRepository{


//    @Inject
//    HttpHelperImpl httpHelper;
//
//    public MeiziDataRepository(HttpHelper httpHelper, DbHelper dbHelper, PreferenceHelper preferenceHelper, GreenDaoHelper greenDaoHelper) {
//        super(httpHelper, dbHelper, preferenceHelper, greenDaoHelper);
//    }

//    public Observable<List<Meizi>> meizis(int page) {
//        return httpHelper.meiziList(page).map(new Function<MeiziList, List<Meizi>>() {
//            @Override
//            public List<Meizi> apply(MeiziList meiziList) throws Exception {
//                return MeiziEntityDataMapper.getInstance().transform(meiziList);
//            }
//        });
//    }

    public void addToFavorite(MeiziList.Meizi meizi) {
        DaoMeiziEntity daoMeiziEntity = MeiziEntityDataMapper.getInstance().transformMeizi2Dao(meizi);
        Logger.d("addToFavorite %s",daoMeiziEntity.url);
        if (!GreenDaoHelperImpl.isDaoContainMeizi(daoMeiziEntity.id)){
            GreenDaoHelperImpl.insert(daoMeiziEntity);
        }else {
            Logger.d("addToFavorite %s","you already love It!!");
        }
    }

//    public List<Meizi> getMeiziFromDao() {
//        List<Meizi> meizis = new ArrayList<>();
//        List<DaoMeiziEntity> daoMeiziEntities = GreenDaoHelperImpl.getAllMeiziEntity();
//        for(DaoMeiziEntity daoMeiziEntity:daoMeiziEntities){
//            meizis.add(MeiziEntityDataMapper.getInstance().transformDao2Meizi(daoMeiziEntity));
//        }
//        Logger.d("meizi %d",meizis.size());
//        return meizis;
//    }
}
